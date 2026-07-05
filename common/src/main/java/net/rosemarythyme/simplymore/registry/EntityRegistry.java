package net.rosemarythyme.simplymore.registry;

//import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.CrowEntity;
import net.rosemarythyme.simplymore.entity.GhostFallingBlockEntity;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<CrowEntity>> CROW = ENTITIES.register(
            SimplyMore.identifier("crow"),
            () -> EntityType.Builder.create(CrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .makeFireImmune()
                    .build("crow")
    );

    public static final RegistrySupplier<EntityType<GhostFallingBlockEntity>> GHOST_FALLING_BLOCK = ENTITIES.register(
            SimplyMore.identifier("ghost_falling_block"),
            () -> EntityType.Builder.create((EntityType<GhostFallingBlockEntity> type, World world) -> new GhostFallingBlockEntity(type, world), SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .build("ghost_falling_block")
    );


    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(CROW, CrowEntity::createMobAttributes);
    }
}
