package net.rosemarythyme.simplymore.world;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
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
import net.rosemarythyme.simplymore.item.uniques.MoundshifterItem;
import net.rosemarythyme.simplymore.networking.s2c.S2CAbilityManagerPacket;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.SoundRegistry;

import java.util.*;
import java.util.function.Function;

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

    public enum Type {
        HARVEST(ActiveAbilityManager::harvestTick, (player) -> HashMultimap.create(), 32),
        DRILL(ActiveAbilityManager::drillTick, ActiveAbilityManager::drillModifiers,100);

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

    public boolean isDrilling(LivingEntity entity) {
        return isInAbility(entity, Type.DRILL) || (entity.getFirstPassenger() instanceof LivingEntity rider && isInAbility(rider, Type.DRILL));
    }
}
