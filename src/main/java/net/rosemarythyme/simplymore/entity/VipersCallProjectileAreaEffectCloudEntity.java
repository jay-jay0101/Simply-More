package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

import java.util.List;

public class VipersCallProjectileAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public VipersCallProjectileAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.ASH, 3.3f, 0, 0, owner, 360);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity livingEntity = this.getOwner();
        if (livingEntity == null) return;

        List<LivingEntity> entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());
        for (LivingEntity target : entities) {
            if (target.isAlive() && !target.isInvulnerable() && !target.isTeammate(livingEntity)) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0));
                target.damage(this.getDamageSources().magic(), 0.7f);
            }
        }

        float radius = this.getRadius();
        double angleIncrement = Math.toRadians(360.0 / 30.0);
        for (int i = 0; i < 30; i++) {
            double angle = i * angleIncrement;
            double xPos = this.getX() + Math.sin(angle) * radius;
            double zPos = this.getZ() + Math.cos(angle) * radius;

            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.COMPOSTER, xPos, this.getY(), zPos, 3, 0.1, 0.1, 0.1, 0.1);
        }
    }

}
