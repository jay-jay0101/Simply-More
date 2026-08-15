package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.LustrousMoxieItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.sweenus.simplyswords.api.SpellScalingProfile;
import org.jetbrains.annotations.NotNull;

public class LightbeamEntity extends AbstractVisibleAbilityEntity {
    protected static final TrackedData<Integer> CHARGE = DataTracker.registerData(LightbeamEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private boolean isFiring = false;
    private Vec3d direction = Vec3d.ZERO;
    private int duration = 0;

    public LightbeamEntity(EntityType<LightbeamEntity> entityType, World world) {
        super(entityType, world);
        setRenderDistanceMultiplier(3);
    }

    public LightbeamEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.LIGHTBEAM.get());
        setRenderDistanceMultiplier(3);
    }

    @Override
    public int getLifespan() {
        return AttackUtils.PSEUDOINFINITE_DURATION;
    }

    public int getCharge() {
        return this.dataTracker.get(CHARGE);
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        int charge = getCharge();

        if(isFiring) {
            travel(owner, charge);
        } else if(age <= this.getLifespan()){
            charge(owner, charge);
        }

        BlockHitResult floor = EntityUtils.raycastDown(this, this.getPos().offset(Direction.UP, 0.1f), getServerWorld(), 50);
        if(floor.getType() == HitResult.Type.MISS) {
            stopFiring();
            return;
        }

        if(!this.isInsideWall()) {
            this.setPosition(floor.getPos());
        }
    }

    private void charge(LivingEntity owner, int charge) {
        float range = MathUtils.clampedLerp(charge, 0, LustrousMoxieItem.SETTINGS.maxChargeTime, LustrousMoxieItem.SETTINGS.minSize, LustrousMoxieItem.SETTINGS.maxSize) + 0.2f;
        setPosition(EntityUtils.rangeAroundPoint(owner.getEyePos(), this, owner.getYaw(), range));

        this.setYaw(0);
        this.dataTracker.set(CHARGE, Math.min(charge + 1, LustrousMoxieItem.SETTINGS.maxChargeTime));
    }

    private void travel(LivingEntity owner, int charge) {
        if(duration <= 0 && age < this.getLifespan()) {
            stopFiring();
            return;
        }

        Vec3d motion = direction.multiply(MathUtils.clampedLerp(charge, 0, LustrousMoxieItem.SETTINGS.maxChargeTime, LustrousMoxieItem.SETTINGS.minSpeed, LustrousMoxieItem.SETTINGS.maxSpeed));
        this.setPosition(this.getPos().add(motion));

        float size = MathUtils.clampedLerp(charge, 0, LustrousMoxieItem.SETTINGS.maxChargeTime, LustrousMoxieItem.SETTINGS.minSize, LustrousMoxieItem.SETTINGS.maxSize) - 1;
        AttackUtils.boxAttack(owner, this.getBoundingBox().expand(size).stretch(motion), AttackUtils.AttackTarget.ENEMIES)
                .applyEffect(StatusEffects.GLOWING, LustrousMoxieItem.SETTINGS.glowDuration, 0)
                .onEach(e -> AttackUtils.getOwnedAbilities(owner, LightOrbEntity.class).stream().filter(o -> o.getPerson().isPresent() && o.getPerson().get().equals(e.getUuid())).toList().forEach(LightOrbEntity::explode))
                .damage(AttackUtils.scaleDamage(SpellScalingProfile.LIGHTNING, owner, 0, 1, LustrousMoxieItem.SETTINGS.beamDamage), owner.getDamageSources().indirectMagic(this, owner));

        duration--;
    }

    public void stopFiring() {
        if(age > getLifespan()) return;
        setAge(getLifespan());

        if(getOwner() instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(ItemRegistry.LUSTROUS_MOXIE.get(), LustrousMoxieItem.SETTINGS.cooldown);
        }

        direction = Vec3d.ZERO;
    }

    public void tryFire() {
        if(isFiring) return;

        LivingEntity owner = getOwner();
        if(owner == null) return;

        isFiring = true;
        direction = MathUtils.getDirectionalVector(owner.getYaw(), 0);
        duration = LustrousMoxieItem.SETTINGS.beamDuration;
    }

    @Override
    public int getOutroTicks() {
        return 10;
    }

    @Override
    public int getIntroTicks() {
        return 40;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(CHARGE, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.putInt("charge", getCharge());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.containsUuid("charge")) {
            this.dataTracker.set(CHARGE, nbt.getInt("charge"));
        } else {
            this.dataTracker.set(CHARGE, 0);
        }
    }

    public boolean isFired() {
        return isFiring;
    }
}
