package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.EntityUtils;

public class LanceEffect extends StatusEffect {

    public LanceEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!EntityUtils.shouldGrantLanceEffect(livingEntity))
            livingEntity.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LANCE));
        return super.applyUpdateEffect(livingEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
