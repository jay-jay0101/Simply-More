package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.joml.Vector3f;

public class JetAreaEffectCloudEntity extends AreaEffectCloudEntity {


    public JetAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.ASH, 0.25f, 0, 0, owner, 1200);
    }

    @Override
    public void tick() {
        super.tick();


        DustParticleEffect dustParticleEffect = new DustParticleEffect(new Vector3f(1f, 1f, 1f), 2);

        ((ServerWorld) getWorld()).spawnParticles(dustParticleEffect, getX(), getY() + 1.5, getZ(), 15, 0.25, 1.5, 0.25, 0.3);
        getWorld().playSound(null, getX(), getY(), getZ(), SoundEvents.ENTITY_HORSE_BREATHE, SoundCategory.AMBIENT,0.6f,0.4f);

        Box box = new Box(getX() - 0.5, getY() - 1, getZ() - 0.5, getX() + 0.5, getY() + 6.25, getZ() + 0.5);
        for (LivingEntity entity : getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {

            if(entity.getVelocity().getY() < 0) {
                entity.addVelocity(0d,0.45d,0d);
            }

            entity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.LIGHTWEIGHT), 35, 0));

            entity.addVelocity(new Vec3d(0d, 0.1d, 0d));
            entity.velocityModified = true;
        }
    }

}
