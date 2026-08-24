package net.rosemarythyme.simplymore.entity;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.uniques.DeathsEyrieItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

public class CrowEntity extends TameableEntity implements Ownable {
    protected static final TrackedData<Optional<UUID>> ATTACKING_UUID = DataTracker.registerData(CrowEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    protected static final TrackedData<OptionalInt> ATTACKING_TIME = DataTracker.registerData(CrowEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);
    private PlayerEntity owner;
    public final AnimationState flapAnimationState = new AnimationState();

    protected static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0, 3.0F, 1F, true));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(2, new SitGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING_UUID, Optional.empty());
        this.dataTracker.startTracking(ATTACKING_TIME, OptionalInt.of(0));
    }


    public CrowEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 15, false);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return null;
    }

    public @Nullable UUID getAttackingUuid() {
        if(this.getDataTracker().get(ATTACKING_UUID).isPresent()) {
            return this.getDataTracker().get(ATTACKING_UUID).get();
        }

        return null;
    }

    public int getAttackingTime() {
        if(this.getDataTracker().get(ATTACKING_TIME).isPresent()) {
            return this.getDataTracker().get(ATTACKING_TIME).getAsInt();
        }

        return 0;
    }

    public void setAttackingTime(int value) {
        this.getDataTracker().set(ATTACKING_TIME, OptionalInt.of(value), true);
    }

    public void setAttackingUuid(UUID value) {
        this.getDataTracker().set(ATTACKING_UUID, Optional.of(value), true);
    }

    @Override
    public void tick() {
        super.tick();

        // Checking if owner is still wielding the weapon
        PlayerEntity player = (PlayerEntity) this.getOwner();
        if (player != null) {
            if (player.getItemCooldownManager().isCoolingDown(ModItemsRegistry.DEATHS_EYRIE.get())) {
                if(this.getAttackingTime() >= -1) {
                    this.setNoGravity(true);
                    this.setNoDrag(true);
                    this.goalSelector.getGoals().clear();
                    this.targetSelector.getGoals().clear();

                    int time = this.getAttackingTime();
                    this.setAttackingTime(time - 1);

                    // Attack
                    LivingEntity target = (LivingEntity) ((ServerWorld) this.getWorld()).getEntity(this.getAttackingUuid());

                    if(target == null || target.isDead()) {
                        this.kill();
                        return;
                    }

                    if (time % 10 == 0) {

                        float dX = this.getRandom().nextBetween(-10,10) / 10f;
                        float dY = this.getRandom().nextBetween(-10,10) / 10f;
                        float dZ = this.getRandom().nextBetween(-10,10) / 10f;

                        this.teleportWithParticles(
                                target.getX() + dX,
                                target.getEyeY() + 1 + dY,
                                target.getZ() + dZ
                        );

                        this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getEyePos());
                        this.removeStatusEffect(StatusEffects.INVISIBILITY);
                        this.setVelocity(new Vec3d(dX * -0.5d, (dY+1.25) * -0.5d, dZ * -0.5d));
                    } else if(time % 10 == 5) {
                        double originalResistance = target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
                        if (target.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) != null) {
                            target.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
                                    .setBaseValue(1.0);
                        }

                        target.damage(this.owner.getDamageSources().playerAttack(this.owner), effect.getDeathsEyrieCrowAttackDamage());
                        this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 10,0, true, false));
                        this.owner.heal(effect.getDeathsEyrieCrowAttackHeal());
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,effect.getDeathsEyrieCrowAttackBlindTime()));
                        target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.BLEED.get(),effect.getDeathsEyrieCrowAttackBleedTime()));

                        if (target.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) != null) {
                            target.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
                                    .setBaseValue(originalResistance);
                        }
                    }
                } else {
                    this.kill();
                }
            } else {
                if (player.isDead()
                        || player.getWorld().getDimension() != this.getWorld().getDimension()
                        || !(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof DeathsEyrieItem)
                ) {
                    this.kill();
                }
            }
        } else {
            this.kill();
        }

        // Flap Animation whilst flying
        if(this.getWorld().isClient() && !isOnGround()) {
            if(this.age % 3 == 0) {
                this.flapAnimationState.start(this.age);
            }
        }
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.dataTracker.get(ATTACKING_UUID).isPresent()) {
            nbt.putUuid("AttackingUUID", this.dataTracker.get(ATTACKING_UUID).get());
        }

        if (this.dataTracker.get(ATTACKING_TIME).isPresent()) {
            nbt.putInt("AttackingTime", this.dataTracker.get(ATTACKING_TIME).getAsInt());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.containsUuid("AttackingUUID")) {
            this.dataTracker.set(ATTACKING_UUID, Optional.of(nbt.getUuid("AttackingUUID")));
        } else {
            this.dataTracker.set(ATTACKING_UUID, Optional.empty());
        }

        if (nbt.contains("AttackingTime")) {
            this.dataTracker.set(ATTACKING_TIME, OptionalInt.of(nbt.getInt("AttackingTime")));
        } else {
            this.dataTracker.set(ATTACKING_TIME, OptionalInt.empty());
        }
    }

    public void teleportWithParticles(double x,double y, double z) {
        teleport(x, y, z);

        this.getWorld().playSound(
                null,
                x,
                y,
                z,
                SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM,
                SoundCategory.NEUTRAL,
                1f,
                0.6f
        );


        ((ServerWorld) this.getWorld()).spawnParticles(
                new DustParticleEffect(new Vector3f(0f, 0f, 0.2f), 1f),
                x,
                y,
                z,
                5,
                0.2f,0.2f,0.2f,
                0
        );
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        this.getWorld().playSound(
                null,
                this.getX(),
                this.getY(),
                this.getZ(),
                SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM,
                SoundCategory.NEUTRAL,
                1f,
                0.6f
        );


        ((ServerWorld) this.getWorld()).spawnParticles(
                new DustParticleEffect(new Vector3f(0f, 0f, 0.2f), 3f),
                this.getX(),
                this.getY()+0.25f,
                this.getZ(),
                5,
                0.2f,0.2f,0.2f,
                0
        );

        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.8F)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source != this.getDamageSources().genericKill()) return false;
        return super.damage(source, amount);
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public EntityView method_48926() {
        return null;
    }

    @Override
    public LivingEntity getOwner() {
        return this.owner;
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
    public void setOwner(PlayerEntity player) {
        this.owner = player;
    }

}
