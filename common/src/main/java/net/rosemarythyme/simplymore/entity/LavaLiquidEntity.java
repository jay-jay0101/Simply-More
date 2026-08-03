package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MagmaseepItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;
import org.jetbrains.annotations.NotNull;

public class LavaLiquidEntity extends AbstractLiquidEntity {

    public LavaLiquidEntity(EntityType<LavaLiquidEntity> entityType, World world) {
        super(entityType, world);
    }

    public LavaLiquidEntity(@NotNull LivingEntity owner, Vec3d position, float maxHeight) {
        super(owner, position, maxHeight, EntityRegistry.LAVA.get());
    }

    @Override
    public int getLifespan() {
        return MagmaseepItem.SETTINGS.ventDuration;
    }

    @Override
    public int getOutroTicks() {
        return 10;
    }

    @Override
    public int getIntroTicks() {
        return 20;
    }

    @Override
    public void hit(LivingEntity owner, TargetList hitList) {
        hitList.filterByTargetType(owner, AttackUtils.AttackTarget.ENEMIES)
                .damage(AttackUtils.scaleDamage("fire", owner, 0, MagmaseepItem.SETTINGS.lavaDamage), owner.getDamageSources().inFire())
                .setOnFireFor(5)
                .applyEffect(StatusEffects.SLOWNESS, 10, 2);
    }
}