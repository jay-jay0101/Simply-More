package net.rosemarythyme.simplymore.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractPetAbility extends TameableEntity implements Ownable {
    protected static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(AbstractPetAbility.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    protected AbstractPetAbility(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public AbstractPetAbility(@NotNull LivingEntity owner, Vec3d position, EntityType<? extends TameableEntity> entityType) {
        super(entityType, owner.getWorld());
        this.dataTracker.set(OWNER, Optional.of(owner.getUuid()));
        this.refreshPositionAfterTeleport(position);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {}

    @Override
    public LivingEntity getOwner() {
        Optional<UUID> uuid = this.dataTracker.get(OWNER);
        if(uuid.isEmpty()) return null;

        Entity owner = this.getWorld().isClient() ?
                this.getWorld().getPlayerByUuid(uuid.get()) :
                ((ServerWorld) this.getWorld()).getEntity(uuid.get());

        if(owner instanceof LivingEntity livingEntity) return livingEntity;
        return null;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(OWNER, Optional.empty());
    }

    @Override
    protected abstract EntityNavigation createNavigation(World world);

    @Override
    public void kill() {}

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    protected abstract void serverTick(LivingEntity owner);

    public void animate() {}

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

    @Override
    public void tick() {
        super.tick();
        animate();

        if(this.getWorld() instanceof ServerWorld world) {
            Optional<UUID> uuid = this.dataTracker.get(OWNER);

            if(uuid.isPresent() && world.getEntity(uuid.get()) instanceof LivingEntity owner) {
                serverTick(owner);
            } else {
                discard();
            }
        }
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }
}
