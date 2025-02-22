package net.rosemarythyme.simplymore.fabric.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.compat.*;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.registry.compat.MythicMetalsCompatProxy;
import nourl.mythicmetals.item.tools.MythicToolMaterials;

import java.util.List;

public class MythicMetalsCompat {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;

    public static final RegistrySupplier<Item> ADAMANTITE_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "adamantite_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "adamantite_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "adamantite_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_LANCE = ModItemsRegistry.ITEMS.register(
            "adamantite_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getLanceDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_KHOPESH = ModItemsRegistry.ITEMS.register(
            "adamantite_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_DAGGER = ModItemsRegistry.ITEMS.register(
            "adamantite_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_PERNACH = ModItemsRegistry.ITEMS.register(
            "adamantite_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getPernachDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "adamantite_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "adamantite_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> ADAMANTITE_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "adamantite_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ADAMANTITE,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getAdamantiteWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:adamantite_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "aquarium_great_katana",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "aquarium_grandsword",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "aquarium_backhand_blade",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_LANCE = ModItemsRegistry.ITEMS.register(
            "aquarium_lance",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "aquarium_khopesh",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "aquarium_dagger",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "aquarium_pernach",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "aquarium_quarterstaff",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "aquarium_great_spear",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> AQUARIUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "aquarium_deer_horns",
            () -> new CompatAquariumSwordItem(
                    MythicToolMaterials.AQUARIUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getAquariumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "banglum_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "banglum_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "banglum_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_LANCE = ModItemsRegistry.ITEMS.register(
            "banglum_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "banglum_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "banglum_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "banglum_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "banglum_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "banglum_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BANGLUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "banglum_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BANGLUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getBanglumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "bronze_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "bronze_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "bronze_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_LANCE = ModItemsRegistry.ITEMS.register(
            "bronze_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getLanceDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_KHOPESH = ModItemsRegistry.ITEMS.register(
            "bronze_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_DAGGER = ModItemsRegistry.ITEMS.register(
            "bronze_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_PERNACH = ModItemsRegistry.ITEMS.register(
            "bronze_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getPernachDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "bronze_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "bronze_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> BRONZE_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "bronze_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.BRONZE,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getBronzeWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:bronze_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "copper_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "copper_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "copper_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_LANCE = ModItemsRegistry.ITEMS.register(
            "copper_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getLanceDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_KHOPESH = ModItemsRegistry.ITEMS.register(
            "copper_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_DAGGER = ModItemsRegistry.ITEMS.register(
            "copper_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_PERNACH = ModItemsRegistry.ITEMS.register(
            "copper_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getPernachDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "copper_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "copper_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> COPPER_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "copper_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.COPPER,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getCopperWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "minecraft:copper_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "kyber_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "kyber_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "kyber_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_LANCE = ModItemsRegistry.ITEMS.register(
            "kyber_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getLanceDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_KHOPESH = ModItemsRegistry.ITEMS.register(
            "kyber_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_DAGGER = ModItemsRegistry.ITEMS.register(
            "kyber_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_PERNACH = ModItemsRegistry.ITEMS.register(
            "kyber_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getPernachDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "kyber_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "kyber_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> KYBER_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "kyber_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.KYBER,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getKyberWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:kyber_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "carmot_great_katana",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "carmot_grandsword",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "carmot_backhand_blade",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_LANCE = ModItemsRegistry.ITEMS.register(
            "carmot_lance",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getLanceDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_KHOPESH = ModItemsRegistry.ITEMS.register(
            "carmot_khopesh",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_DAGGER = ModItemsRegistry.ITEMS.register(
            "carmot_dagger",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_PERNACH = ModItemsRegistry.ITEMS.register(
            "carmot_pernach",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getPernachDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "carmot_quarterstaff",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "carmot_great_spear",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> CARMOT_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "carmot_deer_horns",
            () -> new CompatCarmotSwordItem(
                    MythicToolMaterials.CARMOT,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getCarmotWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:carmot_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "mythril_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "mythril_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "mythril_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_LANCE = ModItemsRegistry.ITEMS.register(
            "mythril_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getLanceDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_KHOPESH = ModItemsRegistry.ITEMS.register(
            "mythril_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_DAGGER = ModItemsRegistry.ITEMS.register(
            "mythril_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_PERNACH = ModItemsRegistry.ITEMS.register(
            "mythril_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getPernachDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "mythril_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "mythril_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> MYTHRIL_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "mythril_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.MYTHRIL,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getMythrilWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:mythril_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "orichalcum_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "orichalcum_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "orichalcum_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_LANCE = ModItemsRegistry.ITEMS.register(
            "orichalcum_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "orichalcum_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "orichalcum_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "orichalcum_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "orichalcum_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "orichalcum_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> ORICHALCUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "orichalcum_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.ORICHALCUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getOrichalcumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:orichalcum_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "osmium_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "osmium_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "osmium_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_LANCE = ModItemsRegistry.ITEMS.register(
            "osmium_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "osmium_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "osmium_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "osmium_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "osmium_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "osmium_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> OSMIUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "osmium_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.OSMIUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getOsmiumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:osmium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "celestium_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "celestium_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    true,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "celestium_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_LANCE = ModItemsRegistry.ITEMS.register(
            "celestium_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    true,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "celestium_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "celestium_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "celestium_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "celestium_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "celestium_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> CELESTIUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "celestium_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.CELESTIUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getCelestiumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:celestium_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "durasteel_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "durasteel_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "durasteel_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_LANCE = ModItemsRegistry.ITEMS.register(
            "durasteel_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getLanceDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_KHOPESH = ModItemsRegistry.ITEMS.register(
            "durasteel_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_DAGGER = ModItemsRegistry.ITEMS.register(
            "durasteel_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_PERNACH = ModItemsRegistry.ITEMS.register(
            "durasteel_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getPernachDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "durasteel_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "durasteel_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> DURASTEEL_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "durasteel_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.DURASTEEL,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getDurasteelWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:durasteel_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "hallowed_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "hallowed_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    true,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "hallowed_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_LANCE = ModItemsRegistry.ITEMS.register(
            "hallowed_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getLanceDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    true,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_KHOPESH = ModItemsRegistry.ITEMS.register(
            "hallowed_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_DAGGER = ModItemsRegistry.ITEMS.register(
            "hallowed_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_PERNACH = ModItemsRegistry.ITEMS.register(
            "hallowed_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getPernachDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "hallowed_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "hallowed_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> HALLOWED_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "hallowed_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.HALLOWED,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getHallowedWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:hallowed_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_great_katana",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_grandsword",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    true,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_backhand_blade",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_LANCE = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_lance",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    true,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_khopesh",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_dagger",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_pernach",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_quarterstaff",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_great_spear",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> LEGENDARY_BANGLUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "legendary_banglum_deer_horns",
            () -> new CompatLegendaryBanglumSwordItem(
                    MythicToolMaterials.LEGENDARY_BANGLUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getLegendaryBanglumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .rarity(Rarity.UNCOMMON),
                    false,
                    false,
                    "mythicmetals:banglum_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "metallurgium_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "metallurgium_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    true,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "metallurgium_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_LANCE = ModItemsRegistry.ITEMS.register(
            "metallurgium_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    false,
                    true,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "metallurgium_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "metallurgium_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "metallurgium_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "metallurgium_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.RARE),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "metallurgium_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> METALLURGIUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "metallurgium_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.METALLURGIUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getMetallurgiumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:metallurgium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "palladium_great_katana",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "palladium_grandsword",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    true,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "palladium_backhand_blade",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_LANCE = ModItemsRegistry.ITEMS.register(
            "palladium_lance",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    true,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "palladium_khopesh",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "palladium_dagger",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "palladium_pernach",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "palladium_quarterstaff",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "palladium_great_spear",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );

    public static final RegistrySupplier<Item> PALLADIUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "palladium_deer_horns",
            () -> new CompatPalladiumSwordItem(
                    MythicToolMaterials.PALLADIUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getPalladiumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "mythicmetals:palladium_ingot"
            )
    );
    public static final RegistrySupplier<Item> PROMETHEUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "prometheum_great_katana",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "prometheum_grandsword",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "prometheum_backhand_blade",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_LANCE = ModItemsRegistry.ITEMS.register(
            "prometheum_lance",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "prometheum_khopesh",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "prometheum_dagger",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "prometheum_pernach",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "prometheum_quarterstaff",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "prometheum_great_spear",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> PROMETHEUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "prometheum_deer_horns",
            () -> new CompatPrometheumSwordItem(
                    MythicToolMaterials.PROMETHEUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getPrometheumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:prometheum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "quadrillum_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "quadrillum_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "quadrillum_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_LANCE = ModItemsRegistry.ITEMS.register(
            "quadrillum_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "quadrillum_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "quadrillum_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "quadrillum_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "quadrillum_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "quadrillum_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> QUADRILLUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "quadrillum_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.QUADRILLUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getQuadrillumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:quadrillum_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "runite_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "runite_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "runite_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_LANCE = ModItemsRegistry.ITEMS.register(
            "runite_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getLanceDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_KHOPESH = ModItemsRegistry.ITEMS.register(
            "runite_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_DAGGER = ModItemsRegistry.ITEMS.register(
            "runite_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_PERNACH = ModItemsRegistry.ITEMS.register(
            "runite_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getPernachDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "runite_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "runite_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNITE_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "runite_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.RUNITE,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getRuniteWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:runite_ingot"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "star_platinum_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "star_platinum_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "star_platinum_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_LANCE = ModItemsRegistry.ITEMS.register(
            "star_platinum_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getLanceDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_KHOPESH = ModItemsRegistry.ITEMS.register(
            "star_platinum_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_DAGGER = ModItemsRegistry.ITEMS.register(
            "star_platinum_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_PERNACH = ModItemsRegistry.ITEMS.register(
            "star_platinum_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getPernachDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "star_platinum_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "star_platinum_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STAR_PLATINUM_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "star_platinum_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STAR_PLATINUM,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getStarPlatinumWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:star_platinum"
            )
    );

    public static final RegistrySupplier<Item> STEEL_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "steel_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "steel_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "steel_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_LANCE = ModItemsRegistry.ITEMS.register(
            "steel_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getLanceDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_KHOPESH = ModItemsRegistry.ITEMS.register(
            "steel_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_DAGGER = ModItemsRegistry.ITEMS.register(
            "steel_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_PERNACH = ModItemsRegistry.ITEMS.register(
            "steel_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getPernachDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "steel_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "steel_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STEEL_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "steel_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STEEL,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getSteelWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:steel_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "stormyx_great_katana",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "stormyx_grandsword",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "stormyx_backhand_blade",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_LANCE = ModItemsRegistry.ITEMS.register(
            "stormyx_lance",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getLanceDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_KHOPESH = ModItemsRegistry.ITEMS.register(
            "stormyx_khopesh",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_DAGGER = ModItemsRegistry.ITEMS.register(
            "stormyx_dagger",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_PERNACH = ModItemsRegistry.ITEMS.register(
            "stormyx_pernach",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getPernachDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "stormyx_quarterstaff",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "stormyx_great_spear",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> STORMYX_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "stormyx_deer_horns",
            () -> new CompatSwordItem(
                    MythicToolMaterials.STORMYX,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getStormyxWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:stormyx_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "tidesinger_great_katana",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "tidesinger_grandsword",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "tidesinger_backhand_blade",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_LANCE = ModItemsRegistry.ITEMS.register(
            "tidesinger_lance",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getLanceDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_KHOPESH = ModItemsRegistry.ITEMS.register(
            "tidesinger_khopesh",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_DAGGER = ModItemsRegistry.ITEMS.register(
            "tidesinger_dagger",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_PERNACH = ModItemsRegistry.ITEMS.register(
            "tidesinger_pernach",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getPernachDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "tidesinger_quarterstaff",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "tidesinger_great_spear",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static final RegistrySupplier<Item> TIDESINGER_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "tidesinger_deer_horns",
            () -> new CompatTidesingerSwordItem(
                    MythicToolMaterials.TIDESINGER,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getTidesingerWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "mythicmetals:aquarium_ingot"
            )
    );

    public static void addToGroup(List<RegistrySupplier<Item>> entries) {
        entries.add(ADAMANTITE_GREAT_KATANA);
        entries.add(ADAMANTITE_GRANDSWORD);
        entries.add(ADAMANTITE_BACKHAND_BLADE);
        entries.add(ADAMANTITE_LANCE);
        entries.add(ADAMANTITE_KHOPESH);
        entries.add(ADAMANTITE_DAGGER);
        entries.add(ADAMANTITE_PERNACH);
        entries.add(ADAMANTITE_QUARTERSTAFF);
        entries.add(ADAMANTITE_GREAT_SPEAR);
        entries.add(ADAMANTITE_DEER_HORNS);
        entries.add(AQUARIUM_GREAT_KATANA);
        entries.add(AQUARIUM_GRANDSWORD);
        entries.add(AQUARIUM_BACKHAND_BLADE);
        entries.add(AQUARIUM_LANCE);
        entries.add(AQUARIUM_KHOPESH);
        entries.add(AQUARIUM_DAGGER);
        entries.add(AQUARIUM_PERNACH);
        entries.add(AQUARIUM_QUARTERSTAFF);
        entries.add(AQUARIUM_GREAT_SPEAR);
        entries.add(AQUARIUM_DEER_HORNS);
        entries.add(BANGLUM_GREAT_KATANA);
        entries.add(BANGLUM_GRANDSWORD);
        entries.add(BANGLUM_BACKHAND_BLADE);
        entries.add(BANGLUM_LANCE);
        entries.add(BANGLUM_KHOPESH);
        entries.add(BANGLUM_DAGGER);
        entries.add(BANGLUM_PERNACH);
        entries.add(BANGLUM_QUARTERSTAFF);
        entries.add(BANGLUM_GREAT_SPEAR);
        entries.add(BANGLUM_DEER_HORNS);
        entries.add(BRONZE_GREAT_KATANA);
        entries.add(BRONZE_GRANDSWORD);
        entries.add(BRONZE_BACKHAND_BLADE);
        entries.add(BRONZE_LANCE);
        entries.add(BRONZE_KHOPESH);
        entries.add(BRONZE_DAGGER);
        entries.add(BRONZE_PERNACH);
        entries.add(BRONZE_QUARTERSTAFF);
        entries.add(BRONZE_GREAT_SPEAR);
        entries.add(BRONZE_DEER_HORNS);
        entries.add(COPPER_GREAT_KATANA);
        entries.add(COPPER_GRANDSWORD);
        entries.add(COPPER_BACKHAND_BLADE);
        entries.add(COPPER_LANCE);
        entries.add(COPPER_KHOPESH);
        entries.add(COPPER_DAGGER);
        entries.add(COPPER_PERNACH);
        entries.add(COPPER_QUARTERSTAFF);
        entries.add(COPPER_GREAT_SPEAR);
        entries.add(COPPER_DEER_HORNS);
        entries.add(KYBER_GREAT_KATANA);
        entries.add(KYBER_GRANDSWORD);
        entries.add(KYBER_BACKHAND_BLADE);
        entries.add(KYBER_LANCE);
        entries.add(KYBER_KHOPESH);
        entries.add(KYBER_DAGGER);
        entries.add(KYBER_PERNACH);
        entries.add(KYBER_QUARTERSTAFF);
        entries.add(KYBER_GREAT_SPEAR);
        entries.add(KYBER_DEER_HORNS);
        entries.add(CARMOT_GREAT_KATANA);
        entries.add(CARMOT_GRANDSWORD);
        entries.add(CARMOT_BACKHAND_BLADE);
        entries.add(CARMOT_LANCE);
        entries.add(CARMOT_KHOPESH);
        entries.add(CARMOT_DAGGER);
        entries.add(CARMOT_PERNACH);
        entries.add(CARMOT_QUARTERSTAFF);
        entries.add(CARMOT_GREAT_SPEAR);
        entries.add(CARMOT_DEER_HORNS);
        entries.add(MYTHRIL_GREAT_KATANA);
        entries.add(MYTHRIL_GRANDSWORD);
        entries.add(MYTHRIL_BACKHAND_BLADE);
        entries.add(MYTHRIL_LANCE);
        entries.add(MYTHRIL_KHOPESH);
        entries.add(MYTHRIL_DAGGER);
        entries.add(MYTHRIL_PERNACH);
        entries.add(MYTHRIL_QUARTERSTAFF);
        entries.add(MYTHRIL_GREAT_SPEAR);
        entries.add(MYTHRIL_DEER_HORNS);
        entries.add(OSMIUM_GREAT_KATANA);
        entries.add(OSMIUM_GRANDSWORD);
        entries.add(OSMIUM_BACKHAND_BLADE);
        entries.add(OSMIUM_LANCE);
        entries.add(OSMIUM_KHOPESH);
        entries.add(OSMIUM_DAGGER);
        entries.add(OSMIUM_PERNACH);
        entries.add(OSMIUM_QUARTERSTAFF);
        entries.add(OSMIUM_GREAT_SPEAR);
        entries.add(OSMIUM_DEER_HORNS);
        entries.add(ORICHALCUM_GREAT_KATANA);
        entries.add(ORICHALCUM_GRANDSWORD);
        entries.add(ORICHALCUM_BACKHAND_BLADE);
        entries.add(ORICHALCUM_LANCE);
        entries.add(ORICHALCUM_KHOPESH);
        entries.add(ORICHALCUM_DAGGER);
        entries.add(ORICHALCUM_PERNACH);
        entries.add(ORICHALCUM_QUARTERSTAFF);
        entries.add(ORICHALCUM_GREAT_SPEAR);
        entries.add(ORICHALCUM_DEER_HORNS);
        entries.add(CELESTIUM_GREAT_KATANA);
        entries.add(CELESTIUM_GRANDSWORD);
        entries.add(CELESTIUM_BACKHAND_BLADE);
        entries.add(CELESTIUM_LANCE);
        entries.add(CELESTIUM_KHOPESH);
        entries.add(CELESTIUM_DAGGER);
        entries.add(CELESTIUM_PERNACH);
        entries.add(CELESTIUM_QUARTERSTAFF);
        entries.add(CELESTIUM_GREAT_SPEAR);
        entries.add(CELESTIUM_DEER_HORNS);
        entries.add(DURASTEEL_GREAT_KATANA);
        entries.add(DURASTEEL_GRANDSWORD);
        entries.add(DURASTEEL_BACKHAND_BLADE);
        entries.add(DURASTEEL_LANCE);
        entries.add(DURASTEEL_KHOPESH);
        entries.add(DURASTEEL_DAGGER);
        entries.add(DURASTEEL_PERNACH);
        entries.add(DURASTEEL_QUARTERSTAFF);
        entries.add(DURASTEEL_GREAT_SPEAR);
        entries.add(DURASTEEL_DEER_HORNS);
        entries.add(HALLOWED_GREAT_KATANA);
        entries.add(HALLOWED_GRANDSWORD);
        entries.add(HALLOWED_BACKHAND_BLADE);
        entries.add(HALLOWED_LANCE);
        entries.add(HALLOWED_KHOPESH);
        entries.add(HALLOWED_DAGGER);
        entries.add(HALLOWED_PERNACH);
        entries.add(HALLOWED_QUARTERSTAFF);
        entries.add(HALLOWED_GREAT_SPEAR);
        entries.add(HALLOWED_DEER_HORNS);
        entries.add(LEGENDARY_BANGLUM_GREAT_KATANA);
        entries.add(LEGENDARY_BANGLUM_GRANDSWORD);
        entries.add(LEGENDARY_BANGLUM_BACKHAND_BLADE);
        entries.add(LEGENDARY_BANGLUM_LANCE);
        entries.add(LEGENDARY_BANGLUM_KHOPESH);
        entries.add(LEGENDARY_BANGLUM_DAGGER);
        entries.add(LEGENDARY_BANGLUM_PERNACH);
        entries.add(LEGENDARY_BANGLUM_QUARTERSTAFF);
        entries.add(LEGENDARY_BANGLUM_GREAT_SPEAR);
        entries.add(LEGENDARY_BANGLUM_DEER_HORNS);
        entries.add(METALLURGIUM_GREAT_KATANA);
        entries.add(METALLURGIUM_GRANDSWORD);
        entries.add(METALLURGIUM_BACKHAND_BLADE);
        entries.add(METALLURGIUM_LANCE);
        entries.add(METALLURGIUM_KHOPESH);
        entries.add(METALLURGIUM_DAGGER);
        entries.add(METALLURGIUM_PERNACH);
        entries.add(METALLURGIUM_QUARTERSTAFF);
        entries.add(METALLURGIUM_GREAT_SPEAR);
        entries.add(METALLURGIUM_DEER_HORNS);
        entries.add(PALLADIUM_GREAT_KATANA);
        entries.add(PALLADIUM_GRANDSWORD);
        entries.add(PALLADIUM_BACKHAND_BLADE);
        entries.add(PALLADIUM_LANCE);
        entries.add(PALLADIUM_KHOPESH);
        entries.add(PALLADIUM_DAGGER);
        entries.add(PALLADIUM_PERNACH);
        entries.add(PALLADIUM_QUARTERSTAFF);
        entries.add(PALLADIUM_GREAT_SPEAR);
        entries.add(PALLADIUM_DEER_HORNS);
        entries.add(PROMETHEUM_GREAT_KATANA);
        entries.add(PROMETHEUM_GRANDSWORD);
        entries.add(PROMETHEUM_BACKHAND_BLADE);
        entries.add(PROMETHEUM_LANCE);
        entries.add(PROMETHEUM_KHOPESH);
        entries.add(PROMETHEUM_DAGGER);
        entries.add(PROMETHEUM_PERNACH);
        entries.add(PROMETHEUM_QUARTERSTAFF);
        entries.add(PROMETHEUM_GREAT_SPEAR);
        entries.add(PROMETHEUM_DEER_HORNS);
        entries.add(QUADRILLUM_GREAT_KATANA);
        entries.add(QUADRILLUM_GRANDSWORD);
        entries.add(QUADRILLUM_BACKHAND_BLADE);
        entries.add(QUADRILLUM_LANCE);
        entries.add(QUADRILLUM_KHOPESH);
        entries.add(QUADRILLUM_DAGGER);
        entries.add(QUADRILLUM_PERNACH);
        entries.add(QUADRILLUM_QUARTERSTAFF);
        entries.add(QUADRILLUM_GREAT_SPEAR);
        entries.add(QUADRILLUM_DEER_HORNS);
        entries.add(RUNITE_GREAT_KATANA);
        entries.add(RUNITE_GRANDSWORD);
        entries.add(RUNITE_BACKHAND_BLADE);
        entries.add(RUNITE_LANCE);
        entries.add(RUNITE_KHOPESH);
        entries.add(RUNITE_DAGGER);
        entries.add(RUNITE_PERNACH);
        entries.add(RUNITE_QUARTERSTAFF);
        entries.add(RUNITE_GREAT_SPEAR);
        entries.add(RUNITE_DEER_HORNS);
        entries.add(STAR_PLATINUM_GREAT_KATANA);
        entries.add(STAR_PLATINUM_GRANDSWORD);
        entries.add(STAR_PLATINUM_BACKHAND_BLADE);
        entries.add(STAR_PLATINUM_LANCE);
        entries.add(STAR_PLATINUM_KHOPESH);
        entries.add(STAR_PLATINUM_DAGGER);
        entries.add(STAR_PLATINUM_PERNACH);
        entries.add(STAR_PLATINUM_QUARTERSTAFF);
        entries.add(STAR_PLATINUM_GREAT_SPEAR);
        entries.add(STAR_PLATINUM_DEER_HORNS);
        entries.add(STEEL_GREAT_KATANA);
        entries.add(STEEL_GRANDSWORD);
        entries.add(STEEL_BACKHAND_BLADE);
        entries.add(STEEL_LANCE);
        entries.add(STEEL_KHOPESH);
        entries.add(STEEL_DAGGER);
        entries.add(STEEL_PERNACH);
        entries.add(STEEL_QUARTERSTAFF);
        entries.add(STEEL_GREAT_SPEAR);
        entries.add(STEEL_DEER_HORNS);
        entries.add(STORMYX_GREAT_KATANA);
        entries.add(STORMYX_GRANDSWORD);
        entries.add(STORMYX_BACKHAND_BLADE);
        entries.add(STORMYX_LANCE);
        entries.add(STORMYX_KHOPESH);
        entries.add(STORMYX_DAGGER);
        entries.add(STORMYX_PERNACH);
        entries.add(STORMYX_QUARTERSTAFF);
        entries.add(STORMYX_GREAT_SPEAR);
        entries.add(STORMYX_DEER_HORNS);
        entries.add(TIDESINGER_GREAT_KATANA);
        entries.add(TIDESINGER_GRANDSWORD);
        entries.add(TIDESINGER_BACKHAND_BLADE);
        entries.add(TIDESINGER_LANCE);
        entries.add(TIDESINGER_KHOPESH);
        entries.add(TIDESINGER_DAGGER);
        entries.add(TIDESINGER_PERNACH);
        entries.add(TIDESINGER_QUARTERSTAFF);
        entries.add(TIDESINGER_GREAT_SPEAR);
        entries.add(TIDESINGER_DEER_HORNS);
    }

    public static void registerCompatItems() {
        addToGroup(MythicMetalsCompatProxy.mythicMetalCompatItems);
    }
}
