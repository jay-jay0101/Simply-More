package net.rosemarythyme.simplymore.entity;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.ai.CrowFollowOwnerGoal;
import net.rosemarythyme.simplymore.entity.ai.CrowLookAtEntityGoal;
import net.rosemarythyme.simplymore.item.uniques.DeathsEyrieItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

public class CrowEntity extends AbstractPetAbility implements EnumeratedEntity {
    private static final int ATTACK_DURATION = 6;
    protected static final TrackedData<Optional<UUID>> ATTACKING = DataTracker.registerData(CrowEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected static final TrackedData<OptionalInt> ATTACKING_TIME = DataTracker.registerData(CrowEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);
    protected static final TrackedData<Integer> ORDINAL = DataTracker.registerData(CrowEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState flapAnim = new AnimationState();

    public CrowEntity(EntityType<CrowEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 15, false);
    }

    @SuppressWarnings("unused") // used via recursion in `AttackUtils#ensureEnumeratedEntities`
    public CrowEntity(@NotNull LivingEntity owner, int ordinal) {
        super(owner, getOriginPoint(owner), EntityRegistry.CROW.get());
        this.moveControl = new FlightMoveControl(this, 15, false);
        this.setOrdinal(ordinal);
    }

    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING).isPresent() && this.dataTracker.get(ATTACKING_TIME).orElse(0) <= ATTACK_DURATION;
    }

    private static Vec3d getOriginPoint(LivingEntity owner) {
        Vec3d pos = owner.getPos().offset(Direction.UP, 3.5f);
        return EntityUtils.rangeAroundPoint(pos, owner, owner.getYaw() + 180f, 1.25f);
    }

    @Override
    public void serverTick(LivingEntity owner) {
        if(!InventoryUtils.isHoldingAwakenedStack(owner, ItemRegistry.DEATHS_EYRIE.get())) {
            discard();
            return;
        }

        if(age == 1) {
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM).setPitch(0.6f));
        }

        OptionalInt duration = this.getDataTracker().get(ATTACKING_TIME);
        LivingEntity entity = TargetUtils.getEntityByUUID(owner, this.getDataTracker().get(ATTACKING).orElse(null));
        if(duration.isPresent() && entity != null) {
            tickAttack(entity, duration.getAsInt());
        }
    }

    private void tickAttack(LivingEntity target, int time) {
        if(!target.isAlive()) {
            stopAttack();
            return;
        }

        if(target.getPos().distanceTo(this.getPos()) > 10f) {
            stopAttack();
            return;
        }

        if(isAttacking()) {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos().offset(Direction.UP, 1f));

            Vec3d velocity = MathUtils.getDirectionalVector(this.getYaw(), this.getPitch()).multiply(1.25f);
            this.setVelocity(velocity);
        }

        if(--time >= 0) {
            this.dataTracker.set(ATTACKING_TIME, OptionalInt.of(time));
        } else {
            new TargetList(target).forceDamage(DeathsEyrieItem.SETTINGS.crowDamage, this.getDamageSources().mobAttack(this))
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), DeathsEyrieItem.SETTINGS.woundedDuration * getOrdinal(), 0);

            stopAttack();
        }
    }

    private void stopAttack() {
        this.dataTracker.set(ATTACKING_TIME, OptionalInt.empty());
        this.dataTracker.set(ATTACKING, Optional.empty());
        this.setVelocity(Vec3d.ZERO);

        LivingEntity owner = getOwner();
        if(owner != null) {
            Vec3d pos = getOriginPoint(owner);
            this.refreshPositionAfterTeleport(pos);
        }

        AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM).setPitch(0.6f));
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if(isAttacking()) return;
        super.pushAwayFrom(entity);
    }

    @Override
    protected void pushAway(Entity entity) {
        if(isAttacking()) return;
        super.pushAway(entity);
    }

    public void startAttack(LivingEntity target) {
        if(isAttacking()) return;

        this.dataTracker.set(ATTACKING, Optional.of(target.getUuid()));
        this.dataTracker.set(ATTACKING_TIME, OptionalInt.of(ATTACK_DURATION + getOrdinal() - 1));

        Vec3d pos = EntityUtils.rangeAroundPoint(target.getPos().offset(Direction.UP, 2.5f), this, this.getRandom().nextFloat() * 360f, 3f);
        this.setPosition(pos);

        AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM).setPitch(0.6f));
    }

    @Override
    public void animate() {
        if(this.getWorld().isClient() && !isOnGround()) {
            if(this.age % 5 == 0) {
                this.flapAnim.start(this.age);
            }
        }
    }

    @Override
    public boolean hasNoGravity() {
        return super.hasNoGravity() || isAttacking();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        writeEnumeratedCustomDataToNbt(nbt);

        if(nbt.containsUuid("attacking")) {
            this.dataTracker.set(ATTACKING, Optional.of(nbt.getUuid("attacking")));
        } else {
            this.dataTracker.set(ATTACKING, Optional.empty());
        }

        if(nbt.contains("attacking_time")) {
            this.dataTracker.set(ATTACKING_TIME, OptionalInt.of(nbt.getInt("attacking_time")));
        } else {
            this.dataTracker.set(ATTACKING_TIME, OptionalInt.empty());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        readEnumeratedCustomDataFromNbt(nbt);

        Optional<UUID> attacking = this.dataTracker.get(ATTACKING);
        attacking.ifPresent((uuid) -> nbt.putUuid("attacking", uuid));

        OptionalInt time = this.dataTracker.get(ATTACKING_TIME);
        time.ifPresent((uuid) -> nbt.putInt("attacking_time", uuid));
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public TrackedData<Integer> getDataType() {
        return ORDINAL;
    }

    @Override
    public boolean shouldEnumerate() {
        return true;
    }

    @Override
    public DataTracker getDataTrackerBridge() {return this.getDataTracker();}

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new CrowFollowOwnerGoal(this, 1.0, 3.0F, 1F));
        this.goalSelector.add(2, new CrowLookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        initEnumeratedDataTracker(builder);

        builder.add(ATTACKING, Optional.empty());
        builder.add(ATTACKING_TIME, OptionalInt.empty());
    }
}
