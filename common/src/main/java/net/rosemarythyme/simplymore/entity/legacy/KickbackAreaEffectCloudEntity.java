package net.rosemarythyme.simplymore.entity.legacy;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.joml.Vector3f;

import java.util.List;

public class KickbackAreaEffectCloudEntity extends AreaEffectCloudEntity {

    protected static UniqueEffectConfig effect = ConfigWrapper.UNIQUE;

    public KickbackAreaEffectCloudEntity(World world, double x, double y, double z, int radius, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, new DustParticleEffect(new Vector3f(0f,0f,0f), 5), radius, 0, 0, owner, effect.revvengine.explosionWindup);
    }

    @Override
    public void tick() {
        super.tick();

        int timeTillDeath = effect.revvengine.explosionWindup - this.age;

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

            Box box = MathUtils.createCubeBox(getPos(), 2);
            List<LivingEntity> targets = AttackUtils.cuboidAttack(getOwner(), box);

            for (LivingEntity target : targets) {
                target.damage(
                        getOwner().getDamageSources().explosion(this, getOwner()),
                        effect.revvengine.explosionDamage
                );

                target.setOnFireFor(effect.revvengine.p3effectTime);
            }
        }
    }

}
