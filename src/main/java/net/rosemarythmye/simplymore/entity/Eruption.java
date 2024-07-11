package net.rosemarythmye.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

import java.util.List;

public class Eruption extends AreaEffectCloudEntity {
    public Eruption(World world, double x, double y, double z, int radius, LivingEntity owner) {
        super(world, x, y, z);
        this.setParticleType(ParticleTypes.SMOKE);
        this.setRadius(radius);
        this.setRadiusGrowth(0);
        this.setRadiusOnUse(0);
        this.setOwner(owner);
        this.setDuration(200);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity livingEntity = this.getOwner();
        List<LivingEntity> entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());
        for (LivingEntity target : entities) {
            if (target.isAlive() && !target.isInvulnerable() && target != livingEntity) {
                if (livingEntity == null) {
                    target.damage(this.getDamageSources().inFire(), 1.0F);
                    target.setOnFireFor(3);
                } else {
                    if (livingEntity.isTeammate(target)) {
                        return;
                    }
                    target.damage(this.getDamageSources().inFire(), 1.0F);
                    target.setOnFireFor(3);
                }
            }
        }
    }

}
