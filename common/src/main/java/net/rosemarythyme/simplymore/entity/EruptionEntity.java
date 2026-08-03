package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MagmaseepItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import org.jetbrains.annotations.NotNull;

public class EruptionEntity extends AbstractAbilityPlacementEntity {
    public EruptionEntity(EntityType<EruptionEntity> entityType, World world) {
        super(entityType, world);
    }

    public EruptionEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.ERUPTION.get());
    }

    @Override
    public int getLifespan() {
        return MagmaseepItem.SETTINGS.smokeDuration;
    }

    private void visual(float range) {
        int count = (int) Math.floor(120 * range);
        AudioVisualUtils.particleCuboid(getServerWorld(), this.getPos(), ParticleTypes.SMOKE, range, 2, count, 0.015f);
    }

    private void blind(float range, LivingEntity owner) {
        AttackUtils.cuboidAttack(owner, this.getPos(), range, 3f, AttackUtils.AttackTarget.ENEMIES)
                .applyEffect(StatusEffects.BLINDNESS, 30, 0);
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        float range = MathUtils.clampedLerp(age, 0, 20, 0, (float) MagmaseepItem.SETTINGS.smokeRange);
        visual(range);
        blind(range, owner);
    }

    @Override
    public int getOutroTicks() {
        return 0;
    }

    @Override
    public int getIntroTicks() {
        return 0;
    }
}
