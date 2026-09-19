package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MimicryTimelineUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;

public class MimicryVisualEntity extends AbstractVisibleAbilityEntity {
    protected static final TrackedData<ItemStack> ITEM_STATE = DataTracker.registerData(MimicryVisualEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    protected static final TrackedData<Integer> ATTACK_START = DataTracker.registerData(MimicryVisualEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> ATTACK_DURATION = DataTracker.registerData(MimicryVisualEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> ATTACK_TYPE = DataTracker.registerData(MimicryVisualEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public enum Animation {
        NONE((visual, owner, progress) -> AnimationData.DEFAULT),
        SWING(sweep(70)),
        REVERSE_SWING(sweep(-70)),
        LONG_SWING(sweep(120)),
        REVERSE_LONG_SWING(sweep(-120)),
        SPIN((visual, owner, progress) -> MimicryTimelineUtils.spinAnimation(progress)),
        DOWN_SWING(downSwing(90)),
        WIDE_DOWN_SWING(downSwing(120)),
        STAB((visual, owner, progress) -> MimicryTimelineUtils.stabAnimation(owner, progress));

        public final TriFunction<MimicryVisualEntity, LivingEntity, Float, AnimationData> getData;
        Animation(TriFunction<MimicryVisualEntity, LivingEntity, Float, AnimationData> getData) {
            this.getData = getData;
        }

        private static TriFunction<MimicryVisualEntity, LivingEntity, Float, AnimationData> sweep(float angle) {
            return (visual, owner, progress) -> MimicryTimelineUtils.sweepAnimation(owner, progress, angle);
        }

        private static TriFunction<MimicryVisualEntity, LivingEntity, Float, AnimationData> downSwing(float angle) {
            return (visual, owner, progress) -> MimicryTimelineUtils.downSwingAnimation(owner, progress, angle);
        }

    }

    public record AnimationData(float yaw, float pitch, float roll, Vec3d offset) {
        public static final AnimationData DEFAULT = new AnimationData(0, 0, 0, Vec3d.ZERO);
    }

    public void startAttack(int duration, Animation animation) {
        this.dataTracker.set(ATTACK_START, this.getAge());
        this.dataTracker.set(ATTACK_DURATION, duration);
        this.dataTracker.set(ATTACK_TYPE, animation.ordinal());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ITEM_STATE, ItemStack.EMPTY);
        builder.add(ATTACK_START, 0);
        builder.add(ATTACK_DURATION, 0);
        builder.add(ATTACK_TYPE, 0);
    }

    public ItemStack getStack() {
        return this.dataTracker.get(ITEM_STATE);
    }

    public Animation getAnimation() {
        int ordinal = this.getDataTracker().get(ATTACK_TYPE);
        return ordinal < 0 || ordinal >= Animation.values().length ? Animation.NONE : Animation.values()[ordinal];
    }

    public int getStart() {
        return this.dataTracker.get(ATTACK_START);
    }

    public int getDuration() {
        return this.dataTracker.get(ATTACK_DURATION);
    }

    public MimicryVisualEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public MimicryVisualEntity(@NotNull LivingEntity owner, ItemStack stack) {
        super(owner, owner.getPos(), EntityRegistry.MIMICRY_VISUAL.get());
        this.dataTracker.set(ITEM_STATE, stack);
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

    @Override
    protected void serverTick(LivingEntity owner) {
        this.refreshPositionAfterTeleport(owner.getPos().offset(Direction.UP, 1));

        if(!ActiveAbilityManager.SERVER.isInAbility(owner, ActiveAbilityManager.Type.MIMICRY)) {
            this.discard();
        }
    }
}
