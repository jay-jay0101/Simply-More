package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.ConfigUtils;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

import java.util.List;

public class AuraOfPurityAreaEffectCloudEntity extends AreaEffectCloudEntity {
    public static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;

    public AuraOfPurityAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.GLOW_SQUID_INK, 2, 0.03f, 0, owner, 200);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity owner = this.getOwner();
        List<LivingEntity> targets = AttackUtils.getAllies(owner, this.getBoundingBox());

        for (LivingEntity target : targets) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 15, 1));
            List.copyOf(target.getStatusEffects()).forEach(effect -> {
                if (effect.getEffectType().value().getCategory() != StatusEffectCategory.HARMFUL) return;
                if (ConfigUtils.isEffectBlacklisted(effect.getEffectType(), UNIQUE_CONFIG.holylight.blacklist, UNIQUE_CONFIG.holylight.includeGlobalBlacklist)) return;

                target.removeStatusEffect(effect.getEffectType());
            });
        }
    }
}
