package net.rosemarythyme.simplymore.entity;

import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.ai.SpiritNavigation;
import net.rosemarythyme.simplymore.util.MathUtils;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractSpiritualEntity extends AbstractPetAbility implements Ownable {
    protected static final TrackedData<Integer> STRENGTH = DataTracker.registerData(AbstractSpiritualEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public final AnimationState idleArmsAnim = new AnimationState();
    protected int age = -20;

    public int getAge() {
        return age;
    }

    public float getRiseFall() {
        if(age > 0) return MathUtils.clampedLerp(age, getLifespan(), getLifespan() + 20, 1f, 0f);
        return MathUtils.clampedLerp(age, -20, 0, 0f, 1f);
    }

    public abstract float getStrength();

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("Age", age);
        nbt.putInt("Strength", this.dataTracker.get(STRENGTH));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.age = nbt.getInt("Age");
        this.dataTracker.set(STRENGTH, nbt.getInt("Strength"));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(STRENGTH, 0);
    }

    public AbstractSpiritualEntity(EntityType<? extends AbstractPetAbility> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiritNavigation(this, world);
    }

    public AbstractSpiritualEntity(@NotNull LivingEntity owner, Vec3d position, EntityType<? extends AbstractPetAbility> entityType) {
        super(owner, position, entityType);
    }

    public abstract boolean isAttacking();

    protected void serverTick(LivingEntity owner) {
        if(!isAttacking()) {
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

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Override
    public boolean isTouchingWater() {
        return false;
    }

    @Override
    public boolean isSubmergedIn(TagKey<Fluid> fluidTag) {
        return false;
    }

    @Override
    public boolean isSubmergedInWater() {
        return false;
    }

    @Override
    public boolean isInsideWaterOrBubbleColumn() {
        return false;
    }

    public abstract double getAuraRange();

    public void animate() {
        if(!isAttacking()) {
            idleArmsAnim.startIfNotRunning(this.age);
        }
    }

    @Override
    public void pushAwayFrom(Entity entity) {}

    public abstract int getLifespan();

    @Override
    public void tick() {
        super.tick();

        age++;
        if(age > getLifespan()) {
            if(age > getLifespan() + 20) {
                discard();
            }
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 0.11F)
                .add(EntityAttributes.GENERIC_GRAVITY, 0.004F)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F);
    }
}