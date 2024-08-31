package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

public class AuraOfPurityAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public AuraOfPurityAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.GLOW_SQUID_INK, 2, 0.03f, 0, owner, 200);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity owner = this.getOwner();
        for (LivingEntity target : this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox())) {
            if (target.isAlive() && (target == owner || target.isTeammate(owner))) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 15, 0));
                target.getStatusEffects().removeIf(effect -> effect.getEffectType().getCategory() == StatusEffectCategory.HARMFUL);
            }
        }
    }
}
