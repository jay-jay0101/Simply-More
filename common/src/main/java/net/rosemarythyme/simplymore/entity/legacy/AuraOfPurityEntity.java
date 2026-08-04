package net.rosemarythyme.simplymore.entity.legacy;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.AbstractAbilityPlacementEntity;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import org.jetbrains.annotations.NotNull;

public class AuraOfPurityEntity extends AbstractAbilityPlacementEntity {
    public AuraOfPurityEntity(EntityType<AuraOfPurityEntity> entityType,  World world) {
        super(entityType, world);
    }

    public AuraOfPurityEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.AURA_OF_PURITY.get());
    }

    @Override
    public int getOutroTicks() {
        return 0;
    }

    @Override
    public int getIntroTicks() {
        return 0;
    }

    @Override
    public int getLifespan() {
        return 200;
    }

    public void visual(float range) {
        ServerWorld world = (ServerWorld) getWorld();

        float pulseRadius = MathUtils.clampedLerp(age % 20, 0, 20, 0, 4f);

        if(pulseRadius == 0) {
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_GENERIC_SWIM, SoundCategory.PLAYERS, 1, 1);
        }
    }

    @Override
    public void tick() {
        super.tick();

        float range = MathUtils.clampedLerp(age, 0, 20, 0, 4f);
        visual(range);

        LivingEntity owner = this.getOwner();
        AttackUtils.cylinderAttack(owner, this.getPos(), range, 2, AttackUtils.AttackTarget.ALLIES_AND_USER)
            .applyEffect(StatusEffects.STRENGTH, 15, 1)
            .removeStatusEffects(PredicateUtils.createForEffectBlacklist(null, false)
                    .and(PredicateUtils.HARMFUL_EFFECT)
        );
    }
}
