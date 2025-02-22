package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class SoulForesightEffect extends StatusEffect {

    public SoulForesightEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            if (effect.getEffectType().getCategory() != StatusEffectCategory.BENEFICIAL || effect.getDuration() <= 10) continue;
            entity.setStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration() - 8, effect.getAmplifier()), entity);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
