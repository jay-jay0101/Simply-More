package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.projectiles.CrowProjectileEntity;
import net.rosemarythyme.simplymore.item.uniques.DeathsEyrieItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.SoundEventRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.SummonUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class CrowEmitterEntity extends AbstractAbilityPlacementEntity {
    private final int strength;

    public CrowEmitterEntity(EntityType<CrowEmitterEntity> entityType, World world) {
        super(entityType, world);
        this.strength = 0;
    }

    public CrowEmitterEntity(@NotNull LivingEntity owner, Vec3d position, float yaw, int strength) {
        super(owner, position, EntityRegistry.CROW_EMITTER.get());
        this.setRotation(yaw, 0);

        this.strength = strength;
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        DustParticleEffect effect = new DustParticleEffect(new Vector3f(0, 0, 0), 3);
        AudioVisualUtils.particleAroundEntity(this, effect, 40, 0.2f, 0f);

        Random random = this.getRandom();
        float yaw = this.getYaw();

        Vec3d tangent = MathUtils.getDirectionalVector(yaw + 90f, 0);

        float xzOffset = 0.75f * (random.nextFloat() * 2 - 1);
        float yOffset = 0.75f * (random.nextFloat() * 2 - 1);

        Vec3d pos = this.getPos().add(tangent.multiply(xzOffset)).offset(Direction.UP, yOffset);
        SummonUtils.spawnProjectile(new CrowProjectileEntity(owner, pos, this.getYaw()), owner);

        if(this.getAge() % 20 == 2) {
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundEventRegistry.SUMMON_GUARDIAN.get()).randomisePitch(0.25f, 0.7f, this.getRandom()));
            AudioVisualUtils.applyScreenshake(this.getServerWorld(), this.getPos(), owner, 12, 1.5f, 30);
        }
    }

    @Override
    public int getOutroTicks() {
        return 0;
    }

    @Override
    public int getIntroTicks() {
        return 0;
    }

    @Override
    public int getLifespan() {
        return strength * DeathsEyrieItem.SETTINGS.durationPerCrow;
    }
}
