package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.joml.Vector3f;

public class RiftAreaEffectCloudEntity extends AreaEffectCloudEntity {

    int color;

    Vector3f[] colours = new Vector3f[] {
            new Vector3f(1, 1, 1),
            new Vector3f(1, 0.667f, 0),
            new Vector3f(1, 0.333f, 1),
            new Vector3f(0.333f,1, 1),
            new Vector3f(1, 1, 0.333f),
            new Vector3f(0.333f,1, 0.333f),
            new Vector3f(1, 0.75f, 0.8f),
            new Vector3f(1, 0.333f, 0.333f),
            new Vector3f(0.667f,0.667f, 0.667f),
            new Vector3f(0, 0.667f, 0.667f),
            new Vector3f(0.667f,0, 0.667f),
            new Vector3f(0.333f,0.333f, 1),
            new Vector3f(0.667f,0.333f, 0),
            new Vector3f(0, 0.667f, 0),
            new Vector3f(0.667f,0, 0),
            new Vector3f(0, 0, 0)
    };

    public RiftAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner, int color) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.ASH, 0.25f, 0, 0, owner, 300);
        this.color = color;
    }

    @Override
    public void tick() {
        super.tick();

        if (getOwner() == null || getWorld().getDimension() != getOwner().getWorld().getDimension()) {
            discard();
            return;
        }

        if (squaredDistanceTo(getOwner().getX(), getOwner().getY(), getOwner().getZ()) > 225) {
            discard();
            return;
        }

        DustParticleEffect bigParticle = new DustParticleEffect(colours[color], 3);
        DustParticleEffect smallParticle = new DustParticleEffect(colours[color], 1);

        ((ServerWorld) getWorld()).spawnParticles(bigParticle, getX(), getY(), getZ(), 2, 0.2, 0.2, 0.2, 0.3);

        Box box = new Box(getX() - 13, getY() - 13, getZ() - 13, getX() + 13, getY() + 13, getZ() + 13);
        for (LivingEntity entity : getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
            if (entity == getOwner() || entity.isTeammate(getOwner())) {
                continue;
            }

            double distanceSquared = squaredDistanceTo(entity.getX(), entity.getY(), entity.getZ());
            double entityDistanceX = entity.getX() - getX();
            double entityDistanceY = entity.getY() - getY();
            double entityDistanceZ = entity.getZ() - getZ();

            if (distanceSquared < 100) {
                entityDistanceX /= 20;
                entityDistanceY /= 20;
                entityDistanceZ /= 20;

                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1));

                for (int i = 0; i < 20; i++) {
                    ((ServerWorld) getWorld()).spawnParticles(smallParticle, getX() + (entityDistanceX * i), getY() + (entityDistanceY * i), getZ() + (entityDistanceZ * i), 1, 0, 0, 0, 0);
                }
            } else {

                entity.setVelocity(entityDistanceX / -10, entityDistanceY / -10, entityDistanceZ / -10);
                if (entity instanceof PlayerEntity player) {
                    player.velocityModified = true;
                }
            }
        }
    }

}
