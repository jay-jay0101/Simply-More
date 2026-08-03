package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLiquidEntity extends AbstractVisibleAbilityEntity {
    private static final TrackedData<Float> HEIGHT = DataTracker.registerData(AbstractLiquidEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Float> MAX_HEIGHT = DataTracker.registerData(AbstractLiquidEntity.class, TrackedDataHandlerRegistry.FLOAT);

    protected AbstractLiquidEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(HEIGHT, 0f);
        builder.add(MAX_HEIGHT, 0f);
    }

    public AbstractLiquidEntity(@NotNull LivingEntity owner, Vec3d position, float maxHeight, EntityType<? extends LivingEntity> entityType) {
        super(owner, position, entityType);
        this.setMaxHeight(maxHeight);
        this.setLiquidHeight(0);
    }

    public void hit(LivingEntity owner, TargetList hitList) {
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient) return;

        float maxHeight = getMaxHeight();

        float height = maxHeight;
        if(age < 0) {
            height = MathUtils.clampedLerp(age, -getIntroTicks(), 0, 0, maxHeight);
        } else if (age >= getLifespan()) {
            height = maxHeight - MathUtils.clampedLerp(age, getLifespan(), getLifespan() + getOutroTicks(), 0, maxHeight);
        }

        setLiquidHeight(height);

        LivingEntity owner = getOwner();
        if(owner == null) return;
        hit(owner, AttackUtils.cuboidAttack(getOwner(),  getPos(), this.getBoundingBox().getLengthX(), height, AttackUtils.AttackTarget.OTHERS_AND_USER_POSITIVELY));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("height")) {
            this.setLiquidHeight(nbt.getFloat("height"));
        }

        if (nbt.contains("max_height")) {
            this.setMaxHeight(nbt.getFloat("max_height"));
        }

    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("height", this.getLiquidHeight());
        nbt.putFloat("max_height", this.getMaxHeight());
    }

    public void setLiquidHeight(float height) {
        this.dataTracker.set(HEIGHT, height);
    }

    public float getLiquidHeight() {
        return this.dataTracker.get(HEIGHT);
    }

    public void setMaxHeight(float heightScale) {
        this.dataTracker.set(MAX_HEIGHT, heightScale);
    }

    public float getMaxHeight() {
        return this.dataTracker.get(MAX_HEIGHT);
    }
}