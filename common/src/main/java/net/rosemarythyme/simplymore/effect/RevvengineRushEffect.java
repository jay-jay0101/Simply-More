package net.rosemarythyme.simplymore.effect;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.entity.KickbackAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.uniques.RevvengineItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.List;

public class RevvengineRushEffect extends StatusEffect {

    public RevvengineRushEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    protected static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);

        // Sound
        if(entity.age % 2 == 0)
            entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundRegistry.MAGIC_BOW_PULL_BACK_SHORT_VERSION_02.get(), SoundCategory.PLAYERS, 1, 1.2f);

        // Movement
        Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised2dVector(entity.getYaw());
        entity.setVelocity(normalisedVector.x() * 0.6, entity.getVelocity().getY(), normalisedVector.z()  * 0.6);
        entity.velocityModified = true;

        // Particles
        if (!entity.getWorld().isClient) {
            if(amplifier > 0) {
                ((ServerWorld) entity.getWorld()).spawnParticles(
                        new DustParticleEffect(
                                new Vector3f(0f,0f,0f),
                                1
                        ),
                        entity.getX(),
                        entity.getEyeY(),
                        entity.getZ(),
                        10,
                        0.3f,
                        0.3f,
                        0.3f,
                        0.3f
                );

                ((ServerWorld) entity.getWorld()).spawnParticles(
                        ParticleTypes.LAVA,
                        entity.getX(),
                        entity.getEyeY(),
                        entity.getZ(),
                        3,
                        0.3f,
                        0.3f,
                        0.3f,
                        0.3f
                );
            } else {
                ((ServerWorld) entity.getWorld()).spawnParticles(
                        new DustParticleEffect(
                                new Vector3f(0.5f,0.5f,0.5f),
                                1
                        ),
                        entity.getX(),
                        entity.getEyeY(),
                        entity.getZ(),
                        5,
                        0.3f,
                        0.3f,
                        0.3f,
                        0.3f
                );
            }
        }

        // Hitting Enemies Detection
        Vec3d position = entity.getEyePos();

        Vec3d particlePos = new Vec3d(
                position.getX() + normalisedVector.x(),
                position.getY(),
                position.getZ() + normalisedVector.z()
        );

        Box box = new Box(
                particlePos.getX() - 1,
                particlePos.getY() - 1,
                particlePos.getZ() - 1,
                particlePos.getX() + 1,
                particlePos.getY() + 1,
                particlePos.getZ() + 1

        );

        List<LivingEntity> entities = entity.getWorld().getNonSpectatingEntities(LivingEntity.class, box).stream().filter(
                livingEntity -> (livingEntity != entity && !livingEntity.isTeammate(entity))).toList();

        if(!entities.isEmpty()) {
            if(amplifier > 0) {
                causeSlash(
                        entity,
                        effect.getRevvenginePhase3EffectTime(),
                        effect.getRevvenginePhase3Damage(),
                        true
                );
            } else {
                causeSlash(
                        entity,
                        effect.getRevvenginePhase2EffectTime(),
                        effect.getRevvenginePhase2Damage(),
                        false
                );
            }
        }

        // On End Effect
        if (entity.hasStatusEffect(this) && entity.getStatusEffect(this).getDuration() < 10) {
            if(amplifier > 0) {
                causeSlash(
                        entity,
                        effect.getRevvenginePhase3EffectTime(),
                        effect.getRevvenginePhase3Damage(),
                        true
                );
            } else {
                causeSlash(
                        entity,
                        effect.getRevvenginePhase2EffectTime(),
                        effect.getRevvenginePhase2Damage(),
                        false
                );
            }
        }

        // Explosions if Phase3
        if(amplifier > 0 && entity.age % 5 == 0) {
            entity.getWorld().spawnEntity(
                    new KickbackAreaEffectCloudEntity(
                        entity.getWorld(),
                        entity.getX(),
                        entity.getEyeY()-0.5,
                        entity.getZ(),
                        1,
                        entity
                    )
            );
        }
    }

    public void causeSlash(LivingEntity user, int effectTime, float damage, boolean isTier3) {
        if(user.getWorld().isClient) return;

        user.removeStatusEffect(this);
        Vec3d position = user.getEyePos();
        Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised2dVector(user.getYaw());

        Vec3d particlePos = new Vec3d(
                position.getX() + normalisedVector.x(),
                position.getY(),
                position.getZ() + normalisedVector.z()
        );

        Box box = new Box(
                particlePos.getX() - 1,
                particlePos.getY() - 1,
                particlePos.getZ() - 1,
                particlePos.getX() + 1,
                particlePos.getY() + 1,
                particlePos.getZ() + 1

        );
        for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
            if (livingEntity.isTeammate(user) || livingEntity == user || livingEntity.isInvulnerable()) continue;

            livingEntity.damage(
                    user.getDamageSources().playerAttack((PlayerEntity) user),
                    RevvengineItem.getHealthModifiedValue(user,
                            effect.getRevvengineMaxDamagePercentageBuff(),
                            damage) + damage
            );

            livingEntity.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.BLEED.get(),
                            effectTime,
                            0
                    )
            );

            livingEntity.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.BLINDNESS,
                            effectTime,
                            0
                    )
            );

            if(isTier3) {
                livingEntity.setOnFireFor(effectTime/20);
            }

            user.getWorld().playSound(null, particlePos.getX(), particlePos.getY(), particlePos.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1,0.5f);


            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, particlePos.getX(), particlePos.getY(), particlePos.getZ(), 1, 0, 0 , 0, 0);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
