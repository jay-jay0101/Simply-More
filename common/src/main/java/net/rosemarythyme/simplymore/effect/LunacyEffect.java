package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.world.MobLunacyManager;

public class LunacyEffect extends StatusEffect {
    public static final int LUNACY_SUBEFFECT_DURATION = 80;

    public LunacyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getWorld().isClient() && entity instanceof MobEntity mob) {
            StatusEffectInstance instance = mob.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LUNACY));
            if(instance != null) MobLunacyManager.tickEffect(mob, instance);
        }

        return true;
    }
}
