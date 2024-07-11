package net.rosemarythmye.simplymore.entity;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;

public class GreatSlitherFang extends EvokerFangsEntity {
    private int warmup;
    private boolean startedAttack;
    private int ticksLeft = 22;
    private boolean playingAnimation;

    public GreatSlitherFang(World world, double x, double y, double z, float yaw, int warmup, LivingEntity owner) {
        super(world, x, y, z, yaw, warmup, owner);
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            this.playingAnimation = true;
            if (!this.isSilent()) {
                this.getWorld()
                        .playSound(
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                SoundEvents.ENTITY_EVOKER_FANGS_ATTACK,
                                this.getSoundCategory(),
                                1.0F,
                                this.random.nextFloat() * 0.2F + 0.85F,
                                false
                        );
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            if (this.playingAnimation) {
                this.ticksLeft--;
                if (this.ticksLeft == 14) {
                    for (int i = 0; i < 12; i++) {
                        double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getWidth() * 0.5;
                        double e = this.getY() + 0.05 + this.random.nextDouble();
                        double f = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getWidth() * 0.5;
                        double g = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                        double h = 0.3 + this.random.nextDouble() * 0.3;
                        double j = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                        this.getWorld().addParticle(ParticleTypes.CRIT, d, e + 1.0, f, g, h, j);
                    }
                }
            }
        } else if (--this.warmup < 0) {
            if (this.warmup == -8) {
                for (LivingEntity livingEntity : this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.2, 0.0, 0.2))) {
                    this.damage(livingEntity);
                }
            }

            if (!this.startedAttack) {
                this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
                this.startedAttack = true;
            }

            if (--this.ticksLeft < 0) {
                this.discard();
            }
        }
    }


    private void damage(LivingEntity target) {
        LivingEntity livingEntity = this.getOwner();
        if (target.isAlive() && !target.isInvulnerable() && target != livingEntity) {
            if (livingEntity == null) {
                target.addStatusEffect(new StatusEffectInstance(ModEffects.VENOM,90,0),livingEntity);
                target.damage(this.getDamageSources().playerAttack(((PlayerEntity) livingEntity)), 4.0F);
            } else {
                if (livingEntity.isTeammate(target)) {
                    return;
                }

                target.addStatusEffect(new StatusEffectInstance(ModEffects.VENOM,90,0),livingEntity);
                target.damage(this.getDamageSources().playerAttack(((PlayerEntity) livingEntity)), 4.0F);
            }
        }
    }
    
}
