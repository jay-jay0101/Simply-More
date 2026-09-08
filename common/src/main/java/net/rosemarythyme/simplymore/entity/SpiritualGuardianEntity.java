package net.rosemarythyme.simplymore.entity;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.IdolItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;
import java.util.function.Predicate;

public class SpiritualGuardianEntity extends AbstractSpiritualEntity {
    private static final int ATTACK_DURATION = 20;
    public final AnimationState attackAnim = new AnimationState();

    private static final TrackedData<OptionalInt> CURRENT_ATTACK_DURATION = DataTracker.registerData(SpiritualGuardianEntity.class, TrackedDataHandlerRegistry.OPTIONAL_INT);
    private int attackCooldown = 0;
    private AttackData attackData;

    private record AttackData(LivingEntity target, float damage) { }

    public SpiritualGuardianEntity(EntityType<SpiritualGuardianEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CURRENT_ATTACK_DURATION, OptionalInt.empty());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        OptionalInt time = this.dataTracker.get(CURRENT_ATTACK_DURATION);
        if(isAttacking() && time.isPresent()) {
            nbt.putInt("AttackTime", time.getAsInt());
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
    }

    @Override
    public float getStrength() {
        return (float) this.dataTracker.get(STRENGTH) / IdolItem.HOLYLIGHT.maxStrength;
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(CURRENT_ATTACK_DURATION).isPresent();
    }

    @Override
    public void animate() {
        super.animate();

        OptionalInt time = this.dataTracker.get(CURRENT_ATTACK_DURATION);
        if(time.isPresent()) {
            idleArmsAnim.stop();
            attackAnim.startIfNotRunning(this.age);
        } else {
            attackAnim.stop();
        }
    }

    @Override
    public int getLifespan() {
        return IdolItem.HOLYLIGHT.guardianDuration;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.PARTICLE_SOUL_ESCAPE.value();
    }

    public SpiritualGuardianEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.SPIRITUAL_GUARDIAN.get());
    }

    @Override
    public double getAuraRange() {
        return MathUtils.clampedLerp(this.dataTracker.get(STRENGTH), 0, IdolItem.HOLYLIGHT.maxStrength, (float) IdolItem.HOLYLIGHT.baseAuraRange, (float) IdolItem.HOLYLIGHT.maxAuraRange);
    }

    public void tickAttacking() {
        OptionalInt time = this.dataTracker.get(CURRENT_ATTACK_DURATION);

        if(time.isEmpty() || time.getAsInt() > ATTACK_DURATION) {
            attackData = null;
            this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.empty());
            return;
        }

        this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.of(time.getAsInt() + 1));
        this.navigation.startMovingTo(attackData.target, 2f);
        this.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, attackData.target.getPos());

        if(time.getAsInt() == (int)(ATTACK_DURATION / 1.2f)) {
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_HOLY_ATTACK_01.get()).setPitch(0.5f));
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundEvents.ENTITY_WARDEN_SONIC_BOOM).setPitch(2));

            double range = getAuraRange();
            if(EntityUtils.isWithinCylinder(attackData.target, this.getPos(), range, range)) {
                if(!(this.getOwner() instanceof LivingEntity owner)) return;

                attackData.target.damage(AttackUtils.getHitSource(owner), attackData.damage * MathUtils.clampedLerp(getStrength(), 0, IdolItem.HOLYLIGHT.maxStrength, IdolItem.HOLYLIGHT.baseReflect, IdolItem.HOLYLIGHT.maxReflect));
                AudioVisualUtils.particleAroundEntity(attackData.target, ParticleTypes.WAX_OFF, 40, 0.2f, 20);
            }
        }
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        super.serverTick(owner);

        if(attackCooldown > 0) attackCooldown--;
        if(attackData != null) {
            tickAttacking();
        }

        if (this.getRiseFall() != 1) {
            AudioVisualUtils.particleCube((ServerWorld) this.getWorld(), this.getPos(), ParticleTypes.WAX_OFF, 20, 0f, 20);
        }

        Predicate<StatusEffect> predicate = PredicateUtils.createForEffectBlacklist(IdolItem.HOLYLIGHT.blacklist, IdolItem.HOLYLIGHT.includeGlobalBlacklist)
                .and(PredicateUtils.HARMFUL_EFFECT)
                .and(PredicateUtils.IS_INSTANT.negate());

        AttackUtils.cylinderAttack(owner, this.getPos(), getAuraRange(), 10, AttackUtils.AttackTarget.ALLIES_AND_USER)
                .onEachEffect(predicate, (target, effect) -> {
                    if (EntityUtils.drainEffect(target, effect, IdolItem.HOLYLIGHT.effectDrainRate)) {
                        int bonus = effect.getAmplifier() + 1;
                        AudioVisualUtils.particleLine((ServerWorld) this.getWorld(), target.getEyePos(), this.getEyePos(), ParticleTypes.SOUL, 0.25, 1, 0, 0);
                        AudioVisualUtils.playSound(getWorld(), this.getPos(), new Sound(SoundRegistry.MAGIC_SWORD_PARRY_02.get()));
                        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.WAX_OFF, 40, 0.25f, 20);

                        this.dataTracker.set(STRENGTH, Math.min(this.dataTracker.get(STRENGTH) + bonus, IdolItem.HOLYLIGHT.maxStrength));
                    }
                });
    }

    public void tryRetaliate(LivingEntity owner, float damage, DamageSource source) {
        if(attackCooldown > 1) return;
        if(isAttacking()) return;

        double auraRange = this.getAuraRange();

        if(!EntityUtils.isWithinCylinder(owner, this.getPos(), auraRange, auraRange)) return;

        if(!(source.getAttacker() instanceof LivingEntity attacker)) return;
        if(!EntityUtils.isWithinCylinder(attacker, this.getPos(), auraRange + 5, auraRange)) return;

        attackCooldown = IdolItem.HOLYLIGHT.attackCooldown + ATTACK_DURATION;
        attackData = new AttackData(attacker, Math.min(damage, IdolItem.HOLYLIGHT.maxReflectDamage));
        this.dataTracker.set(CURRENT_ATTACK_DURATION, OptionalInt.of(0));

        AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_HOLY_ATTACK_03.get()).setPitch(0f));
    }
}
