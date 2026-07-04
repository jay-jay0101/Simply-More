package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;

public class EntityUtils {
    public static boolean shouldGrantLanceEffect(LivingEntity entity) {
        return isLanceInMainHand(entity) && isRidingLivingEntity(entity) && isOffHandEmpty(entity);
    }

    private static boolean isLanceInMainHand(LivingEntity livingEntity) {

        return livingEntity.getMainHandStack().getItem() instanceof Weapon weapon && weapon.swordType() == Weapon.SwordTypes.LANCE;
    }

    private static boolean isRidingLivingEntity(LivingEntity entity) {
        return entity.getVehicle() instanceof LivingEntity;
    }

    private static boolean isOffHandEmpty(LivingEntity livingEntity) {
        return !(livingEntity.getStackInHand(Hand.OFF_HAND).getItem() instanceof ToolItem);
    }

    public static void reapplyAndIncrementEffect(LivingEntity entity, RegistryEntry<StatusEffect> effect, int duration, int additionalAmplifier, int maxAmplifier) {
        int amplifier = additionalAmplifier - 1;
        if(entity.hasStatusEffect(effect)) {
            amplifier = entity.getStatusEffect(effect).getAmplifier() + additionalAmplifier;
        }

        entity.addStatusEffect(new StatusEffectInstance(effect, duration, Math.min(amplifier, maxAmplifier)));
    }
}
