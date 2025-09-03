package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;

public class StarlightEffect extends StatusEffect {
    protected static UniqueEffectConfig effect = ConfigWrapper.unique;


    public StarlightEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity affectedEntity, int amplifier) {
        int frequency = effect.glimmerstep.baseSpeedFrequency - (effect.glimmerstep.speedFrequencyPerStack * (amplifier + 1));
        if(affectedEntity.getWorld().getTime() % frequency == 0) {
            affectedEntity.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.SPEED,
                            effect.glimmerstep.speedTime,
                            amplifier
                    )
            );

            if(affectedEntity.getVehicle() instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SPEED,
                                effect.glimmerstep.speedTime,
                                amplifier
                        )
                );
            }
        }

        return super.applyUpdateEffect(affectedEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
