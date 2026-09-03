package net.rosemarythyme.simplymore.entity;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.ai.SpiritNavigation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractSpiritualEntity extends MobEntity {
    protected static final TrackedData<Optional<UUID>> OWNER = DataTracker.registerData(AbstractSpiritualEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected static final TrackedData<Integer> STRENGTH = DataTracker.registerData(AbstractSpiritualEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public final AnimationState idleArms = new AnimationState();
    protected int age = 0;

    public int getAge() {
        return age;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        return false;
    }

    @Override
    public void kill() {}

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(STRENGTH, 0);
        builder.add(OWNER, Optional.empty());
    }

    public AbstractSpiritualEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiritNavigation(this, world);
    }

    public AbstractSpiritualEntity(@NotNull LivingEntity owner, Vec3d position, EntityType<? extends MobEntity> entityType) {
        super(entityType, owner.getWorld());
        this.dataTracker.set(OWNER, Optional.of(owner.getUuid()));
        this.refreshPositionAfterTeleport(position);
    }

    public abstract boolean isAttacking();

    protected void serverTick(LivingEntity owner) {
        if(!isAttacking()) {
            if(this.navigation.isIdle()) {
            }
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, owner.getEyePos());


            if (this.distanceTo(owner) > getAuraRange() / 2) {
                if(this.age % 10 == 0) {
                    this.navigation.startMovingTo(owner, 2f);
                }
            } else {
                this.navigation.stop();
            }
        }
    }

    public abstract double getAuraRange();

    public void animate() {
        if (!idleArms.isRunning()) {
            idleArms.start(this.age);
        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {}

    public abstract int getLifespan();

    @Override
    public void tick() {
        super.tick();
        animate();

        age++;
        if(age > getLifespan()) {
            discard();
            return;
        }

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
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }
}