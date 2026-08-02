package net.rosemarythyme.simplymore.registry.item.compat;

import com.mythicmetals.component.MythicDataComponents;
import com.mythicmetals.component.PrometheumComponent;
import com.mythicmetals.item.tools.MythicToolMaterials;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.item.compat.mythicmetals.LegendaryBanglumSwordItem;
import net.rosemarythyme.simplymore.item.compat.mythicmetals.PalladiumSwordItem;
import net.rosemarythyme.simplymore.item.compat.mythicmetals.TidesingerSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.sweenus.simplyswords.config.Config;

import java.util.List;

public class MythicMetalsCompatRegistry {
    private static final WeaponAttributesConfig ATTIRBUTES_CONFIG = ConfigWrapper.attributes;

    public static final List<RegistrySupplier<Item>> ADAMANTITE_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "adamantite",
            Config.weaponAttribute.materialDamageModifier.adamantite_damageModifier.get().intValue(),
            MythicToolMaterials.ADAMANTITE
    );

    public static final List<RegistrySupplier<Item>> AQUARIUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "aquarium",
            Config.weaponAttribute.materialDamageModifier.aquarium_damageModifier.get().intValue(),
            MythicToolMaterials.AQUARIUM
    );

    public static final List<RegistrySupplier<Item>> BANGLUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "banglum",
            Config.weaponAttribute.materialDamageModifier.banglum_damageModifier.get().intValue(),
            MythicToolMaterials.BANGLUM
    );

    public static final List<RegistrySupplier<Item>> BRONZE_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "bronze",
            Config.weaponAttribute.materialDamageModifier.banglum_damageModifier.get().intValue(),
            MythicToolMaterials.BRONZE
    );

    public static final List<RegistrySupplier<Item>> CARMOT_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "carmot",
            Config.weaponAttribute.materialDamageModifier.carmot_damageModifier.get().intValue(),
            MythicToolMaterials.CARMOT
    );

    public static final List<RegistrySupplier<Item>> CELESTIUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "celestium",
            Config.weaponAttribute.materialDamageModifier.carmot_damageModifier.get().intValue(),
            MythicToolMaterials.CARMOT,
            new Item.Settings().rarity(Rarity.RARE)
    );

    public static final List<RegistrySupplier<Item>> COPPER_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "copper",
            Config.weaponAttribute.materialDamageModifier.copper_damageModifier.get().intValue(),
            MythicToolMaterials.COPPER
    );

    public static final List<RegistrySupplier<Item>> DURASTEEL_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "durasteel",
            Config.weaponAttribute.materialDamageModifier.durasteel_damageModifier.get().intValue(),
            MythicToolMaterials.DURASTEEL
    );

    public static final List<RegistrySupplier<Item>> HALLOWED_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "hallowed",
            ATTIRBUTES_CONFIG.typeDamageModifier.hallowed_damage_modifier.get(),
            MythicToolMaterials.HALLOWED,
            new Item.Settings().rarity(Rarity.UNCOMMON)
    );

    public static final List<RegistrySupplier<Item>> KYBER_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "kyber",
            Config.weaponAttribute.materialDamageModifier.kyber_damageModifier.get().intValue(),
            MythicToolMaterials.KYBER
    );

    public static final List<RegistrySupplier<Item>> LEGENDARY_BANGLUM_WEAPONS = ItemRegistry.registerSet(
            LegendaryBanglumSwordItem.class,
            "legendary_banglum",
            ATTIRBUTES_CONFIG.typeDamageModifier.legendary_banglum_damage_modifier.get(),
            MythicToolMaterials.LEGENDARY_BANGLUM,
            new Item.Settings().rarity(Rarity.UNCOMMON)
    );

    public static final List<RegistrySupplier<Item>> METALLURGIUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "metallurgium",
            Config.weaponAttribute.materialDamageModifier.metallurgium_damageModifier.get().intValue(),
            MythicToolMaterials.METALLURGIUM,
            new Item.Settings().rarity(Rarity.RARE).fireproof()
    );


    public static final List<RegistrySupplier<Item>> MYTHRIL_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "mythril",
            Config.weaponAttribute.materialDamageModifier.mythril_damageModifier.get().intValue(),
            MythicToolMaterials.MYTHRIL
    );

    public static final List<RegistrySupplier<Item>> ORICHALCUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "orichalcum",
            Config.weaponAttribute.materialDamageModifier.orichalcum_damageModifier.get().intValue(),
            MythicToolMaterials.ORICHALCUM
    );

    public static final List<RegistrySupplier<Item>> OSMIUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "osmium",
            Config.weaponAttribute.materialDamageModifier.osmium_damageModifier.get().intValue(),
            MythicToolMaterials.OSMIUM
    );

    public static final List<RegistrySupplier<Item>> PALLADIUM_WEAPONS = ItemRegistry.registerSet(
            PalladiumSwordItem.class,
            "palladium",
            Config.weaponAttribute.materialDamageModifier.palladium_damageModifier.get().intValue(),
            MythicToolMaterials.PALLADIUM,
            new Item.Settings().fireproof()
    );

    public static final List<RegistrySupplier<Item>> PROMETHEUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "prometheum",
            Config.weaponAttribute.materialDamageModifier.prometheum_damageModifier.get().intValue(),
            MythicToolMaterials.PROMETHEUM,
            new Item.Settings().component(MythicDataComponents.PROMETHEUM, PrometheumComponent.DEFAULT)
    );

    public static final List<RegistrySupplier<Item>> QUADRILLUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "quadrillum",
            Config.weaponAttribute.materialDamageModifier.quadrillum_damageModifier.get().intValue(),
            MythicToolMaterials.QUADRILLUM
    );

    public static final List<RegistrySupplier<Item>> RUNITE_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "runite",
            Config.weaponAttribute.materialDamageModifier.runite_damageModifier.get().intValue(),
            MythicToolMaterials.RUNITE
    );

    public static final List<RegistrySupplier<Item>> STAR_PLATINUM_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "star_platinum",
            Config.weaponAttribute.materialDamageModifier.starPlatinum_damageModifier.get().intValue(),
            MythicToolMaterials.STAR_PLATINUM
    );

    public static final List<RegistrySupplier<Item>> STEEL_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "steel",
            Config.weaponAttribute.materialDamageModifier.steel_damageModifier.get().intValue(),
            MythicToolMaterials.STEEL
    );

    public static final List<RegistrySupplier<Item>> STORMYX_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "stormyx",
            Config.weaponAttribute.materialDamageModifier.stormyx_damageModifier.get().intValue(),
            MythicToolMaterials.STORMYX
    );

    public static final List<RegistrySupplier<Item>> TIDESINGER_WEAPONS = ItemRegistry.registerSet(
            TidesingerSwordItem.class,
            "tidesinger",
            ATTIRBUTES_CONFIG.typeDamageModifier.tidesinger_damage_modifier.get(),
            MythicToolMaterials.TIDESINGER
    );

    public static void registerCompatItems() {
    }

    public static void addToGroup() {
        ItemRegistry.addToItemGroup(ADAMANTITE_WEAPONS);
        ItemRegistry.addToItemGroup(AQUARIUM_WEAPONS);
        ItemRegistry.addToItemGroup(BANGLUM_WEAPONS);
        ItemRegistry.addToItemGroup(BRONZE_WEAPONS);
        ItemRegistry.addToItemGroup(CARMOT_WEAPONS);
        ItemRegistry.addToItemGroup(CELESTIUM_WEAPONS);
        ItemRegistry.addToItemGroup(COPPER_WEAPONS);
        ItemRegistry.addToItemGroup(DURASTEEL_WEAPONS);
        ItemRegistry.addToItemGroup(HALLOWED_WEAPONS);
        ItemRegistry.addToItemGroup(KYBER_WEAPONS);
        ItemRegistry.addToItemGroup(LEGENDARY_BANGLUM_WEAPONS);
        ItemRegistry.addToItemGroup(METALLURGIUM_WEAPONS);
        ItemRegistry.addToItemGroup(MYTHRIL_WEAPONS);
        ItemRegistry.addToItemGroup(ORICHALCUM_WEAPONS);
        ItemRegistry.addToItemGroup(OSMIUM_WEAPONS);
        ItemRegistry.addToItemGroup(PALLADIUM_WEAPONS);
        ItemRegistry.addToItemGroup(PROMETHEUM_WEAPONS);
        ItemRegistry.addToItemGroup(QUADRILLUM_WEAPONS);
        ItemRegistry.addToItemGroup(RUNITE_WEAPONS);
        ItemRegistry.addToItemGroup(STAR_PLATINUM_WEAPONS);
        ItemRegistry.addToItemGroup(STEEL_WEAPONS);
        ItemRegistry.addToItemGroup(STORMYX_WEAPONS);
        ItemRegistry.addToItemGroup(TIDESINGER_WEAPONS);
    }
}
