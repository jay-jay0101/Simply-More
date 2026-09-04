package net.rosemarythyme.simplymore.entity.legacy;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.AttackUtils;

import java.util.List;

public class FlowerFieldAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public FlowerFieldAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
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
