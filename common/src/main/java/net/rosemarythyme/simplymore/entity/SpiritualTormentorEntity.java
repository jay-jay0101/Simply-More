package net.rosemarythyme.simplymore.entity;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.IdolItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

public class SpiritualTormentorEntity extends AbstractSpiritualEntity {
    private static final int MAX_ATTACK_TIME = 100;
    private static final int END_LAG_TIME = 20;
    private static final TrackedData<OptionalInt> CURRENT_ATTACK_DURATION = DataTracker.registerData(SpiritualTormentorEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);
    private static final TrackedData<OptionalInt> POST_ATTACK_WAIT_TIME = DataTracker.registerData(SpiritualTormentorEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);
    private int attackCooldown = 0;
    private AttackData attackData;

    public final AnimationState startChargeAnim = new AnimationState();
    public final AnimationState chargeAnim = new AnimationState();
    public final AnimationState endChargeAnim = new AnimationState();

    private record AttackData(LivingEntity target, float yaw, Vec3d originalPos, double intendedRange) { }

    public SpiritualTormentorEntity(EntityType<? extends AbstractSpiritualEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(CURRENT_ATTACK_DURATION).isPresent() || this.dataTracker.get(POST_ATTACK_WAIT_TIME).isPresent();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CURRENT_ATTACK_DURATION, OptionalInt.empty());
        builder.add(POST_ATTACK_WAIT_TIME, OptionalInt.empty());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        OptionalInt time = this.dataTracker.get(CURRENT_ATTACK_DURATION);
        if(isAttacking() && time.isPresent()) {
            nbt.putInt("AttackTime", time.getAsInt());
        }

        OptionalInt postWait = this.dataTracker.get(CURRENT_ATTACK_DURATION);
        if(isAttacking() && postWait.isPresent()) {
            nbt.putInt("AttackEndLag", postWait.getAsInt());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if(nbt.contains("AttackTime")) {
            this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.of(nbt.getInt("AttackTime")));
        } else {
            this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.empty());
        }

        if(nbt.contains("AttackEndLag")) {
            this.dataTracker.set(POST_ATTACK_WAIT_TIME, OptionalInt.of(nbt.getInt("AttackEndLag")));
        } else {
            this.dataTracker.set(POST_ATTACK_WAIT_TIME, OptionalInt.empty());
        }
    }

    public SpiritualTormentorEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.SPIRITUAL_TORMENTOR.get());
    }

    @Override
    public float getStrength() {
        return (float) this.dataTracker.get(STRENGTH) / IdolItem.DARKSENT.maxStrength;
    }

    @Override
    public double getAuraRange() {
        return MathHelper.lerp(getStrength(), IdolItem.DARKSENT.baseAuraRange, IdolItem.DARKSENT.maxAuraRange);
    }

    @Override
    public int getLifespan() {
        return IdolItem.DARKSENT.tormentorDuration;
    }

    private void tickCharge(LivingEntity owner, int time) {
        AudioVisualUtils.particleCube((ServerWorld) this.getWorld(), this.getPos(), ParticleTypes.FLAME, 20, 0.25f, 0.05f);

        Vec3d velocity = MathUtils.getDirectionalVector(attackData.yaw, 0).multiply(MathHelper.lerp(this.getStrength(), IdolItem.DARKSENT.minDashSpeed, IdolItem.DARKSENT.maxDashSpeed));
        this.setYaw(attackData.yaw);
        this.setPitch(0);
        Vec3d horizontalVelocity = new Vec3d(velocity.getX(), this.getVelocity().getY(), velocity.getZ());
        this.setVelocity(horizontalVelocity);

        Vec3d offset = MathUtils.getDirectionalVector(attackData.yaw, 0).multiply(1.2f);

        AttackUtils.cubeAttack(owner, this.getPos().add(offset.getX(), 0.5f, offset.getZ()), 1.8f, AttackUtils.AttackTarget.ENEMIES)
                .damage(MathHelper.lerp(this.getStrength(), IdolItem.DARKSENT.baseDamage, IdolItem.DARKSENT.maxDamage), AttackUtils.getHitSource(owner))
                .filter(entity -> entity.getVelocity().getY() < IdolItem.DARKSENT.knockUpHeight)
                .knockback(this, 0.4f)
                .addVelocity(0f, IdolItem.DARKSENT.knockUpHeight, 0f);


        double travelledRange = Math.sqrt(this.squaredDistanceTo(new Vec3d(attackData.originalPos.getX(), this.getY(), attackData.originalPos.getZ())));
        if(time > MAX_ATTACK_TIME
                || travelledRange >= attackData.intendedRange
                || EntityUtils.tryStepUp(this, horizontalVelocity) == EntityUtils.StepUpResult.TOO_TALL) {
            endAttack();
        }
    }

    private void tickAttacking(LivingEntity owner) {
        int time = this.dataTracker.get(CURRENT_ATTACK_DURATION).orElse(0);
        this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.of(++time));

        if(time > IdolItem.DARKSENT.timeBeforeCharge) {
            tickCharge(owner, time);
        } else {
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, attackData.target.getPos());

            float yaw = MathUtils.getYawAndPitch(MathUtils.normalisedDirectionBetween(attackData.originalPos, attackData.target.getPos(), false)).getLeft();
            this.attackData = new AttackData(attackData.target, yaw, attackData.originalPos, attackData.intendedRange);

            if(time == IdolItem.DARKSENT.timeBeforeCharge) {
                AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get()));
            }
        }
    }

    @Override
    public void animate() {
        super.animate();

        OptionalInt time = this.dataTracker.get(CURRENT_ATTACK_DURATION);
        if(time.isPresent()) {
            int timeVal = time.getAsInt();
            if(timeVal == IdolItem.DARKSENT.timeBeforeCharge - 10) {
                startChargeAnim.startIfNotRunning(this.age);
            } else if (timeVal > IdolItem.DARKSENT.timeBeforeCharge) {
                chargeAnim.startIfNotRunning(this.age);
                startChargeAnim.stop();
            }

            idleArmsAnim.stop();
        } else {
            startChargeAnim.stop();
            chargeAnim.stop();
        }

        OptionalInt endLag = this.dataTracker.get(POST_ATTACK_WAIT_TIME);
        if(endLag.isPresent()) {
            int endLagVal = endLag.getAsInt();
            if(endLagVal == END_LAG_TIME - 1) {
                endChargeAnim.startIfNotRunning(this.age);
            }

            startChargeAnim.stop();
            chargeAnim.stop();
        } else {
            endChargeAnim.stop();
        }
    }

    private void endAttack() {
        this.attackData = null;
        this.attackCooldown = IdolItem.DARKSENT.attackCooldown + END_LAG_TIME;
        this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.empty());
        this.dataTracker.set(POST_ATTACK_WAIT_TIME, OptionalInt.of(END_LAG_TIME));
    }

    private void findTarget(LivingEntity owner) {
        Optional<Pair<LivingEntity, Float>> targetDistance = AttackUtils.cuboidAttack(owner, this.getPos(), getAuraRange() * 2, 5, AttackUtils.AttackTarget.ENEMIES)
                .filter(LivingEntity::isAlive)
//                .filter(this::canSee)
                .getByMax((target) -> target.distanceTo(this));

        if(targetDistance.isPresent()) {
            this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.of(0));
            this.attackCooldown = IdolItem.DARKSENT.attackCooldown + MAX_ATTACK_TIME + END_LAG_TIME;

            LivingEntity target = targetDistance.get().getLeft();
            this.attackData = new AttackData(target, 0, this.getPos(), targetDistance.get().getRight());

            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get()));
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_02.get()).setPitch(0f));
        }
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        super.serverTick(owner);

        if(attackCooldown > 0) {
            attackCooldown--;
        } else {
            if(attackData == null){
                findTarget(owner);
            }
        }

        if(attackData != null) {
            tickAttacking(owner);
        } else {
            OptionalInt endLag = this.dataTracker.get(POST_ATTACK_WAIT_TIME);
            if(endLag.isPresent()) {
                int endLagTime = endLag.getAsInt() - 1;
                this.dataTracker.set(POST_ATTACK_WAIT_TIME, endLagTime <= 0 ? OptionalInt.empty() : OptionalInt.of(endLagTime));
            }
        }

        if (this.getRiseFall() != 1) {
            AudioVisualUtils.particleCube((ServerWorld) this.getWorld(), this.getPos(), ParticleTypes.FLAME, 20, 0f, 0.2);
        }

        Predicate<StatusEffect> predicate = PredicateUtils.createForEffectBlacklist(IdolItem.DARKSENT.blacklist, IdolItem.DARKSENT.includeGlobalBlacklist)
                .and(PredicateUtils.BENEFICIAL_EFFECT)
                .and(PredicateUtils.IS_INSTANT.negate());

        AttackUtils.cylinderAttack(owner, this.getPos(), getAuraRange(), 10, AttackUtils.AttackTarget.ENEMIES)
                .onEachEffect(predicate, (target, effect) -> {
                    if (EntityUtils.drainEffect(target, effect, IdolItem.DARKSENT.effectDrainRate)) {
                        int bonus = effect.getAmplifier() + 1;
                        AudioVisualUtils.particleLine((ServerWorld) this.getWorld(), target.getEyePos(), this.getEyePos(), ParticleTypes.SOUL, 0.25, 1, 0, 0);
                        AudioVisualUtils.playSound(getWorld(), this.getPos(), new Sound(SoundRegistry.MAGIC_SWORD_PARRY_02.get()));
                        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.FLAME, 40, 0.25f, 0.2);

                        target.damage(target.getDamageSources().indirectMagic(this, owner), IdolItem.DARKSENT.drainDamage);
                        this.dataTracker.set(STRENGTH, Math.min(this.dataTracker.get(STRENGTH) + bonus, IdolItem.DARKSENT.maxStrength));
                    }
                });
    }
}
