package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import org.jetbrains.annotations.NotNull;

public class AuraOfCorruptionEntity extends AbstractAbilityPlacementEntity {
    public static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;

    public AuraOfCorruptionEntity(EntityType<AuraOfCorruptionEntity> entityType, World world) {
        super(entityType, world);
    }

    public AuraOfCorruptionEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.AURA_OF_CORRUPTION.get());
    }

    @Override
    int getLifespan() {
        return 200;
    }

    public void visual(float range) {
        ServerWorld world = (ServerWorld) getWorld();
        AudioVisualUtils.particleRing(world, this.getPos(), ParticleTypes.SQUID_INK, range, 100);
    }

    @Override
    public void tick() {
        super.tick();

        float range = MathUtils.clampedLerp(age, 0, 20, 0, 4f);
        visual(range);

        LivingEntity owner = this.getOwner();
        AttackUtils.cylinderAttack(owner, this.getPos(), range, 2, AttackUtils.AttackTarget.ALLIES_AND_USER)
            .applyDurationDependantEffect(StatusEffects.WITHER, 20, 1)
            .applyEffect(StatusEffects.WEAKNESS, 40, 0);
    }
}
