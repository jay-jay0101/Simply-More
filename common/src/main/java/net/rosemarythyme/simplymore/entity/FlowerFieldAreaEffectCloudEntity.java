package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

import java.util.List;

public class FlowerFieldAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public FlowerFieldAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.TOTEM_OF_UNDYING, 8, 0f, 0, owner, 300);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity owner = this.getOwner();
        List<LivingEntity> targets = AttackUtils.cuboidAttack(owner, this.getBoundingBox());
        
        for (LivingEntity target : targets) {
            if(this.age % 25 == 0) {
                target.heal(1);
            }
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 50, 1));
        }
    }
}
