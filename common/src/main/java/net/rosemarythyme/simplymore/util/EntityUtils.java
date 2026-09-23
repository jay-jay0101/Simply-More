package net.rosemarythyme.simplymore.util;

import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.AbstractAbilityPlacementEntity;
import net.rosemarythyme.simplymore.entity.AbstractSpiritualEntity;
import net.rosemarythyme.simplymore.entity.SpiritualGuardianEntity;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.world.WeaponAbilityCooldownManager;

import java.util.*;

public class EntityUtils {
    private static final Map<LivingEntity, Long> SWING_CACHE = new HashMap<>();

    public static void replaceStackInInventory(LivingEntity entity, ItemStack oldStack, ItemStack newStack) {
        if(entity instanceof PlayerEntity player) {
            PlayerInventory inventory = player.getInventory();

            int slot = inventory.getSlotWithStack(oldStack);
            if(slot != -1) {
                inventory.setStack(slot, newStack);
                return;
            }
        }

        if(entity.getStackInHand(Hand.MAIN_HAND) == oldStack) {
            entity.setStackInHand(Hand.MAIN_HAND, newStack);
        } else if (entity.getStackInHand(Hand.OFF_HAND) == oldStack) {
            entity.setStackInHand(Hand.OFF_HAND, newStack);
        }
    }

    public static List<ItemStack> getEntireInventory(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();

        List<ItemStack> stacks = new ArrayList<>(inventory.main);
        stacks.addAll(inventory.offHand);
        stacks.addAll(inventory.armor);
        return stacks;
    }

    public static void putAllItemsOnCooldown(LivingEntity target, int time) {
        if (target instanceof PlayerEntity playerTarget) {
            for (ItemStack item : getEntireInventory(playerTarget)) {
                if (!playerTarget.getItemCooldownManager().isCoolingDown(item.getItem())) {
                    playerTarget.getItemCooldownManager().set(item.getItem(), time);
                }
            }
        } else {
            if(target.getWorld() instanceof ServerWorld world) {
                WeaponAbilityCooldownManager.setCooldown(world, target, target.getStackInHand(Hand.MAIN_HAND), time);
                WeaponAbilityCooldownManager.setCooldown(world, target, target.getStackInHand(Hand.OFF_HAND), time);
            }
        }
    }

    public static void cooldown(LivingEntity target, Item item, int time, boolean force) {
        if (target instanceof PlayerEntity playerTarget) {
            if (!playerTarget.getItemCooldownManager().isCoolingDown(item) || force) {
                playerTarget.getItemCooldownManager().set(item, time);
            }
        } else {
            WeaponAbilityCooldownManager.setCooldown((ServerWorld) target.getWorld(), target, item.getDefaultStack(), time);
        }
    }

    public static void putInCache(LivingEntity entity, long time) {
        SWING_CACHE.put(entity, time);
    }

    public static long getCache(LivingEntity entity) {
        return SWING_CACHE.getOrDefault(entity, 0L);
    }

    public static void cleanCache() {
        for(Map.Entry<LivingEntity, Long> entry : new HashSet<>(SWING_CACHE.entrySet())) {
            if(!entry.getKey().isAlive()) {
                SWING_CACHE.remove(entry.getKey());
            }
        }
    }

    public static boolean isRidingLivingEntity(LivingEntity entity) {
        return entity.getVehicle() instanceof LivingEntity;
    }

    public static BlockHitResult raycastDown(Entity entity, Vec3d pos, World world, double maxRange) {
        return world.raycast(new RaycastContext(pos, pos.offset(Direction.DOWN, maxRange), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
    }

    public static BlockHitResult raycastUp(Entity entity, Vec3d pos, World world, double maxRange) {
        return world.raycast(new RaycastContext(pos, pos.offset(Direction.UP, maxRange), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
    }

    public static void reapplyAndIncrementEffect(LivingEntity entity, RegistryEntry<StatusEffect> effect, int duration, int additionalAmplifier, int maxAmplifier) {
        int amplifier = additionalAmplifier - 1;

        StatusEffectInstance instance = entity.getStatusEffect(effect);
        if(instance != null) {
            amplifier = instance.getAmplifier() + additionalAmplifier;
        }

        entity.addStatusEffect(new StatusEffectInstance(effect, duration, Math.min(amplifier, maxAmplifier)));
    }

    public static void incrementEffect(LivingEntity entity, RegistryEntry<StatusEffect> effect, int additionalAmplifier, int maxAmplifier) {
        StatusEffectInstance instance = entity.getStatusEffect(effect);
        if(instance == null) return;

        int amplifier = instance.getAmplifier() + additionalAmplifier;
        entity.addStatusEffect(new StatusEffectInstance(effect, instance.getDuration(), Math.min(amplifier, maxAmplifier)));
    }

    public static boolean isActiveStack(LivingEntity entity, ItemStack stack) {
        if(entity.getStackInHand(Hand.MAIN_HAND).getItem() == stack.getItem()) {
            return entity.getStackInHand(Hand.MAIN_HAND) == stack;
        }

        return isHolding(entity, stack);
    }

    public static ItemStack getItemInEitherHand(Item item, LivingEntity entity) {
        ItemStack stack = entity.getStackInHand(Hand.MAIN_HAND);
        if(stack.getItem() == item) return stack;

        stack = entity.getStackInHand(Hand.OFF_HAND);
        if(stack.getItem() == item) return stack;

        return ItemStack.EMPTY;
    }

    public static boolean isHoldingInMainHand(LivingEntity entity, ItemStack stack) {
        return entity.getStackInHand(Hand.MAIN_HAND).equals(stack);
    }

    public static boolean isHolding(LivingEntity entity, Item item) {
        if(entity.getStackInHand(Hand.MAIN_HAND).getItem() == item) return true;
        return !(item instanceof TwoHandedWeapon) && entity.getStackInHand(Hand.OFF_HAND).getItem() == item;
    }

    public static boolean isHolding(LivingEntity entity, ItemStack stack) {
        if(entity.getStackInHand(Hand.MAIN_HAND) == stack) return true;
        return !(stack.getItem() instanceof TwoHandedWeapon) && entity.getStackInHand(Hand.OFF_HAND) == stack;
    }

    public static void spawnAround(World world, LivingEntity entity, Vec3d pos, double horizontalRange, double verticalRange) {
        double deltaX = (world.getRandom().nextDouble() * horizontalRange * 2) - horizontalRange;
        double deltaY = (world.getRandom().nextDouble() * verticalRange * 2) - verticalRange;
        double deltaZ = (world.getRandom().nextDouble() * horizontalRange * 2) - horizontalRange;

        entity.setPos(pos.getX() + deltaX, pos.getY() + deltaY, pos.getZ() + deltaZ);
        world.spawnEntity(entity);
    }

    public static Vec3d rangeAroundPoint(Vec3d pos, Entity entity, float yaw, float idealRange) {
        Vec3d targetPos = pos.add(MathUtils.getDirectionalVector(yaw, 0).multiply(idealRange));
        BlockHitResult hit = entity.getWorld().raycast(new RaycastContext(pos, targetPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, ShapeContext.of(entity)));

        return hit.getPos();
    }

    public static boolean isStunned(LivingEntity entity) {
        return isStunned(entity, false);
    }

    public static boolean isStunned(LivingEntity entity, boolean includeClient) {
        return ActiveAbilityManager.SERVER.isStatue(entity)
                || (includeClient && ClientActiveAbilityManager.CLIENT.isStatue(entity))
                || entity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN))
                || entity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.IMPLICIT_STUN));
    }

    public static float modifyDamageTaken(LivingEntity livingEntity, DamageSource source, float original) {
        final float damage = original;
        AttackUtils.getOwnedEntities(livingEntity, SpiritualGuardianEntity.class).forEach(guardian -> guardian.tryRetaliate(livingEntity, damage, source));

        if(ActiveAbilityManager.SERVER.isInAbility(livingEntity, ActiveAbilityManager.Type.RAGE)) {
            if(!source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return 0;
            }
        }

        RegistryEntry<StatusEffect> blessing = StatusEffectRegistry.getReference(StatusEffectRegistry.BLESSING);
        if(livingEntity.hasStatusEffect(blessing)) {
            if(!source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                AudioVisualUtils.particleAroundEntity(livingEntity, ParticleTypes.WAX_ON, 100, 0.2f, 10);
                AudioVisualUtils.playSound(livingEntity.getWorld(), livingEntity.getPos(), new Sound(SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value()).setPitch(1.4f));

                livingEntity.removeStatusEffect(blessing);
                return 0;
            }
        }

        RegistryEntry<StatusEffect> fragile = StatusEffectRegistry.getReference(StatusEffectRegistry.FRAGILE);
        StatusEffectInstance fragileInstance = livingEntity.getStatusEffect(fragile);
        if(fragileInstance != null) {
            int level = fragileInstance.getAmplifier() + 1;
            original *= 1 + (level * 0.25f);

            AudioVisualUtils.playSound(livingEntity.getWorld(), livingEntity.getPos(), new Sound(SoundEvents.BLOCK_GLASS_BREAK).setPitch(1.2f));
            AudioVisualUtils.particleAroundEntity(livingEntity, new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.GLASS.getDefaultState()), 40, 0.2f, 0.2f);
        }

        return original;
    }

    public static boolean isUntargetable(LivingEntity livingEntity) {
        return livingEntity instanceof AbstractAbilityPlacementEntity ||
                livingEntity instanceof AbstractSpiritualEntity;
    }

    public static boolean drainEffect(LivingEntity entity, StatusEffectInstance effect, int drain) {
        int currentDuration = effect.getDuration();

        entity.removeStatusEffect(effect.getEffectType());
        if(currentDuration > drain + 1) {
            entity.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration() - drain, effect.getAmplifier()));
            return false;
        }

        return true;
    }

    public static boolean isWithinCylinder(LivingEntity entity, Vec3d centerPos, double horizontalRange, double verticalRange) {
        if(centerPos.distanceTo(new Vec3d(entity.getX(), centerPos.getY(), entity.getZ())) > horizontalRange) return false;
        return !(Math.abs(centerPos.getY() - entity.getY()) > verticalRange);
    }

    public enum StepUpResult {
        NOT_ON_FLOOR,
        TOO_SHORT,
        TOO_TALL,
        SUCCESS
    }

    public static StepUpResult tryStepUp(LivingEntity entity, Vec3d velocity) {
        if(!entity.isOnGround()) return StepUpResult.NOT_ON_FLOOR;

        Vec3d horizontalDirection = new Vec3d(velocity.getX(), 0, velocity.getZ());

        Vec3d pos = entity.getPos();
        BlockHitResult bottomBlock = entity.getWorld().raycast(new RaycastContext(pos, pos.add(horizontalDirection), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        if(bottomBlock.getType() != HitResult.Type.BLOCK) return StepUpResult.TOO_SHORT;

        Vec3d topPos = entity.getPos().offset(Direction.UP, 1);
        BlockHitResult topBlock = entity.getWorld().raycast(new RaycastContext(topPos, topPos.add(horizontalDirection), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity));
        if(topBlock.getType() == HitResult.Type.BLOCK) return StepUpResult.TOO_TALL;

        entity.refreshPositionAfterTeleport(entity.getX(), entity.getY() + 1, entity.getZ());
        return StepUpResult.SUCCESS;
    }

    public static void lifesteal(LivingEntity attacker, LivingEntity target, float value) {
        if(target instanceof ArmorStandEntity) return;
        attacker.heal((float) HelperMethods.getEntityAttackDamage(attacker) * value);
    }
}
