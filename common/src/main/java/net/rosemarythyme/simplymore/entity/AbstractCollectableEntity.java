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
import net.rosemarythyme.simplymore.util.data.TargetList;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCollectableEntity extends AbstractVisibleAbilityEntity {
    protected static final TrackedData<Integer> COLLECTION_TIME = DataTracker.registerData(AbstractCollectableEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public Map<LivingEntity, Integer> times = new HashMap<>();

    public int getCollectionTime() {
        return this.getDataTracker().get(COLLECTION_TIME);
    }

    protected AbstractCollectableEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("collection_time", getCollectionTime());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if(nbt.contains("collection_time")) {
            this.dataTracker.set(COLLECTION_TIME, nbt.getInt("collection_time"));
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(COLLECTION_TIME, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient) return;

        Map<LivingEntity, Integer> newTimes = new HashMap<>();
        TargetList targets = AttackUtils.cubeAttack(getOwner(), getPos(), 1.5, AttackUtils.AttackTarget.OTHERS_AND_USER_POSITIVELY)
                .filter(this::canCollect)
                .onEach((entity) -> {
                    int oldTime = times.getOrDefault(entity, 0);
                    newTimes.put(entity, oldTime + 1);
                })
                .filter((livingEntity -> times.getOrDefault(livingEntity, 0) == timeToCollect()))
                .onFirst(this::onCollect);

        times = newTimes;

        if(targets.isPopulated()) {
            discard();
        }

        this.dataTracker.set(COLLECTION_TIME, newTimes.values().stream().mapToInt(Integer::intValue).max().orElse(0));
    }

    public AbstractCollectableEntity(@NotNull LivingEntity owner, Vec3d position, EntityType<? extends LivingEntity> entityType) {
        super(owner, position, entityType);
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
        return AttackUtils.PSEUDOINFINITE_DURATION;
    }

    abstract boolean canCollect(LivingEntity entity);
    abstract void onCollect(LivingEntity entity);
    abstract int timeToCollect();
}
