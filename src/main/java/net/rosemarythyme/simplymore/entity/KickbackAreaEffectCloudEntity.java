package net.rosemarythyme.simplymore.entity;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.joml.Vector3f;

import java.util.List;

public class KickbackAreaEffectCloudEntity extends AreaEffectCloudEntity {

    protected static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;

    public KickbackAreaEffectCloudEntity(World world, double x, double y, double z, int radius, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, new DustParticleEffect(new Vector3f(0f,0f,0f), 5), radius, 0, 0, owner, effect.getRevvenginePhase3ExplosionWaitTime());
    }

    @Override
    public void tick() {
        super.tick();

        int timeTillDeath = effect.getRevvenginePhase3ExplosionWaitTime() - this.age;

        if(timeTillDeath > 10) {
            ((ServerWorld) getWorld()).spawnParticles(
                    new DustParticleEffect(
                            new Vector3f(0f, 0f, 0f),
                            5
                    ),
                    getX(),
                    getEyeY(),
                    getZ(),
                    8,
                    1f,
                    1f,
                    1f,
                    0.8f
            );

            ((ServerWorld) getWorld()).spawnParticles(
                    ParticleTypes.LAVA,
                    getX(),
                    getEyeY(),
                    getZ(),
                    5,
                    1f,
                    1f,
                    1f,
                    1.2f
            );
        }

        if(timeTillDeath == 3) {
            ((ServerWorld) getWorld()).spawnParticles(
                    ParticleTypes.EXPLOSION,
                    getX(),
                    getEyeY(),
                    getZ(),
                    15,
                    1f,
                    1f,
                    1f,
                    0.8f
            );

            getWorld().playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1,1);

            Box box = new Box(
                    getX() - 2,
                    getY() - 2,
                    getZ() - 2,
                    getX() + 2,
                    getY() + 2,
                    getZ() + 2

            );

            for (LivingEntity livingEntity : getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
                if (livingEntity.isTeammate(getOwner()) || livingEntity == getOwner() || livingEntity.isInvulnerable()) continue;

                livingEntity.damage(
                        getOwner().getDamageSources().explosion(this, getOwner()),
                        effect.getRevvenginePhase3ExplosionDamage()
                );

                livingEntity.setOnFireFor(effect.getRevvenginePhase3EffectTime());
            }
        }
    }

}
