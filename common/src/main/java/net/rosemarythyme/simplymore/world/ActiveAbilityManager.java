package net.rosemarythyme.simplymore.world;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.item.uniques.MoundshifterItem;
import net.rosemarythyme.simplymore.item.uniques.MyrmedgeItem;
import net.rosemarythyme.simplymore.item.uniques.VipersCallItem;
import net.rosemarythyme.simplymore.networking.s2c.S2CAbilityManagerPacket;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.SoundRegistry;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class ActiveAbilityManager {
    public static final ActiveAbilityManager SERVER = new ActiveAbilityManager();

    protected final List<ActiveAbility> activeAbilities = new ArrayList<>();
    protected record ActiveAbility(LivingEntity owner, int duration, int remainingDuration, Type type) {
        private int tick() {
            return this.type.run.apply(this);
        }

        ActiveAbility setRemainingDuration(int time) {
            return new ActiveAbility(owner, duration, time, type);
        }
    }

    public void clear() {
        activeAbilities.clear();
    }

    public float getInOutStrength(LivingEntity player, Type type) {
        if(!isInAbility(player, type)) return 0f;

        float durationPercentage = (float) this.getCurrentDuration(player, type) / this.getTotalDuration(player, type);
        float durationStrength = 1f;

        if(durationPercentage <= 0.1f) {
            durationStrength =  MathUtils.clampedLerp(durationPercentage, 0, 0.1f, 0f, 1f);
        }

        if(durationPercentage >= 0.9f) {
            durationStrength = 1f - MathUtils.clampedLerp(durationPercentage, 0.9f, 1f, 0f, 1f);
        }

        return durationStrength;
    }

    public enum Type {
        HARVEST(ActiveAbilityManager::harvestTick, (player) -> HashMultimap.create(), 32),
        DRILL(ActiveAbilityManager::drillTick, ActiveAbilityManager::drillModifiers,100),
        STATUE(ActiveAbilityManager::statueTick, ActiveAbilityManager::statueModifiers, 100),
        PETRIFIED(ActiveAbilityManager::petrifiedTick, ActiveAbilityManager::statueModifiers, 100),
        VIPERS_CALL(ActiveAbilityManager::vipersCallTick, (player) -> HashMultimap.create(), 32),
        GRASPING(ActiveAbilityManager::graspingTick, ActiveAbilityManager::graspingModifiers, 0);

        final Function<ActiveAbility, Integer> run;
        final Function<LivingEntity, Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> modifiers;
        final double range;
        Type(Function<ActiveAbility, Integer> run, Function<LivingEntity, Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier>> modifiers, double range) {
            this.run = run;
            this.range = range;
            this.modifiers = modifiers;
        }
    }

    public void stop(LivingEntity owner, Type type) {
        add(owner, type, 0, 0);
        sync(new ActiveAbility(owner, 0, 0, type));
    }

    public void add(LivingEntity owner, Type type, int duration, int currentDuration) {
        if(owner == null) return;
        remove(owner, type, false);

        if(currentDuration == 0) {
            removeAttributes(type, owner);
            return;
        }
        
        activeAbilities.add(new ActiveAbility(owner, duration, currentDuration, type));

        owner.getAttributes().addTemporaryModifiers(type.modifiers.apply(owner));
    }

    public void start(LivingEntity owner, Type type, int duration) {
        add(owner, type, duration, duration);
    }

    private void removeAttributes(Type type, LivingEntity owner) {
        owner.getAttributes().removeModifiers(type.modifiers.apply(owner));
    }

    public void remove(LivingEntity owner, Type type, boolean updateClient) {
        Optional<ActiveAbility> ability = activeAbilities.stream().filter(a ->
                a.owner() == owner &&
                        a.type() == type
        ).findFirst();

        if(ability.isEmpty()) return;
        ActiveAbility active = ability.get();

        activeAbilities.remove(active);

        if(updateClient) {
            sync(new ActiveAbility(active.owner, active.duration, 0, active.type));
        }
    }

    public boolean isInAbility(LivingEntity owner, Type type) {
        return new ArrayList<>(activeAbilities).stream().anyMatch(ability ->
                ability.owner() == owner &&
                ability.type() == type
        );
    }

    public int getCurrentDuration(LivingEntity owner, Type type) {
        Optional<ActiveAbility> ability = new ArrayList<>(activeAbilities).stream().filter((a) ->
                a.owner() == owner &&
                        a.type() == type
        ).findFirst();

        if(ability.isEmpty()) return 0;

        return ability.get().remainingDuration;
    }

    public int getTotalDuration(LivingEntity owner, Type type) {
        Optional<ActiveAbility> ability = new ArrayList<>(activeAbilities).stream().filter((a) ->
                a.owner() == owner &&
                        a.type() == type
        ).findFirst();

        if(ability.isEmpty()) return 0;

        return ability.get().duration;
    }

    private void sync(ActiveAbility ability) {
        List<ServerPlayerEntity> players = ability.owner.getWorld().getPlayers().stream().map((player -> (ServerPlayerEntity) player)).toList();
        NetworkManager.sendToPlayers(players, new S2CAbilityManagerPacket(ability.owner, ability.duration, ability.remainingDuration, ability.type));
    }

    public void tick() {
        List<ActiveAbility> remaining = new ArrayList<>();
        Set<ActiveAbility> toSync = new HashSet<>();
        for(ActiveAbility ability : new ArrayList<>(activeAbilities)) {
            if(ability.owner == null) {
                continue;
            }

            int time = ability.tick();

            ability = ability.setRemainingDuration(time);

            if(!ability.owner.isAlive() || ability.remainingDuration <= 0) {
                removeAttributes(ability.type, ability.owner);
                sync(new ActiveAbility(ability.owner, 0, 0, ability.type));
                continue;
            }

            remaining.add(ability);

            if(ability.remainingDuration == ability.duration - 1
                    || ability.remainingDuration % 5 == 0) {
                toSync.add(ability);
            }
        }

        toSync.forEach(this::sync);

        activeAbilities.clear();
        activeAbilities.addAll(remaining);
    }

    private static int harvestTick(ActiveAbility ability) {
        new TargetList(ability.owner)
                .applyEffect(StatusEffects.HASTE, 10, 3)
                .applyEffect(StatusEffects.SPEED, 10, 1);

        if(ability.remainingDuration % 10 == 0) {
            AudioVisualUtils.playSound(ability.owner.getWorld(), ability.owner.getPos(), new Sound(SoundEvents.ENTITY_WARDEN_HEARTBEAT).setPitch(1.2f));
        }

        if(!EntityUtils.isHolding(ability.owner, ItemRegistry.THE_BLOOD_HARVESTER.get())) {
            int outro = (int)(ability.duration / 10f);
            if(ability.remainingDuration > outro) {
                return outro;
            }
        }

        return ability.remainingDuration - 1;
    }

    private static int drillTick(ActiveAbility ability) {
        ServerWorld world = (ServerWorld) ability.owner.getWorld();

        if(ability.remainingDuration == 1) {
            MoundshifterItem.emerge(world, ability.owner, true);
            return 0;
        }

        if(!(ability.owner.getVehicle() instanceof LivingEntity vehicle)) {
            ability.owner.dismountVehicle();
        } else {
            return drillTick(new ActiveAbility(vehicle, ability.duration, ability.remainingDuration, ability.type));
        }

        AudioVisualUtils.particleCuboid(world, ability.owner.getPos(), new BlockStateParticleEffect(ParticleTypes.BLOCK, ability.owner.getWorld().getBlockState(ability.owner.getBlockPos().down())), 0.1f, 0.1f, 25, 0.2f);

        if(!ability.owner.isOnGround() || (ability.owner.getVehicle() instanceof LivingEntity vehicle && vehicle.isOnGround())) {
            BlockHitResult hit = EntityUtils.raycastDown(ability.owner, ability.owner.getPos(), ability.owner.getWorld(), 2);
            if(hit.getType() == HitResult.Type.MISS) {
                if(ability.owner instanceof PlayerEntity player) {
                    PlayerItemUseManager.stop(player, ability.owner.getActiveItem(), false);
                }

                MoundshifterItem.emerge(world, ability.owner, false);
                return 0;
            }

            ability.owner.setVelocity(new Vec3d(0, -2,0));
            ability.owner.velocityModified = true;
        }

        if(ability.remainingDuration % 10 == 0) {
            AudioVisualUtils.playSound(ability.owner.getWorld(), ability.owner.getPos(), new Sound(SoundRegistry.MAGIC_BOW_PULL_BACK_LONG_VERSION_02.get(), 1f, 0f));
        }

        return ability.remainingDuration - 1;
    }

    private static Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> drillModifiers(LivingEntity entity) {
        Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map = HashMultimap.create();
        map.put(EntityAttributes.GENERIC_STEP_HEIGHT, new EntityAttributeModifier(SimplyMore.identifier("drill_height"), 0.5f, EntityAttributeModifier.Operation.ADD_VALUE));

        if(entity instanceof PlayerEntity) {
            map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("drill_speed"), MoundshifterItem.SETTINGS.speedMultiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        return map;
    }

    private static int statueTick(ActiveAbility ability) {
        if(ability.remainingDuration <= 1) {
            EntityUtils.cooldown(ability.owner, ItemRegistry.BLADE_OF_THE_GROTESQUE.get(), BladeOfTheGrotesqueItem.SETTINGS.cooldown, true);
        }

        return petrifiedTick(ability);
    }

    private static int petrifiedTick(ActiveAbility ability) {
        AudioVisualUtils.particleAroundEntity(ability.owner, ParticleTypes.ASH, 5, 0.2, 1);
        EntityUtils.putAllItemsOnCooldown(ability.owner, ability.remainingDuration - 1);

        if(ability.remainingDuration <= 1) {
            BladeOfTheGrotesqueItem.breakOutVisuals(ability.owner);
            return 0;
        }

        return ability.remainingDuration - 1;
    }

    private static int vipersCallTick(ActiveAbility ability) {
        AudioVisualUtils.particleAroundEntity(ability.owner, ParticleTypes.SPORE_BLOSSOM_AIR, 3, 2f, 1f);
        TargetList nearby = AttackUtils.cylinderAttack(ability.owner, ability.owner.getPos(), VipersCallItem.SETTINGS.auraRange, 4, AttackUtils.AttackTarget.OTHERS_AND_USER_POSITIVELY);

        List<StatusEffectInstance> positive = new ArrayList<>();
        List<StatusEffectInstance> negative = new ArrayList<>();

        Predicate<StatusEffect> predicate = PredicateUtils.createForEffectBlacklist(VipersCallItem.SETTINGS.blacklist, VipersCallItem.SETTINGS.includeGlobalBlacklist);
        for (LivingEntity entity : nearby.targets()) {
            if(AttackUtils.canTarget(ability.owner, entity, AttackUtils.AttackTarget.ENEMIES)) {
                for(StatusEffectInstance instance : List.copyOf(entity.getStatusEffects())) {
                    if(predicate.and(PredicateUtils.HARMFUL_EFFECT).test(instance.getEffectType().value())) {
                        negative.add(instance);
                    }
                }
            } else {
                for(StatusEffectInstance instance : List.copyOf(entity.getStatusEffects())) {
                    if(predicate.and(PredicateUtils.BENEFICIAL_EFFECT).test(instance.getEffectType().value())) {
                        positive.add(instance);
                    }
                }
            }
        }

        nearby.filterByTargetType(ability.owner, AttackUtils.AttackTarget.ALLIES_AND_USER)
                .onEach((entity) -> positive.forEach(instance -> entity.addStatusEffect(new StatusEffectInstance(instance))));

        nearby.filterByTargetType(ability.owner, AttackUtils.AttackTarget.ENEMIES)
                .onEach((entity) -> negative.forEach(instance -> entity.addStatusEffect(new StatusEffectInstance(instance))));

        return ability.remainingDuration - 1;
    }

    private static int graspingTick(ActiveAbility ability) {
        LivingEntity target = MyrmedgeItem.getActiveMyrmedgeTarget(ability.owner);
        if(target == null) {
            MyrmedgeItem.stopAbility(ability.owner);
            return 0;
        }

        target.dismountVehicle();
        target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN), 5, 0));
        target.refreshPositionAfterTeleport(EntityUtils.rangeAroundPoint(ability.owner.getEyePos(), target, ability.owner.getYaw(), 1.5f));
        target.fallDistance = 0;

        if(ability.remainingDuration % 20 == 0) {
            if(target instanceof PlayerEntity playerTarget) {
                HungerManager manager = playerTarget.getHungerManager();
                manager.setSaturationLevel(0);
                manager.setFoodLevel(manager.getFoodLevel() - 1);
            }

            target.damage(AttackUtils.getHitSource(ability.owner), MyrmedgeItem.SETTINGS.grabDamage);
            ability.owner.heal(1);

            if(ability.owner instanceof PlayerEntity player) {
                player.getHungerManager().add(2, 0.1f);
            }
        }

        return ability.remainingDuration - 1;
    }

    private static Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> statueModifiers(LivingEntity entity) {
        Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map = HashMultimap.create();
        map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(SimplyMore.identifier("statue_attack_speed"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(SimplyMore.identifier("statue_attack_damage"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return map;
    }

    private static Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> graspingModifiers(LivingEntity entity) {
        Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> map = HashMultimap.create();
        map.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(SimplyMore.identifier("grasping_attack_speed"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(SimplyMore.identifier("grasping_attack_damage"), -1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        map.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SimplyMore.identifier("grasping_move_speed"), -MyrmedgeItem.SETTINGS.grabSelfSlow, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return map;
    }

    public boolean isDrilling(LivingEntity entity) {
        return isInAbility(entity, Type.DRILL) || (entity.getFirstPassenger() instanceof LivingEntity rider && isInAbility(rider, Type.DRILL));
    }

    public boolean isStatue(LivingEntity entity) {
        return isInAbility(entity, Type.STATUE) || isInAbility(entity, Type.PETRIFIED);
    }
}
