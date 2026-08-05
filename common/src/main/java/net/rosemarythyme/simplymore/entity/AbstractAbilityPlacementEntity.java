package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractAbilityPlacementEntity extends LivingEntity implements Ownable {
    protected static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(AbstractAbilityPlacementEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected int age = -getIntroTicks();

    public int getAge() {
        return age;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(OWNER, Optional.empty());
    }

    public abstract int getOutroTicks();
    public abstract int getIntroTicks();

    protected void serverTick(LivingEntity owner) {}

    @Override
    protected void pushAway(Entity entity) {}

    @Override
    public void pushAwayFrom(Entity entity) {}

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    public void kill() {}

    protected AbstractAbilityPlacementEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        onSpawn();
    }

    protected void onSpawn() {}

    @Override
    public boolean canTakeDamage() {
        return false;
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return false;
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    @Override
    public Text getName() {
        return Text.empty();
    }

    @Override
    public void move(MovementType movementType, Vec3d movement) {}

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return List.of();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {}

    public ServerWorld getServerWorld() {
        if(!(this.getWorld() instanceof ServerWorld world)) throw new RuntimeException("AbstractAbilityPlacementEntity#getServerWorld called from client.");

        return world;
    }

    public AbstractAbilityPlacementEntity(@NotNull LivingEntity owner, Vec3d position, EntityType<? extends LivingEntity> entityType) {
        this(entityType, owner.getWorld());
        this.dataTracker.set(OWNER, Optional.of(owner.getUuid()));
        this.refreshPositionAfterTeleport(position);
    }

    public abstract int getLifespan();

    public boolean isActive() {
        return age >= 0 && age <= getLifespan();
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        age++;

        if(this.getWorld().isClient) return;

        if(this.age > getLifespan() + getOutroTicks()) this.discard();

        LivingEntity owner = getOwner();

        if(owner == null) {
            this.discard();
            return;
        }

        serverTick(owner);
    }

    public Optional<UUID> getOwnerUUID() {
        return this.dataTracker.get(OWNER);
    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        this.getOwnerUUID().ifPresent(uuid -> nbt.putUuid("owner", uuid));

        nbt.putInt("Age", age);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.containsUuid("owner")) {
            this.dataTracker.set(OWNER, Optional.of(nbt.getUuid("owner")));
        } else {
            this.dataTracker.set(OWNER, Optional.empty());
        }
    }

    @Override
    public @Nullable LivingEntity getOwner() {
        Optional<UUID> uuid = getOwnerUUID();

        if(uuid.isEmpty()) return null;
        Entity owner = getServerWorld().getEntity(uuid.get());
        return owner instanceof LivingEntity livingOwner ? livingOwner : null;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
        throw new RuntimeException("AbstractAbilityPlacementEntity should not be synced to the client. Use AbstractVisibleAbilityEntity instead.");
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }
}