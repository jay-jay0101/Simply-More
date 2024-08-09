package net.rosemarythmye.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AuraOfPurity extends AreaEffectCloudEntity {
    public AuraOfPurity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        this.setParticleType(ParticleTypes.GLOW_SQUID_INK);
        this.setRadius(2);
        this.setRadiusGrowth(0.03f);
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
                if(target != owner && !target.isTeammate(owner)) continue;
                List<StatusEffectInstance> removeList = new ArrayList<>();
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,15,0));
                for (StatusEffectInstance effect : target.getStatusEffects()) {
                    if(effect.getEffectType().getCategory() == StatusEffectCategory.HARMFUL) removeList.add(effect);
                }

                for(StatusEffectInstance effect : removeList) {
                    target.removeStatusEffect(effect.getEffectType());
                }
            }

        }
    }

}
