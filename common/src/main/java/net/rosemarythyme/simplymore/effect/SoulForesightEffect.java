package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.util.ConfigUtils;

public class SoulForesightEffect extends StatusEffect {
    public static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;

    public SoulForesightEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            if (effect.getEffectType().value().getCategory() != StatusEffectCategory.BENEFICIAL || effect.getDuration() <= 10) continue;
            if (ConfigUtils.isEffectBlacklisted(effect.getEffectType(), UNIQUE_CONFIG.soul_foreseer.blacklist, UNIQUE_CONFIG.soul_foreseer.includeGlobalBlacklist)) continue;
            entity.setStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration() - 8, effect.getAmplifier()), entity);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
