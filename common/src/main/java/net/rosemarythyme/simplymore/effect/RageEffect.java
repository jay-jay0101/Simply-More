package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class RageEffect extends StatusEffect {


    public RageEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        entity.damage(entity.getDamageSources().genericKill(), 1.0F);
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,7,4));
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 5 == 0;
    }

}
