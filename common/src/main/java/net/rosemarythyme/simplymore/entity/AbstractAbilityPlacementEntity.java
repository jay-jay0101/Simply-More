package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class AbstractAbilityPlacementEntity extends MarkerEntity implements Ownable {
    protected UUID ownerUuid;
    protected int age = 0;
    protected static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;

    protected AbstractAbilityPlacementEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public ServerWorld getServerWorld() {
        return (ServerWorld) this.getWorld();
    }

    public AbstractAbilityPlacementEntity(@NotNull LivingEntity owner, Vec3d position, EntityType<?> entityType) {
        this(entityType, owner.getWorld());
        this.ownerUuid = owner.getUuid();
        this.age = 0;
        this.refreshPositionAfterTeleport(position);
    }

    abstract int getLifespan();

    @Override
    public void tick() {
        super.tick();

        age++;
        if(this.age > getLifespan()) this.discard();
        if(getOwner() == null) this.discard();
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        if(ownerUuid != null) {
            nbt.putUuid("Owner", ownerUuid);
        }

        nbt.putInt("Age", age);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if(nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
        } else {
            this.ownerUuid = null;
        }

        if(nbt.contains("Age")) {
            this.age = nbt.getInt("Age");
        } else {
            this.age = 0;
        }
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        if(ownerUuid == null) return null;
        Entity owner = ((ServerWorld) getWorld()).getEntity(ownerUuid);
        return owner instanceof LivingEntity livingOwner ? livingOwner : null;
    }
}