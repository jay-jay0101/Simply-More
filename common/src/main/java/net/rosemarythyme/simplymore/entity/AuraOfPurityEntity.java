package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.ParticleRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.ConfigUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AuraOfPurityEntity extends AbstractAbilityPlacementEntity {
    public static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;

    public AuraOfPurityEntity(EntityType<AuraOfPurityEntity> entityType,  World world) {
        super(entityType, world);
    }

    public AuraOfPurityEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.AURA_OF_PURITY.get());
    }

    @Override
    int getLifespan() {
        return 200;
    }

    public void visual(float range) {
        ServerWorld world = (ServerWorld) getWorld();
        VisualEffectsUtils.particleRing(world, this.getPos(), ParticleRegistry.HOLY_WATER.get(), range, 100);

        float pulseRadius = MathUtils.clampedLerp(age % 20, 0, 20, 0, 4f);

        if(pulseRadius == 0) {
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_SWIM, SoundCategory.PLAYERS, 1, 1);
        }

        VisualEffectsUtils.particleRing(world, this.getPos(), ParticleRegistry.HOLY_WATER.get(), pulseRadius, 100);
    }

    @Override
    public void tick() {
        super.tick();

        float range = MathUtils.clampedLerp(age, 0, 20, 0, 4f);
        visual(range);

        LivingEntity owner = this.getOwner();
        List<LivingEntity> targets = AttackUtils.cylinderAttack(owner, this.getPos(), range, 2, AttackUtils.AttackTarget.ALLIES_AND_USER);

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
