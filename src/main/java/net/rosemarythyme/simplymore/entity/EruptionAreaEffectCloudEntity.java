package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

import java.util.List;

public class EruptionAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public EruptionAreaEffectCloudEntity(World world, double x, double y, double z, int radius, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.SMOKE, radius, 0, 0, owner, 200);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity livingEntity = this.getOwner();
        List<LivingEntity> entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());
        for (LivingEntity target : entities) {
            if (target.isAlive() && !target.isInvulnerable() && target != livingEntity) {
                if (livingEntity == null || !livingEntity.isTeammate(target)) {
                    target.damage(this.getDamageSources().inFire(), 1.0F);
                    target.setOnFireFor(3);
                }
            }
        }
    }

}
