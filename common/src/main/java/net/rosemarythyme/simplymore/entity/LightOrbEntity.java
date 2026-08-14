package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.LustrousMoxieItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class LightOrbEntity extends AbstractVisibleAbilityEntity {
    protected static final TrackedData<Optional<UUID>> PERSON = DataTracker.registerData(LightOrbEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private final int offset;

    public LightOrbEntity(EntityType<LightOrbEntity> entityType, World world) {
        super(entityType, world);
        this.offset = 0;
    }

    public LightOrbEntity(@NotNull LivingEntity owner, Vec3d position, LivingEntity target, int offset) {
        super(owner, position, EntityRegistry.LIGHT_ORB.get());
        this.dataTracker.set(PERSON, Optional.of(target.getUuid()));
        this.offset = offset;
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        Optional<UUID> uuid = getPerson();
        if(uuid.isEmpty()) {
            discard();
            return;
        }

        Entity entity = getServerWorld().getEntity(uuid.get());
        if(!(entity instanceof LivingEntity target)) {
            discard();
            return;
        }

        rotateAround(target);
    }

    private void rotateAround(LivingEntity target) {
        float arc = 360f / LustrousMoxieItem.SETTINGS.maxOrbs;
        float time = getServerWorld().getTime() / (20 / 120f);

        float yaw = (time % 360) + (arc * offset);

        setPosition(EntityUtils.rangeAroundPoint(target.getPos().offset(Direction.UP, target.getHeight() / 2f), this, yaw, 1f));
    }

    @Override
    public int getLifespan() {
        return LustrousMoxieItem.SETTINGS.orbDuration;
    }


    public void explode() {
        AudioVisualUtils.playSound(getServerWorld(), getPos(), new Sound(SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT).setVolume(0.4f));
        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.EXPLOSION, 1, 0, 0);
        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.FLASH, 1, 0, 0);

        LivingEntity owner = getOwner();
        if(owner != null) {
            AttackUtils.cubeAttack(owner, getPos(), 3, AttackUtils.AttackTarget.ENEMIES)
                    .forceDamage(LustrousMoxieItem.SETTINGS.explosionDamage, owner.getDamageSources().explosion(this, owner));
        }

        this.discard();
    }

    @Override
    public int getOutroTicks() {
        return 10;
    }

    @Override
    public int getIntroTicks() {
        return 10;
    }

    public Optional<UUID> getPerson() {
        return this.dataTracker.get(PERSON);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(PERSON, Optional.empty());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        this.getPerson().ifPresent(uuid -> nbt.putUuid("player", uuid));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.containsUuid("player")) {
            this.dataTracker.set(PERSON, Optional.of(nbt.getUuid("player")));
        } else {
            this.dataTracker.set(PERSON, Optional.empty());
        }
    }
}
