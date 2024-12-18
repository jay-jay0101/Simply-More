package net.rosemarythyme.simplymore.entity;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class GhostFallingBlockEntity extends FallingBlockEntity implements Ownable {
    public Entity owner;
    protected static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;

    public GhostFallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public GhostFallingBlockEntity(World world, double x, double y, double z, Vec3d velocity, LivingEntity owner) {
        this(EntityType.FALLING_BLOCK, world);
        this.intersectionChecked = true;
        this.setPosition(x, y, z);
        this.setVelocity(velocity);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.dropItem = false;
        this.owner = owner;
        this.setFallingBlockPos(this.getBlockPos());
    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        if(this.getOwner() != null) {
            nbt.putUuid("Owner", this.getOwner().getUuid());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        UUID uUID;
        if (nbt.containsUuid("Owner")) {
            uUID = nbt.getUuid("Owner");
            this.owner = ((ServerWorld) this.getWorld()).getEntity(uUID);
        }
    }

    @Override
    public void onDestroyedOnLanding(Block block, BlockPos pos) {
        if (block instanceof LandingBlock) {
            this.destroy();
        }
    }

    public void destroy() {
        this.playSound(SoundEvents.BLOCK_STONE_BREAK, 1f, 1f);
        ((ServerWorld) this.getWorld()).spawnParticles(new BlockStateParticleEffect(
                        ParticleTypes.BLOCK,
                        Blocks.STONE.getDefaultState()
                ),
                this.getX(),
                this.getY(),
                this.getZ(),
                15,
                0.25f,
                0.25f,
                0.25f,
                0.5f
        );
        this.discard();
    }

    @Override
    public void tick() {
        this.timeFalling++;
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }

        this.move(MovementType.SELF, this.getVelocity());
        if (!this.getWorld().isClient) {
            BlockPos blockPos = this.getBlockPos();

            if (this.isOnGround()) {
                this.destroy();
            } else if (!this.getWorld().isClient
                    && (this.timeFalling > 100 && (blockPos.getY() <= this.getWorld().getBottomY() || blockPos.getY() > this.getWorld().getTopY()) || this.timeFalling > 600)) {
                this.destroy();
            }
        }

        this.setVelocity(this.getVelocity().multiply(0.98));

        // Hit Enemies
        Box box = new Box(
                this.getX() + 0.75,
                this.getY() + 0.75,
                this.getZ() + 0.75,
                this.getX() - 0.75,
                this.getY() - 0.75,
                this.getZ() - 0.75
        );

        List<LivingEntity> livingEntities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

        Entity ownerEntity = this.getOwner();
        if(ownerEntity == null) return;

        List<LivingEntity> targets = livingEntities.stream().filter(livingEntity ->
                livingEntity != ownerEntity
                        && !livingEntity.isTeammate(ownerEntity)
                        && !(livingEntity instanceof Ownable pet && pet.getOwner() == ownerEntity)
        ).toList();

        targets.forEach(
                target -> {
                    target.damage(this.getDamageSources().fallingBlock(this), effect.getExedrillRockDamage());
                    target.addStatusEffect(
                            new StatusEffectInstance(
                                    ModEffectsRegistry.STUNNED,
                                    effect.getExedrillRockStunTime()
                            )
                    );
                }
        );

        if(!targets.isEmpty()) {
            this.destroy();
        }

    }

    @Override
    public @Nullable Entity getOwner() {
        return this.owner;
    }
}
