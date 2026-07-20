package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class JetstreamEntity extends AbstractAbilityPlacementEntity {
    public JetstreamEntity(EntityType<JetstreamEntity> entityType, World world) {
        super(entityType, world);
    }

    public JetstreamEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.JETSTREAM.get());
    }

    @Override
    int getLifespan() {
        return 1200;
    }

    public void visual() {
        AudioVisualUtils.particleCuboid(
                this.getServerWorld(), this.getPos().add(0d, 1.5d, 0d),
                new DustParticleEffect(new Vector3f(1f, 1f, 1f), 2),
                0.25d, 1.5d, 15, 0.3d
        );

        AudioVisualUtils.playSound(getServerWorld(), this.getPos(), new Sound(SoundEvents.ENTITY_HORSE_BREATHE, 0.6f, 0.4f));
    }

    @Override
    public void tick() {
        super.tick();
        visual();

        LivingEntity owner = this.getOwner();
        if(owner == null) return;

        AttackUtils.cuboidAttack(owner, owner.getPos().add(0,2.625,0), 0.5, 3.625, AttackUtils.AttackTarget.OTHERS_AND_USER_POSITIVELY)
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LIGHTWEIGHT), 35, 0)
                .addVelocity(0d, 0.1d, 0d)
                .filter((entity) -> entity.getVelocity().getY() < 0)
                .addVelocity(0d, 0.45d, 0d);
    }
}
