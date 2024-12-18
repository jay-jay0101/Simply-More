package net.rosemarythyme.simplymore.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.CrowEntity;
import net.rosemarythyme.simplymore.entity.GhostFallingBlockEntity;

public class ModEntityRegistry {
    public static final EntityType<CrowEntity> CROW = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(SimplyMore.ID, "crow"),
            EntityType.Builder.create(CrowEntity::new, SpawnGroup.MISC)
                    .setDimensions(0.25f, 0.25f)
                    .makeFireImmune()
                    .build("crow")
    );

    public static final EntityType<GhostFallingBlockEntity> GHOST_FALLING_BLOCK = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(SimplyMore.ID, "ghost_falling_block"),
            EntityType.Builder.create((EntityType<GhostFallingBlockEntity> type, World world) -> new GhostFallingBlockEntity(type, world), SpawnGroup.MISC)
                    .setDimensions(0.25f, 0.25f)
                    .build("ghost_falling_block")
    );


    public static void registerModEntities() {
        SimplyMore.LOGGER.info("Registering Entities for " + SimplyMore.ID);

        FabricDefaultAttributeRegistry.register(CROW, CrowEntity.createMobAttributes());
    }
}
