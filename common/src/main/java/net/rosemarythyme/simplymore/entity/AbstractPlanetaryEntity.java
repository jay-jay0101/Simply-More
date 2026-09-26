package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.TimekeeperItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.TargetList;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPlanetaryEntity extends AbstractVisibleAbilityEntity {
    public final boolean empowered;

    public AbstractPlanetaryEntity(EntityType<? extends AbstractPlanetaryEntity> entityType, World world) {
        super(entityType, world);
        empowered = false;
    }

    public AbstractPlanetaryEntity(@NotNull LivingEntity owner, Vec3d position, boolean empowered, EntityType<? extends AbstractPlanetaryEntity> entityType) {
        super(owner, position, entityType);
        this.empowered = empowered;
    }

    @Override
    public int getOutroTicks() {
        return 20;
    }

    @Override
    public int getIntroTicks() {
        return 20;
    }

    @Override
    public int getLifespan() {
        return TimekeeperItem.SETTINGS.orbitDuration;
    }

    @Override
    protected final void serverTick(LivingEntity owner) {
        float yaw = (owner.age % 3600) * TimekeeperItem.SETTINGS.orbitSpeed + getYawOffset();
        double range = TimekeeperItem.SETTINGS.orbitRange * (1 - Math.abs(MathUtils.getRiseFall(this)));

        this.setPosition(EntityUtils.rangeAroundPoint(owner.getPos().offset(Direction.UP, 1), this, yaw, (float) range));
        this.setRotation(isTidallyLocked() ? yaw : yaw * 4, 0);

        if(this.isActive()) {
            if(this.getAge() % 5 == 0) {
                onHit(owner, TargetUtils.boxAttack(owner, this.getBoundingBox().expand(0.4f), TargetUtils.TargetType.ENEMIES));
            }

            if(!InventoryUtils.isHolding(owner, ItemRegistry.TIMEKEEPER.get())) {
                   this.setAge(this.getLifespan());
            }
        }

        aura(owner, yaw);

        if(this.getAge() == this.getLifespan() + this.getOutroTicks() - 1) {
            AudioVisualUtils.applyScreenshake(getServerWorld(), owner.getPos(), owner, 12, 2f, 20);
            SummonUtils.spawnAbility(new PlanetaryBurstEntity(owner, isTidallyLocked(), this.empowered), owner);
        }
    }

    public abstract void aura(LivingEntity owner, float yaw);

    public abstract void onHit(LivingEntity owner, TargetList targets);

    public abstract float getYawOffset();

    public abstract boolean isTidallyLocked();
}
