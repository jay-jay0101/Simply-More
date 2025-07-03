package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

import java.util.List;

public class HexEffect extends StatusEffect {

    public HexEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int Amplifier) {
        StatusEffectInstance effect = entity.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX));
        if(entity.getWorld().isClient)
            return super.applyUpdateEffect(entity, Amplifier);


        if(effect.getDuration() % 100 == 5) {
            entity.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.getReference(ModEffectsRegistry.HEX),
                            effect.getDuration(),
                            effect.getAmplifier() + 1
                    )
            );

            ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.SOUL, entity.getX(),entity.getY(),entity.getZ(),10,0,0,0,0.1f);
        }

        // Status Changes
        switch (effect.getAmplifier()) {
            case 0:
                break;
            case 1:
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                10,
                                0
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.MINING_FATIGUE,
                                10,
                                0
                        )
                );
            case 2:
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.MINING_FATIGUE,
                                10,
                                1
                        )
                );

                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.WEAKNESS,
                                10,
                                0
                        )
                );
                break;
            case 3:
            case 4:
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.MINING_FATIGUE,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.WEAKNESS,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.NAUSEA,
                                80,
                                0
                        )
                );
                break;
            case 5:
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.BLINDNESS,
                                10,
                                0
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.MINING_FATIGUE,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.WEAKNESS,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.NAUSEA,
                                80,
                                0
                        )
                );
                break;
            default:
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                10,
                                2
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.BLINDNESS,
                                10,
                                0
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.MINING_FATIGUE,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.WEAKNESS,
                                10,
                                1
                        )
                );
                entity.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.NAUSEA,
                                80,
                                0
                        )
                );
                break;
        }

        // Other effects
        if(effect.getAmplifier() >= 4) {
            List<StatusEffectInstance> negativeEffects = entity.getStatusEffects().stream().filter(
                    statusEffectInstance -> !statusEffectInstance.getEffectType().value().isBeneficial() &&
                            statusEffectInstance.getEffectType() != ModEffectsRegistry.getReference(ModEffectsRegistry.HEX)
            ).toList();

            negativeEffects.forEach(
                    statusEffectInstance -> {
                        entity.addStatusEffect(
                                new StatusEffectInstance(
                                        statusEffectInstance.getEffectType(),
                                        statusEffectInstance.getDuration() + 1,
                                        statusEffectInstance.getAmplifier()
                                )
                        );
                    }
            );
        }

        if(effect.getAmplifier() >= 5) {
            List<StatusEffectInstance> positiveEffects = entity.getStatusEffects().stream().filter(
                    statusEffectInstance -> statusEffectInstance.getEffectType().value().isBeneficial()
            ).toList();

            positiveEffects.forEach(
                    statusEffectInstance -> {
                        entity.removeStatusEffect(statusEffectInstance.getEffectType());
                    }
            );
        }

        if(effect.getAmplifier() >= 8) {
            entity.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX));
        }

        return super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
