package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class AuraOfCorruptionAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public AuraOfCorruptionAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        this.setParticleType(ParticleTypes.SQUID_INK);
        this.setRadius(8);
        this.setRadiusGrowth(-0.03f);
        this.setRadiusOnUse(0);
        this.setOwner(owner);
        this.setDuration(200);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity owner = this.getOwner();
        for (LivingEntity target : this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox())) {
            if (target.isAlive()) {
                if (target == owner || target.isTeammate(owner)) continue;
                if (!target.hasStatusEffect(StatusEffects.WITHER)) target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,20,1));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,40,0));
            }
        }
    }

}
