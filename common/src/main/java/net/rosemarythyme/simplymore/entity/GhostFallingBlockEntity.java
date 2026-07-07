package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.registry.EntityRegistry;

public class GhostFallingBlockEntity extends FallingBlockEntity {
    protected static UniqueEffectConfig effect = ConfigWrapper.unique;


    public GhostFallingBlockEntity(EntityType<? extends GhostFallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public GhostFallingBlockEntity(World world, Vec3d pos, Vec3d velocity) {
        this(EntityRegistry.GHOST_FALLING_BLOCK.get(), world);
        this.refreshPositionAfterTeleport(pos);
        this.setVelocity(velocity);
    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    public void destroy() {
        this.playSound(getBlockState().getSoundGroup().getBreakSound(), 1f, 1f);

        BlockStateParticleEffect breakParticle = new BlockStateParticleEffect(
                ParticleTypes.BLOCK,
                getBlockState()
        );

        ((ServerWorld) this.getWorld()).spawnParticles(breakParticle,
                this.getX(), this.getY(), this.getZ(),
                15, 0.25f, 0.25f, 0.25f, 0.5f
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

        if (!this.getWorld().isClient() && (this.isOnGround() || this.timeFalling >= 600)) {
            this.destroy();
        }

        this.setVelocity(this.getVelocity().multiply(0.98));
    }
}
