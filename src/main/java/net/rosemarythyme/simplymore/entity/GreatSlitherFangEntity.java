package net.rosemarythyme.simplymore.entity;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

public class GreatSlitherFangEntity extends EvokerFangsEntity {
    int warmup;
    boolean startedAttack;
    int ticksLeft = 22;
    boolean playingAnimation;
    LivingEntity owner;

    public GreatSlitherFangEntity(World world, double x, double y, double z, float yaw, int warmup, LivingEntity owner) {
        super(world, x, y, z, yaw, warmup, owner);
        this.owner = owner;
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            playingAnimation = true;
            if (!isSilent()) {
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, getSoundCategory(), 1.0F, random.nextFloat() * 0.2F + 0.85F, false);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            if (playingAnimation) {
                ticksLeft--;
                if (ticksLeft == 14) {
                    spawnParticles();
                }
            }
        } else if (--warmup < 0) {
            if (warmup == -8) {
                damageEntities();
            }

            if (!startedAttack) {
                this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
                startedAttack = true;
            }

            if (--ticksLeft < 0) {
                discard();
            }
        }
    }

    private void spawnParticles() {
        for (int i = 0; i < 12; i++) {
            double xOffset = (random.nextDouble() * 2.0 - 1.0) * getWidth() * 0.5;
            double yOffset = 0.05 + random.nextDouble();
            double zOffset = (random.nextDouble() * 2.0 - 1.0) * getWidth() * 0.5;
            double velocityX = (random.nextDouble() * 2.0 - 1.0) * 0.3;
            double velocityY = 0.3 + random.nextDouble() * 0.3;
            double velocityZ = (random.nextDouble() * 2.0 - 1.0) * 0.3;
            this.getWorld().addParticle(ParticleTypes.CRIT, getX() + xOffset, getY() + yOffset + 1.0, getZ() + zOffset, velocityX, velocityY, velocityZ);
        }
    }

    private void damageEntities() {
        for (LivingEntity livingEntity : this.getWorld().getNonSpectatingEntities(LivingEntity.class, getBoundingBox().expand(0.2, 0.0, 0.2))) {
            damage(livingEntity);
        }
    }

    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;

    private void damage(LivingEntity target) {
        float damageAmount = effect.getSlitherFangsDamage();
        int venomTime = effect.getSlitherFangsVenomTime();
        if (target.isAlive() && !target.isInvulnerable() && target != owner) {
            if (owner == null) {
                target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.VENOM, venomTime, 0), null);
                target.damage(getDamageSources().playerAttack(null), damageAmount);
            } else {
                if (owner.isTeammate(target)) {
                    return;
                }

                target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.VENOM, venomTime, 0), owner);
                target.damage(getDamageSources().playerAttack(((PlayerEntity) owner)), damageAmount);
            }
        }
    }
}
