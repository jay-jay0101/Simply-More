package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.PerforiscusItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

public class FlowerFieldEntity extends AbstractVisibleAbilityEntity {
    public FlowerFieldEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public FlowerFieldEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.FLOWER_FIELD.get());
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        if(!isActive()) return;

        if(this.getAge() % 20 == 0) {
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundEvents.BLOCK_CHORUS_FLOWER_GROW).randomisePitch(0.8f, 1.2f, this.getRandom()));
            AudioVisualUtils.particleCylinder(this.getServerWorld(), this.getPos().offset(Direction.UP, 0.2), ParticleTypes.CHERRY_LEAVES, 350, PerforiscusItem.SETTINGS.range, 0f, 1f);
            AudioVisualUtils.particleAroundEntity(this, ParticleTypes.SPORE_BLOSSOM_AIR, 100, 0.2f, -0.5f);
        }

        if(this.getAge() % PerforiscusItem.SETTINGS.healInterval == 0) {
            TargetUtils.cylinderSelect(owner, this.getPos(), PerforiscusItem.SETTINGS.range, 3f, TargetUtils.TargetType.ALLIES_AND_USER)
                    .onEach(this::heal);
        }
    }

    public void heal(LivingEntity entity) {
        entity.heal(1f);
        AudioVisualUtils.playSound(entity.getWorld(), entity.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_POISON_ATTACK_03.get()).setVolume(0.2f).randomisePitch(0f, 1.5f, this.getRandom()));
        AudioVisualUtils.particleAroundEntity(entity, ParticleTypes.TOTEM_OF_UNDYING, 4, 0.1f, 0.4f);
    }

    @Override
    public int getOutroTicks() {
        return 10;
    }

    @Override
    public int getIntroTicks() {
        return 10;
    }

    @Override
    public int getLifespan() {
        return PerforiscusItem.SETTINGS.duration;
    }
}
