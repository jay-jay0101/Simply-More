package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCollidableAbilityEntity extends AbstractVisibleAbilityEntity {
    protected AbstractCollidableAbilityEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public AbstractCollidableAbilityEntity(@NotNull LivingEntity owner, Vec3d position, EntityType<? extends LivingEntity> entityType) {
        super(owner, position, entityType);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean collidesWith(Entity other) {
        return !other.isSpectator() && !this.hasPassenger(other);
    }
}