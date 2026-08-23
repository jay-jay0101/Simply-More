package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.SoulfractureItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

public class SoulFragmentEntity extends AbstractCollectableEntity {
    protected static final TrackedData<Optional<UUID>> PERSON = DataTracker.registerData(SoulFragmentEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public final int texture;
    public float yaw = 0;
    private MovementOverride movementOverride;

    private record MovementOverride(Vec3d startPos, int duration, int remainingDuration, Type type) {
        private enum Type {
            SPAWN(SoulFragmentEntity::movementOnSpawn),
            TO_OWNER(SoulFragmentEntity::movementToUser),
            SWAP(SoulFragmentEntity::movementToOtherSide);

            private final BiConsumer<SoulFragmentEntity, LivingEntity> movementScript;

            Type(BiConsumer<SoulFragmentEntity, LivingEntity> movementScript) {
                this.movementScript = movementScript;
            }
        }

        private MovementOverride decrementDuration() {
            return new MovementOverride(startPos, duration, remainingDuration - 1, type);
        }

        private MovementOverride(Vec3d startPos, int duration, Type type) {
            this(startPos, duration, duration, type);
        }
    }

    public void rotate180() {
        this.movementOverride = new MovementOverride(this.getPos(), 20, MovementOverride.Type.SWAP);
    }

    public void startConsumption() {
        movementOverride = new MovementOverride(this.getPos(), 10, MovementOverride.Type.TO_OWNER);
    }

    public Optional<UUID> getPerson() {
        return this.dataTracker.get(PERSON);
    }

    public SoulFragmentEntity(EntityType<SoulFragmentEntity> entityType, World world) {
        super(entityType, world);
        texture = getRandom().nextBetween(1, 4);
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

    @Override
    protected void serverTick(LivingEntity owner) {
        super.serverTick(owner);

        if(movementOverride != null) {
            movementOverride.type.movementScript.accept(this, owner);
            movementOverride = movementOverride.decrementDuration();

            if(movementOverride.remainingDuration == 0) {
                movementOverride = null;
            }
        } else {
            rotateAround(owner, SoulfractureItem.SETTINGS.fragmentPassiveRotationSpeed);
        }

        Optional<UUID> person = getPerson();
        if(person.isEmpty()
                || !(getServerWorld().getEntity(person.get()) instanceof LivingEntity livingEntity)
                || livingEntity.isDead()
                || livingEntity.distanceTo(owner) > 30) {
            if(movementOverride == null || movementOverride.type != MovementOverride.Type.TO_OWNER) {
                startConsumption();
            }
        }

        if(age % 10 == 0) {
            AudioVisualUtils.particleAroundEntity(this, ParticleTypes.SOUL_FIRE_FLAME, 1, 0.05f, 0.01f);
        }

        if(!EntityUtils.isHolding(owner, ItemRegistry.SOULFRACTURE.get())) {
            discard();
        }
    }

    private void rotateAround(LivingEntity owner, float yawDelta) {
        yaw += yawDelta;
        setPosition(EntityUtils.rangeAroundPoint(owner.getEyePos(), this, yaw, (float) SoulfractureItem.SETTINGS.fragmentRadius));
    }

    private static void movementOnSpawn(SoulFragmentEntity fragment, LivingEntity owner) {
        Vec3d targetPos = EntityUtils.rangeAroundPoint(owner.getEyePos(), fragment, fragment.yaw, (float) SoulfractureItem.SETTINGS.fragmentRadius);

        MovementOverride override = fragment.movementOverride;
        fragment.setPosition(override.startPos.lerp(targetPos, 1f - MathUtils.clampedLerp(override.remainingDuration, 0, override.duration, 0f, 1f)));
    }

    private static void movementToOtherSide(SoulFragmentEntity fragment, LivingEntity owner) {
        MovementOverride override = fragment.movementOverride;

        float yawDelta = 180f / override.duration;
        AudioVisualUtils.particleAroundEntity(fragment, ParticleTypes.SOUL_FIRE_FLAME, 3, 0.2f, 0);
        AudioVisualUtils.particleAroundEntity(fragment, ParticleTypes.SOUL_FIRE_FLAME, 1, 0f, 0);
        fragment.rotateAround(owner, yawDelta);
    }

    private static void movementToUser(SoulFragmentEntity fragment, LivingEntity owner) {
        MovementOverride override = fragment.movementOverride;

        if(override.remainingDuration == 1) {
            fragment.onCollect(owner);
            owner.heal(SoulfractureItem.SETTINGS.fragmentHeal);
            fragment.discard();
        }

        fragment.setPosition(override.startPos.lerp(owner.getPos(), 1f - MathUtils.clampedLerp(override.remainingDuration, 0, override.duration, 0, 1f)));
    }

    public SoulFragmentEntity(@NotNull LivingEntity owner, Vec3d position, @NotNull LivingEntity player, float yaw) {
        super(owner, position, EntityRegistry.SOUL_FRAGMENT.get());
        texture = getRandom().nextBetween(1, 4);
        this.dataTracker.set(PERSON, Optional.of(player.getUuid()));
        this.yaw = yaw;

        this.movementOverride = new MovementOverride(position, 20, MovementOverride.Type.SPAWN);
    }

    @Override
    boolean canCollect(LivingEntity entity) {
        if(movementOverride != null && movementOverride.type == MovementOverride.Type.SPAWN) return false;

        Optional<UUID> person = getPerson();
        return person.isPresent() && person.get().equals(entity.getUuid());
    }

    @Override
    void onCollect(LivingEntity entity) {
        AudioVisualUtils.playSound(entity.getWorld(), entity.getPos(), new Sound(SoundEvents.ENTITY_ALLAY_DEATH).setVolume(0.4f));
        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.SOUL, 5, 0.25f, 0f);
        AudioVisualUtils.particleAroundEntity(entity, ParticleTypes.SOUL, 5, 0.25f, 0.5f);
    }

    @Override
    int timeToCollect() {
        return 10;
    }
}
