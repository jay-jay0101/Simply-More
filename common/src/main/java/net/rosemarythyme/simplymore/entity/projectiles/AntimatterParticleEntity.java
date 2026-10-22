package net.rosemarythyme.simplymore.entity.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MatterbaneItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AntimatterParticleEntity extends AbstractAbilityProjectileEntity {
    protected static final TrackedData<Integer> PARTICLE_STATE = DataTracker.registerData(AntimatterParticleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> COLOR = DataTracker.registerData(AntimatterParticleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private TargetList trappedEntities = TargetList.empty();

    public enum ParticleState {
        STABLE,
        UNSTABLE,
        EMPOWERED
    }

    public AntimatterParticleEntity(EntityType<? extends AbstractAbilityProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public AntimatterParticleEntity(@NotNull LivingEntity owner, Vec3d position, Vec3d velocity, int color) {
        super(owner, position, velocity, EntityRegistry.ANTIMATTER_PARTICLE.get());
        this.dataTracker.set(COLOR, color);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(PARTICLE_STATE, ParticleState.STABLE.ordinal());
        builder.add(COLOR, 0x0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("color", this.dataTracker.get(COLOR));
        nbt.putInt("state", this.dataTracker.get(PARTICLE_STATE));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.contains("color")) {
            this.dataTracker.set(COLOR, nbt.getInt("color"));
        } else {
            this.dataTracker.set(COLOR, 0x0);
        }

        if (nbt.contains("state")) {
            this.dataTracker.set(PARTICLE_STATE, nbt.getInt("state"));
        } else {
            this.dataTracker.set(PARTICLE_STATE, ParticleState.STABLE.ordinal());
        }
    }

    @Override
    protected void onHit(HitResult result) {
        if(this.getState() != ParticleState.STABLE) {
            explode(false);
        }
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        ParticleState state = getState();
        switch (state) {
            case STABLE -> tryDestabilise();
            case UNSTABLE -> pullTogether(owner);
            case EMPOWERED -> empoweredTick(owner);
        }
    }

    private void empoweredTick(LivingEntity owner) {
        trapEntities(owner);
        empoweredVisuals();

        if(this.age == getLifespan() - 1) {
            this.explode(true);
        }
    }

    private void empoweredVisuals() {
        if(this.age % 5 == 0) {
            for(int i = 0; i < 10; i++) {
                Vec3d end = this.getPos().add(MathUtils.getDirectionalVector(this.getRandom().nextFloat() * 360f, (this.getRandom().nextFloat() * 180f) - 90f).multiply(MatterbaneItem.SETTINGS.particleEmpowermentRadius * 0.9f));
                AudioVisualUtils.particleLine((ServerWorld) this.getWorld(), this.getPos(), end, new DustParticleEffect(MathUtils.colorToVector(this.getColor()), 1), 0.25f, 6, 0.15f, 0);
            }

            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_THUNDER_SHOOT_IMPACT_03.get()).setVolume(0.2f).randomisePitch(0f, 2f, this.getRandom()));
        }
    }

    private void trapEntities(LivingEntity owner) {
        trappedEntities = trappedEntities.include(TargetUtils.cubeSelect(owner, this.getPos(), MatterbaneItem.SETTINGS.particleEmpowermentRadius, TargetUtils.TargetType.ENEMIES))
                .filter(LivingEntity::isAlive)
                .applyEffect(StatusEffects.SLOWNESS, 10, 1);

        trappedEntities.filter((target) -> MathUtils.cubicDistance(target.getPos(), this.getPos()) > MatterbaneItem.SETTINGS.particleEmpowermentRadius)
                .filter((target) -> MathUtils.cubicDistance(target.getPos(), this.getPos()) <= MatterbaneItem.SETTINGS.particleEmpowermentRadius + 25)
                .onEach((target) -> {
                    Pair<Float, Float> yawAndPitch = MathUtils.getYawAndPitchBetween(this.getPos(), target.getPos());
                    Vec3d newPos = EntityUtils.cubicRangeAroundPoint(this.getPos(), target, yawAndPitch.getLeft(), yawAndPitch.getRight(), MatterbaneItem.SETTINGS.particleEmpowermentRadius * 0.95f);
                    target.requestTeleport(newPos.getX(), newPos.getY(), newPos.getZ());
                });
    }

    private void tryDestabilise() {
        if(this.age >= MatterbaneItem.SETTINGS.particleStableTime) {
            setState(ParticleState.UNSTABLE);
        } else if (this.age == MatterbaneItem.SETTINGS.particleStableTime - 4) {
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_SCIFI_SHOOT_IMPACT_01.get()).setVolume(0.5f));
        }
    }

    private void pullTogether(LivingEntity owner) {
        List<AntimatterParticleEntity> nearbyParticles = this.getWorld().getNonSpectatingEntities(AntimatterParticleEntity.class, this.getBoundingBox().expand(1f));
        nearbyParticles.remove(this);

        if(!nearbyParticles.isEmpty()) {
            AntimatterParticleEntity other = nearbyParticles.getFirst();
            this.addVelocity(MathUtils.normalisedDirectionBetween(this.getPos(), other.getPos(), true).multiply(0.1f));
        }

        tryExplode(owner);
    }

    private void tryExplode(LivingEntity owner) {
        List<AntimatterParticleEntity> explodable = this.getWorld().getNonSpectatingEntities(AntimatterParticleEntity.class, this.getBoundingBox().expand(0.1f));
        if(explodable.size() > 1) {
            explodable.forEach(particle -> particle.explode(false));
            return;
        }

        TargetList enemies = TargetUtils.cubeSelect(owner, this.getPos(), 6/16f, TargetUtils.TargetType.ENEMIES);
        if(enemies.isPopulated()) {
            this.explode(false);
        }
    }

    public void explode(boolean isEmpowered) {
        if(this.isRemoved()) return;
        if(!isEmpowered && this.getState() == ParticleState.EMPOWERED) return;
        if(!(this.getWorld() instanceof ServerWorld world)) return;
        if(!((this.getOwner()) instanceof LivingEntity owner)) return;

        explosionVisuals(isEmpowered, world, owner);

        double range = isEmpowered ? MatterbaneItem.SETTINGS.particleEmpowermentRadius : MatterbaneItem.SETTINGS.explosionRange;
        float damage = isEmpowered ? MatterbaneItem.SETTINGS.empoweredExplosionDamage : MatterbaneItem.SETTINGS.explosionDamage;

        TargetUtils.cubeSelect(owner, this.getPos(), range, TargetUtils.TargetType.ENEMIES)
                .damage(damage, owner.getDamageSources().explosion(this, owner))
                .knockback(this.getPos(), MatterbaneItem.SETTINGS.explosionKnockback);

        this.discard();
    }

    private void explosionVisuals(boolean isEmpowered, ServerWorld world, LivingEntity owner) {
        AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_SCIFI_SHOOT_IMPACT_02.get()));
        AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundEvents.ENTITY_GENERIC_EXPLODE.value()).setVolume(0.25f));
        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.FLASH, 1, 0, 0);

        if(isEmpowered) {
            AudioVisualUtils.particleAroundEntity(this, new DustParticleEffect(MathUtils.colorToVector(getColor()), 1), 600, MatterbaneItem.SETTINGS.particleEmpowermentRadius, 0);
            AudioVisualUtils.applyScreenshake(world, this.getPos(), owner, 20, 2.5f, 20);
        } else {
            AudioVisualUtils.particleAroundEntity(this, new DustParticleEffect(MathUtils.colorToVector(getColor()), 1), 10, 0.5f, 0);
            AudioVisualUtils.applyScreenshake(world, this.getPos(), owner, 15, 1, 20);
        }
    }

    public boolean canEmpower(LivingEntity player) {
        return this.getState() == AntimatterParticleEntity.ParticleState.UNSTABLE && this.getOwner() instanceof LivingEntity owner && player.getUuid().equals(owner.getUuid());
    }

    public void empower() {
        AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.SPELL_FIRE.get()));
        AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.MAGIC_BOW_CHARGE_SHORT_VERSION.get()).setPitch(0.5f));

        setState(ParticleState.EMPOWERED);
        setAge(getEmpowerStartAge(), true);
    }

    public int getEmpowerStartAge() {
        return this.getLifespan() - MatterbaneItem.SETTINGS.particleEmpowermentDuration;
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public ParticleState getState() {
        ParticleState[] states = ParticleState.values();
        int ordinal = this.dataTracker.get(PARTICLE_STATE);

        if(ordinal < 0 || ordinal >= states.length) return ParticleState.STABLE;
        return states[ordinal];
    }

    public void setState(ParticleState state) {
        this.dataTracker.set(PARTICLE_STATE, state.ordinal());
    }

    @Override
    float getAirDrag() {
        return 0.9f;
    }

    @Override
    float getWaterDrag() {
        return getAirDrag();
    }

    @Override
    protected double getGravity() {
        return 0;
    }

    @Override
    public int getLifespan() {
        return MatterbaneItem.SETTINGS.particleLifespan;
    }

    @Override
    protected boolean shouldPierce(HitResult result) {
        return true;
    }
}
