package net.rosemarythyme.simplymore.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.*;
import net.rosemarythyme.simplymore.entity.legacy.*;

import java.util.ArrayList;
import java.util.List;

public class EntityRegistry {
    private static final List<RegistrySupplier<? extends EntityType<? extends AbstractAbilityPlacementEntity>>> MARKERS = new ArrayList<>();

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<CrowEntity>> CROW = registerType(
            EntityType.Builder.<CrowEntity>create(CrowEntity::new, SpawnGroup.MISC)
                    .dimensions(0.25f, 0.25f)
                    .makeFireImmune(),
            "crow"
    );

    public static final RegistrySupplier<EntityType<GhostFallingBlockEntity>> GHOST_FALLING_BLOCK = registerType(
            EntityType.Builder.<GhostFallingBlockEntity>create(GhostFallingBlockEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 1f),
            "ghost_falling_block"
    );

    public static final RegistrySupplier<EntityType<GreatSlitherFangEntity>> GREAT_SLITHER_FANG = registerType(
            EntityType.Builder.<GreatSlitherFangEntity>create(GreatSlitherFangEntity::new, SpawnGroup.MISC)
                    .dimensions(1f, 1f),
            "great_slither_fang"
    );

    public static final RegistrySupplier<EntityType<VolcanicVentEntity>> VOLCANIC_VENT =
            registerMarkerEntity("volcanic_vent", VolcanicVentEntity::new, 10/16f, 5/16f);

    public static final RegistrySupplier<EntityType<LavaLiquidEntity>> LAVA =
            registerMarkerEntity("lava", LavaLiquidEntity::new, 1f, 1f);

    public static final RegistrySupplier<EntityType<AuraOfPurityEntity>> AURA_OF_PURITY =
            registerMarkerEntity("aura_of_purity", AuraOfPurityEntity::new);

    public static final RegistrySupplier<EntityType<AuraOfCorruptionEntity>> AURA_OF_CORRUPTION =
            registerMarkerEntity("aura_of_corruption", AuraOfCorruptionEntity::new);

    public static final RegistrySupplier<EntityType<EruptionEntity>> ERUPTION =
            registerMarkerEntity("eruption", EruptionEntity::new);

    public static final RegistrySupplier<EntityType<JetstreamEntity>> JETSTREAM =
            registerMarkerEntity("jetstream", JetstreamEntity::new);

    public static final RegistrySupplier<EntityType<RiftEntity>> RIFT =
            registerMarkerEntity("rift", RiftEntity::new);

    public static <T extends Entity> RegistrySupplier<EntityType<T>> registerType(EntityType.Builder<T> builder, String name) {
        Identifier id = SimplyMore.identifier(name);
        return ENTITIES.register(id, () -> builder.build(id.toString()));
    }

    public static <T extends AbstractAbilityPlacementEntity> RegistrySupplier<EntityType<T>> registerMarkerEntity(String name, EntityType.EntityFactory<T> entity) {
        return registerMarkerEntity(name, entity, 0, 0);
    }

    public static <T extends AbstractAbilityPlacementEntity> RegistrySupplier<EntityType<T>> registerMarkerEntity(String name, EntityType.EntityFactory<T> entity, float width, float height) {
        EntityType.Builder<T> type = EntityType.Builder.create(entity, SpawnGroup.MISC)
                .dimensions(width, height)
                .disableSummon();

        RegistrySupplier<EntityType<T>> supplier = registerType(width == 0 || height  == 0 ? type.maxTrackingRange(0) : type, name);

        MARKERS.add(supplier);
        return supplier;
    }

    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(CROW, CrowEntity::createMobAttributes);

        for(var marker : MARKERS) {
            EntityAttributeRegistry.register(marker, LivingEntity::createLivingAttributes);
        }
    }
}
