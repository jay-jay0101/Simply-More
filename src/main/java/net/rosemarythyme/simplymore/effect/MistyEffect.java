package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class MistyEffect extends StatusEffect {


    public MistyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity affectedEntity, int amplifier) {
        if (!affectedEntity.hasStatusEffect(this)) {
            return;
        }

        int effectDuration = affectedEntity.getStatusEffect(this).getDuration();

        if (effectDuration >= 9960
                && effectDuration < 9990
                && !affectedEntity.getWorld().isClient
                && affectedEntity instanceof PlayerEntity playerEntity) {

            float playerYaw = (float) Math.toRadians(playerEntity.getYaw() + 90);
            float playerPitch = (float) Math.toRadians(playerEntity.getPitch());

            float forwardDirectionX = (float) (Math.cos(playerYaw) * Math.cos(playerPitch));
            float forwardDirectionZ = (float) (Math.sin(playerYaw) * Math.cos(playerPitch));
            float forwardDirectionY = (float) Math.sin(playerPitch) * -1.0f;
            LivingEntity teleportTarget = findTeleportTarget(playerEntity, forwardDirectionX, forwardDirectionY, forwardDirectionZ);

            if (teleportTarget != null) {
                playerEntity.removeStatusEffect(this);
                double distanceToTarget = Math.sqrt(
                                  Math.pow(playerEntity.getX() - teleportTarget.getX(), 2)
                                + Math.pow(playerEntity.getY() - teleportTarget.getY(), 2)
                                + Math.pow(playerEntity.getZ() - teleportTarget.getZ(), 2)
                );
                distanceToTarget /= 10;
                spawnParticles(playerEntity, forwardDirectionX, forwardDirectionY, forwardDirectionZ, distanceToTarget);
                playerEntity.teleport(teleportTarget.getX(), teleportTarget.getY(), teleportTarget.getPos().getZ());
            }
        }

        if (affectedEntity.isOnGround() && effectDuration < 9980) {
            affectedEntity.removeStatusEffect(this);
        }

        if (!affectedEntity.getWorld().isClient) {
            ((ServerWorld) affectedEntity.getWorld()).spawnParticles(ParticleTypes.LARGE_SMOKE, affectedEntity.getX(), affectedEntity.getY() + 0.5, affectedEntity.getZ(), 5, 0.5, 0.5, 0.5, 0);
        }

        affectedEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 5));
        affectedEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 5));
        super.applyUpdateEffect(affectedEntity, amplifier);
    }

    private LivingEntity findTeleportTarget(PlayerEntity player, float offsetDirectionX, float offsetDirectionY, float offsetDirectionZ) {
        LivingEntity teleportTarget = null;
        double boxSize = 1.3d;
        for (int i = 10; i > 0; i--) {
            double x = player.getX() + (offsetDirectionX * i);
            double y = player.getY() + (offsetDirectionY * i);
            double z = player.getZ() + (offsetDirectionZ * i);
            for (LivingEntity target : player.getEntityWorld().getNonSpectatingEntities(
                    LivingEntity.class,
                    new Box(
                            x - boxSize,
                            y - boxSize,
                            z - boxSize,
                            x + boxSize,
                            y + boxSize,
                            z + boxSize
                    ))) {
                if (!player.canSee(target)) {
                    continue;
                }
                if (target == player || target.isTeammate(player)) {
                    continue;
                }
                if (teleportTarget == null) {
                    teleportTarget = target;

                    int amplifier = (int) Math.ceil(target.getMaxHealth() / 5);
                    if (amplifier > 20) {
                        amplifier = 20;
                    }
                    amplifier--;
                    target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.WITHERING_FATE, 600, amplifier));
                    target.damage(player.getDamageSources().playerAttack(player), 8);
                }
            }
        }
        return teleportTarget;
    }

    private void spawnParticles(PlayerEntity player, float offsetDirectionX, float offsetDirectionY, float offsetDirectionZ, double lineBetweenPlayerAndTarget) {
        for (int i = 0; i < 10; i++) {
            double x = player.getX() + (offsetDirectionX * i * lineBetweenPlayerAndTarget);
            double y = player.getY() + (offsetDirectionY * i * lineBetweenPlayerAndTarget);
            double z = player.getZ() + (offsetDirectionZ * i * lineBetweenPlayerAndTarget);
            ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, x, y, z, 10, 0.2, 0.2, 0.2, 0.2);
            player.getWorld().playSound(null, x, y, z, SoundRegistry.DARK_SWORD_ATTACK_01.get(), SoundCategory.PLAYERS, 1, 1);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
