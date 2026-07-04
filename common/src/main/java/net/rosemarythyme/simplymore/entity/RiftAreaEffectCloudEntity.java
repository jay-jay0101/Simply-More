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
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.joml.Vector3f;

import java.util.List;

public class RiftAreaEffectCloudEntity extends AreaEffectCloudEntity {

    Vector3f color;

    public RiftAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner, Vector3f color) {
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

        DustParticleEffect bigParticle = new DustParticleEffect(color, 3);
        DustParticleEffect smallParticle = new DustParticleEffect(color, 1);

        ((ServerWorld) getWorld()).spawnParticles(bigParticle, getX(), getY(), getZ(), 2, 0.2, 0.2, 0.2, 0.3);

        Box box = MathUtils.createCubeBox(getPos(), 13);
        List<LivingEntity> targets = AttackUtils.getTargets(getOwner(), box);
        for (LivingEntity target : targets) {

            double distanceSquared = squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            double entityDistanceX = target.getX() - getX();
            double entityDistanceY = target.getY() - getY();
            double entityDistanceZ = target.getZ() - getZ();

            if (distanceSquared < 100) {
                entityDistanceX /= 20;
                entityDistanceY /= 20;
                entityDistanceZ /= 20;

                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1));

                for (int i = 0; i < 20; i++) {
                    ((ServerWorld) getWorld()).spawnParticles(smallParticle, getX() + (entityDistanceX * i), getY() + (entityDistanceY * i), getZ() + (entityDistanceZ * i), 1, 0, 0, 0, 0);
                }
            } else {

                target.setVelocity(entityDistanceX / -10, entityDistanceY / -10, entityDistanceZ / -10);
                if (target instanceof PlayerEntity player) {
                    player.velocityModified = true;
                }
            }
        }
    }

}
