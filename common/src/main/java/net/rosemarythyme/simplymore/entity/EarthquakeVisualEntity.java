package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import org.jetbrains.annotations.NotNull;

public class EarthquakeVisualEntity extends AbstractVisibleAbilityEntity {
    public EarthquakeVisualEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public EarthquakeVisualEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.EARTHQUAKE.get());
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
        return 10;
    }
}
