package net.rosemarythyme.simplymore.registry;

//import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.*;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<CrowEntity>> CROW = registerType(
            EntityType.Builder.create(CrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .makeFireImmune(),
            "crow"
    );

    public static final RegistrySupplier<EntityType<GhostFallingBlockEntity>> GHOST_FALLING_BLOCK = registerType(
            EntityType.Builder.<GhostFallingBlockEntity>create(GhostFallingBlockEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 1f),
            "ghost_falling_block"
    );

    public static final RegistrySupplier<EntityType<AuraOfPurityEntity>> AURA_OF_PURITY =
            registerMarkerEntity("aura_of_purity", AuraOfPurityEntity::new);

    public static final RegistrySupplier<EntityType<AuraOfCorruptionEntity>> AURA_OF_CORRUPTION =
            registerMarkerEntity("aura_of_corruption", AuraOfCorruptionEntity::new);

    public static final RegistrySupplier<EntityType<AuraOfCorruptionEntity>> JETSTREAM =
            registerMarkerEntity("jetstream", AuraOfCorruptionEntity::new);

    public static <T extends Entity> RegistrySupplier<EntityType<T>> registerType(EntityType.Builder<T> builder, String name) {
        Identifier id = SimplyMore.identifier(name);
        return ENTITIES.register(id, () -> builder.build(id.toString()));
    }

    public static <T extends AbstractAbilityPlacementEntity> RegistrySupplier<EntityType<T>> registerMarkerEntity(String name, EntityType.EntityFactory<T> entity) {
        return registerType(EntityType.Builder.create(entity, SpawnGroup.MISC)
                .dimensions(0f, 0f)
                .maxTrackingRange(0)
                .disableSummon(), name);
    }

    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(CROW, CrowEntity::createMobAttributes);
    }
}
