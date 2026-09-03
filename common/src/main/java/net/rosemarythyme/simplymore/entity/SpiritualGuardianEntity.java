package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.IdolItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import org.jetbrains.annotations.NotNull;

public class SpiritualGuardianEntity extends AbstractSpiritualEntity {

    public SpiritualGuardianEntity(EntityType<SpiritualGuardianEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isAttacking() {
        return false;
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 0.11F)
                .add(EntityAttributes.GENERIC_GRAVITY, 0.004F)
                .add(EntityAttributes.GENERIC_STEP_HEIGHT, 1)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public int getLifespan() {
        return 10000000; //todo
    }

    public SpiritualGuardianEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.SPIRITUAL_GUARDIAN.get());
    }

    @Override
    public double getAuraRange() {
        return IdolItem.HOLYLIGHT.baseAuraRange;
    }
}
