package net.rosemarythyme.simplymore.entity.projectiles;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MoundshifterItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class DugBlockEntity extends AbstractAbilityProjectileEntity {
    protected static final TrackedData<BlockState> STATE = DataTracker.registerData(DugBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_STATE);
    private MovementOverride movementOverride;
    private int offset;

    private record MovementOverride(Vec3d startPos, int duration, int remainingDuration, Type type) {
        private enum Type {
            SPAWN(DugBlockEntity::movementOnSpawn),
            SHOOT((block, owner) -> {});

            private final BiConsumer<DugBlockEntity, LivingEntity> movementScript;

            Type(BiConsumer<DugBlockEntity, LivingEntity> movementScript) {
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

    public BlockState getBlockState() {
        return this.dataTracker.get(STATE);
    }

    public DugBlockEntity(EntityType<DugBlockEntity> entityType, World world) {
        super(entityType, world);
        offset = 0;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(STATE, Blocks.STONE.getDefaultState());
    }

    @Override
    float getAirDrag() {
        return 0.95f;
    }

    @Override
    float getWaterDrag() {
        return 0.8f;
    }

    @Override
    protected double getGravity() {
        if(movementOverride == null || movementOverride.type != MovementOverride.Type.SHOOT) return 0f;

        return MathUtils.clampedLerp(movementOverride.duration - movementOverride.remainingDuration, 0, 40, 0f, 0.08f);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.put("BlockState", NbtHelper.fromBlockState(getBlockState()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.containsUuid("BlockState")) {
            this.dataTracker.set(STATE, NbtHelper.toBlockState(this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("BlockState")));
        } else {
            this.dataTracker.set(STATE, Blocks.STONE.getDefaultState());
        }
    }

    protected void serverTick(LivingEntity owner) {
        if(movementOverride != null) {
            movementOverride.type.movementScript.accept(this, owner);
            movementOverride = movementOverride.decrementDuration();

            if(movementOverride.remainingDuration == 0) {
                movementOverride = null;
            }
        } else {
            moveBehind(owner);

            if(!EntityUtils.isHolding(owner, ItemRegistry.MOUNDSHIFTER.get())) {
                discard();
            }
        }

        if(ActiveAbilityManager.SERVER.isInAbility(owner, ActiveAbilityManager.Type.DRILL)) {
            discard();
        }

        if(age % 10 == 0) {
            AudioVisualUtils.particleAroundEntity(this, new BlockStateParticleEffect(ParticleTypes.BLOCK, getBlockState()), 1, 0.05f, 0.01f);
        }
    }

    private void moveBehind(LivingEntity owner) {
        setPosition(getBehindPos(owner, this));
        this.setRotation(owner.getYaw(), 0);
    }

    @Override
    protected void onHit(HitResult result) {
        BlockState state = getBlockState();
        AudioVisualUtils.playSound(getWorld(), result.getPos(), new Sound(state.getSoundGroup().getBreakSound()));
        AudioVisualUtils.particleAroundEntity(this, new BlockStateParticleEffect(ParticleTypes.BLOCK, state), 40, 0.5f, 0.4f);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity owner = getOwner();
        if(owner == null) return;

        if(!(entityHitResult.getEntity() instanceof LivingEntity target)) return;

        new TargetList(target)
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN), MoundshifterItem.SETTINGS.blockStunTime, 0)
                .damage(MoundshifterItem.SETTINGS.blockDamage, owner.getDamageSources().explosion(this, owner));
    }

    public void tryFire() {
        if(offset == 0 && (movementOverride == null || movementOverride.type != MovementOverride.Type.SHOOT)) {
            movementOverride = new MovementOverride(this.getPos(), AttackUtils.PSEUDOINFINITE_DURATION, MovementOverride.Type.SHOOT);

            Entity owner = getOwner();
            if(owner == null) return;

            this.setVelocity(MathUtils.getDirectionalVector(owner.getYaw(), owner.getPitch()).multiply(MoundshifterItem.SETTINGS.blockSpeed));
            this.velocityModified = true;
        } else {
            offset--;
        }
    }

    @Override
    protected boolean canCollide(HitResult result) {
        return movementOverride != null && movementOverride.type == MovementOverride.Type.SHOOT;
    }

    private static Vec3d getBehindPos(LivingEntity owner, DugBlockEntity block) {
        Vec3d direct = EntityUtils.rangeAroundPoint(owner.getEyePos().offset(Direction.UP, 0.5f), block, owner.getYaw() + 180, 1);

        float offsetPos;
        if(block.offset % 2 == 0) {
            offsetPos = (float) block.offset / 2;
        } else {
            offsetPos = (float) (block.offset + 1) / 2;
            offsetPos = (float) -Math.floor(offsetPos);
        }

        Vec3d tangent = MathUtils.getDirectionalVector(owner.getYaw() + 90, 0);

        return direct.add(tangent.multiply(offsetPos * 1.3f));
    }

    private static void movementOnSpawn(DugBlockEntity block, LivingEntity owner) {
        Vec3d targetPos = getBehindPos(owner, block);

        MovementOverride override = block.movementOverride;
        block.setPosition(override.startPos.lerp(targetPos, 1f - MathUtils.clampedLerp(override.remainingDuration, 0, override.duration, 0f, 1f)));
    }

    public DugBlockEntity(@NotNull LivingEntity owner, Vec3d position, BlockState state, int offset) {
        super(owner, position, new Vec3d(0, 0, 0), EntityRegistry.DUG_BLOCK.get());
        this.offset = offset;
        this.dataTracker.set(STATE, state);

        this.movementOverride = new MovementOverride(position, 10, MovementOverride.Type.SPAWN);
    }
}
