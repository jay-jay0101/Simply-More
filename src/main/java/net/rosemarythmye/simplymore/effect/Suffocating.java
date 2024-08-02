package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class Suffocating extends StatusEffect {

    public Suffocating(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        entity.damage(entity.getDamageSources().inWall(), 1.0F);
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int Duration, int Amplifier) {
        int i = 50 >> Amplifier;
        return i == 0 || Duration % i == 0;
    }


}
