package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

public class FlowerFieldAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public FlowerFieldAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.TOTEM_OF_UNDYING, 8, 0f, 0, owner, 300);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity owner = this.getOwner();
        for (LivingEntity target : this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox())) {
            if (target.isAlive() && (target == owner || target.isTeammate(owner))) {
                if(this.age % 25 == 0) {
                    target.heal(1);
                }
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 50, 1));
            }
        }
    }
}
