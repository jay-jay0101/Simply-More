package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class Chill extends StatusEffect {


    public Chill(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        int m = entity.getFrozenTicks();
        entity.inPowderSnow = true;
        entity.setFrozenTicks(Math.min(entity.getMinFreezeDamageTicks(), m + 1));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
