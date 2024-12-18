package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class BloomEffect extends StatusEffect {

    public BloomEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        int duration = entity.getStatusEffect(this).getDuration();
        int interval = 0;

        switch (Amplifier) {
            case 0:
            case 1:
            case 2:
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,20,0));
                interval = 50;
                break;
            case 3:
            case 4:
            case 5:
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,20,0));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,20,0));
                interval = 50;
                break;
            case 6:
            case 7:
            case 8:
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,20,1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,20,0));
                interval = 50;
                break;
            case 9:
            case 10:
            case 11:
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,20,1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,20,0));
                interval = 25;
                break;
            default:
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,20,1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,20,1));
                interval = 25;
                break;
        }

        if (duration % interval == 0) {
            entity.heal(1);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
