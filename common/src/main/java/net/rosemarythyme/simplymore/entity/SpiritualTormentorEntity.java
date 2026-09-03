package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.IdolItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import org.jetbrains.annotations.NotNull;

public class SpiritualTormentorEntity extends AbstractSpiritualEntity {
    public SpiritualTormentorEntity(EntityType<? extends AbstractSpiritualEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpiritualTormentorEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.SPIRITUAL_TORMENTOR.get());
    }

    @Override
    public boolean isAttacking() {
        return false;
    }

    @Override
    public double getAuraRange() {
        return IdolItem.DARKSENT.baseAuraRange;
    }

    @Override
    public int getLifespan() {
        return 1000000;
    }
}
