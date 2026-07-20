package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.util.data.TargetList;

public class StarlightEffect extends StatusEffect {
    protected static UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;

    public StarlightEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        int frequency = UNIQUE_CONFIG.glimmerstep.baseSpeedFrequency - (UNIQUE_CONFIG.glimmerstep.speedFrequencyPerStack * (amplifier + 1));

        if(entity.getWorld().getTime() % frequency == 0) {
            new TargetList(entity).include(entity.getVehicle())
                    .applyEffect(StatusEffects.SPEED, UNIQUE_CONFIG.glimmerstep.speedTime, amplifier);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
