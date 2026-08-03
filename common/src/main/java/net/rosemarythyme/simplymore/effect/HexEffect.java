package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;

import java.util.function.Predicate;

public class HexEffect extends StatusEffect {

    public static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.UNIQUE;

    public HexEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.getWorld().isClient) return super.applyUpdateEffect(entity, amplifier);

        RegistryEntry<StatusEffect> hex = StatusEffectRegistry.getReference(StatusEffectRegistry.HEX);
        StatusEffectInstance effect = entity.getStatusEffect(hex);

        if(effect.getDuration() % 100 == 5) {
            EntityUtils.incrementEffect(entity, hex, 1, 20);
            AudioVisualUtils.particleAroundEntity(entity, ParticleTypes.SOUL, 10, 0, 0.1d);
        }

        TargetList target = new TargetList(entity);

        if(amplifier >= 1) {
            target.applyEffect(StatusEffects.SLOWNESS, 10, 0)
                    .applyEffect(StatusEffects.MINING_FATIGUE, 10, 0);
        }

        if(amplifier >= 2) {
            target.applyEffect(StatusEffects.SLOWNESS, 10, 1)
                    .applyEffect(StatusEffects.MINING_FATIGUE, 10, 1);
        }

        if(amplifier >= 3) {
            target.applyEffect(StatusEffects.WEAKNESS, 10, 0)
                    .applyEffect(StatusEffects.NAUSEA, 80, 0);
        }

        Predicate<StatusEffect> blacklistPredicate = PredicateUtils.createForEffectBlacklist(UNIQUE_CONFIG.culterex.blacklist, UNIQUE_CONFIG.culterex.includeGlobalBlacklist);

        if(amplifier >= 4) {
            target.addDurationToStatusEffect(
                    blacklistPredicate.and(PredicateUtils.HARMFUL_EFFECT).and((status) -> status != hex.value()), 1);
        }

        if(amplifier >= 5) {
            target.applyEffect(StatusEffects.BLINDNESS, 10, 0)
                    .applyEffect(StatusEffects.WEAKNESS, 10, 1)
                    .removeStatusEffects(blacklistPredicate.and(PredicateUtils.BENEFICIAL_EFFECT));
        }

        if(amplifier >= 6) {
            target.applyEffect(StatusEffects.SLOWNESS, 10, 2);
        }

        if(amplifier >= 8) {
            target.removeStatusEffect(hex);
        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
