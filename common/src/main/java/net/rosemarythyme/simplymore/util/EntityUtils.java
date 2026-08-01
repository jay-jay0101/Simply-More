package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityUtils {
    public static boolean isRidingLivingEntity(LivingEntity entity) {
        return entity.getVehicle() instanceof LivingEntity;
    }

    public static void reapplyAndIncrementEffect(LivingEntity entity, RegistryEntry<StatusEffect> effect, int duration, int additionalAmplifier, int maxAmplifier) {
        int amplifier = additionalAmplifier - 1;
        if(entity.hasStatusEffect(effect)) {
            amplifier = entity.getStatusEffect(effect).getAmplifier() + additionalAmplifier;
        }

        entity.addStatusEffect(new StatusEffectInstance(effect, duration, Math.min(amplifier, maxAmplifier)));
    }

    public static void incrementEffect(LivingEntity entity, RegistryEntry<StatusEffect> effect, int additionalAmplifier, int maxAmplifier) {
        if(!entity.hasStatusEffect(effect)) return;

        StatusEffectInstance effectInstance = entity.getStatusEffect(effect);

        int amplifier = effectInstance.getAmplifier() + additionalAmplifier;
        entity.addStatusEffect(new StatusEffectInstance(effect, effectInstance.getDuration(), Math.min(amplifier, maxAmplifier)));
    }

    public static boolean isHolding(LivingEntity entity, ItemStack stack) {
        return entity.getStackInHand(Hand.MAIN_HAND).equals(stack);
    }

    public static void spawnAround(World world, LivingEntity entity, Vec3d pos, double horizontalRange, double verticalRange) {
        double deltaX = (world.getRandom().nextDouble() * horizontalRange * 2) - horizontalRange;
        double deltaY = (world.getRandom().nextDouble() * verticalRange * 2) - verticalRange;
        double deltaZ = (world.getRandom().nextDouble() * horizontalRange * 2) - horizontalRange;

        entity.setPos(pos.getX() + deltaX, pos.getY() + deltaY, pos.getZ() + deltaZ);
        world.spawnEntity(entity);
    }
}
