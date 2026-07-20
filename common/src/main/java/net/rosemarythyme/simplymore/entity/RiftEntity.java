package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class RiftEntity extends AbstractAbilityPlacementEntity {
    private Vector3f color = new Vector3f();

    public RiftEntity(EntityType<RiftEntity> entityType, World world) {
        super(entityType, world);
    }

    public RiftEntity(@NotNull LivingEntity owner, Vec3d position, Vector3f color) {
        super(owner, position, EntityRegistry.RIFT.get());
        this.color = color;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        NbtCompound colorNbt = new NbtCompound();
        colorNbt.putFloat("r", color.x);
        colorNbt.putFloat("g", color.y);
        colorNbt.putFloat("b", color.z);

        nbt.put("Color", colorNbt);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if(nbt.contains("Color")) {
            NbtCompound colorNbt = nbt.getCompound("Color");
            this.color = new Vector3f(
                    colorNbt.getFloat("r"),
                    colorNbt.getFloat("g"),
                    colorNbt.getFloat("b")
            );
        } else {
            this.color = new Vector3f(0, 0, 0);
        }
    }

    @Override
    int getLifespan() {
        return 300;
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity owner = this.getOwner();
        if(owner == null) return;

        if(owner.getWorld().getDimension() != this.getWorld().getDimension()) discard();
        if(this.distanceTo(owner) > 15) discard();

        DustParticleEffect bigParticle = new DustParticleEffect(color, 3);
        DustParticleEffect smallParticle = new DustParticleEffect(color, 1);
        AudioVisualUtils.particleCube(this.getServerWorld(), this.getPos(), bigParticle, 2, 0.2d, 0.3d);

        AttackUtils.cubeAttack(owner, getPos(), 13, AttackUtils.AttackTarget.ENEMIES)
                .applyEffect(StatusEffects.SLOWNESS, 10, 1)
                .targetIndicator()
                .onEach((target) -> AudioVisualUtils.particleLine(this.getServerWorld(), this.getPos(), target.getPos(), smallParticle, 0.25d, 1, 0, 0))
                .filter((target) -> target.distanceTo(this) > 10)
                .onEach((target) -> {
                    double distance = target.distanceTo(this);
                    Vec3d direction = MathUtils.normalisedDirectionBetween(target.getPos(), this.getPos(), true);
                    target.setVelocity(direction.multiply(distance / 10d));
                });
    }
}
