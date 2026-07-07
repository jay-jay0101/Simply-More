package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import org.jetbrains.annotations.NotNull;

public class AuraOfPurityEntity extends AbstractAbilityPlacementEntity {
    public static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;

    public AuraOfPurityEntity(EntityType<AuraOfPurityEntity> entityType,  World world) {
        super(entityType, world);
    }

    public AuraOfPurityEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.AURA_OF_PURITY.get());
    }


//    public AuraOfPurityEntity(World world, double x, double y, double z, LivingEntity owner) {
//        super(world, x, y, z);
//        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.GLOW_SQUID_INK, 2, 0.03f, 0, owner, 200);
//    }

    @Override
    int getLifespan() {
        return 200;
    }

    @Override
    public void tick() {
        super.tick();

        ((ServerWorld) getWorld()).spawnParticles(ParticleTypes.GLOW, this.getX(), this.getY(), this.getZ(), 10, 0, 0, 0, 0);
//        LivingEntity owner = this.getOwner();
//        List<LivingEntity> targets = AttackUtils.getAllies(owner, this.getBoundingBox());
//
//        for (LivingEntity target : targets) {
//            target.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 15, 1));
//            List.copyOf(target.getStatusEffects()).forEach(effect -> {
//                if (effect.getEffectType().value().getCategory() != StatusEffectCategory.HARMFUL) return;
//                if (ConfigUtils.isEffectBlacklisted(effect.getEffectType(), UNIQUE_CONFIG.holylight.blacklist, UNIQUE_CONFIG.holylight.includeGlobalBlacklist)) return;
//
//                target.removeStatusEffect(effect.getEffectType());
//            });
//        }
    }
}
