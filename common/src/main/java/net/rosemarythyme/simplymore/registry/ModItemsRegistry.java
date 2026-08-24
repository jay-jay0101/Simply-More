package net.rosemarythyme.simplymore.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import me.shedaniel.autoconfig.AutoConfig;
//import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.MimicryAttributesConfig;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.RemovedItem;
import net.rosemarythyme.simplymore.item.RuneCarverItem;
import net.rosemarythyme.simplymore.item.normal.GrandSwordItem;
import net.rosemarythyme.simplymore.item.normal.LanceItem;
import net.rosemarythyme.simplymore.item.normal.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.item.runics.RunicGrandSwordItem;
import net.rosemarythyme.simplymore.item.runics.RunicLanceItem;
import net.rosemarythyme.simplymore.item.uniques.*;
import net.rosemarythyme.simplymore.item.uniques.idols.*;
import net.rosemarythyme.simplymore.item.uniques.joke.JesterPenetrateItem;
import net.rosemarythyme.simplymore.item.uniques.joke.ThePanItem;
import net.rosemarythyme.simplymore.item.uniques.mimicry.*;
import net.rosemarythyme.simplymore.registry.compat.Gobber2CompatRegistry;
import net.rosemarythyme.simplymore.registry.compat.MythicMetalsCompatProxy;
import net.rosemarythyme.simplymore.registry.compat.StickNStoneCompatRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreToolMaterial;
import net.sweenus.simplyswords.item.RunicSwordItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class ModItemsRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;
    static UniqueEffectConfig effect = config.uniqueEffects;
    static MimicryAttributesConfig mimicryAttributes = config.mimicry;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> IRON_GREAT_KATANA = ITEMS.register(
            "iron_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_GREAT_KATANA = ITEMS.register(
            "gold_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_GREAT_KATANA = ITEMS.register(
            "diamond_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_GREAT_KATANA = ITEMS.register(
            "netherite_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_GREAT_KATANA = ITEMS.register(
            "runic_great_katana",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final RegistrySupplier<Item> IRON_GRANDSWORD = ITEMS.register(
            "iron_grandsword",
            () -> new GrandSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_GRANDSWORD = ITEMS.register(
            "gold_grandsword",
            () -> new GrandSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_GRANDSWORD = ITEMS.register(
            "diamond_grandsword",
            () -> new GrandSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_GRANDSWORD = ITEMS.register(
            "netherite_grandsword",
            () -> new GrandSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_GRANDSWORD = ITEMS.register(
            "runic_grandsword",
            () -> new RunicGrandSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    
    public static final RegistrySupplier<Item> IRON_BACKHAND_BLADE = ITEMS.register(
            "iron_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_BACKHAND_BLADE = ITEMS.register("gold_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_BACKHAND_BLADE = ITEMS.register(
            "diamond_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_BACKHAND_BLADE = ITEMS.register(
            "netherite_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_BACKHAND_BLADE = ITEMS.register(
            "runic_backhand_blade",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final RegistrySupplier<Item> IRON_LANCE = ITEMS.register(
            "iron_lance",
            () -> new LanceItem(
                    ToolMaterials.IRON,
                    attributes.getLanceDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_LANCE = ITEMS.register(
            "gold_lance",
            () -> new LanceItem(
                    ToolMaterials.GOLD,
                    attributes.getLanceDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"));
    public static final RegistrySupplier<Item> DIAMOND_LANCE = ITEMS.register(
            "diamond_lance",
            () -> new LanceItem(
                    ToolMaterials.DIAMOND,
                    attributes.getLanceDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_LANCE = ITEMS.register(
            "netherite_lance",
            () -> new LanceItem(
                    ToolMaterials.NETHERITE,
                    attributes.getLanceDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_LANCE = ITEMS.register(
            "runic_lance",
            () -> new RunicLanceItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getLanceDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final RegistrySupplier<Item> IRON_KHOPESH = ITEMS.register(
            "iron_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final RegistrySupplier<Item> GOLD_KHOPESH = ITEMS.register(
            "gold_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final RegistrySupplier<Item> DIAMOND_KHOPESH = ITEMS.register(
            "diamond_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final RegistrySupplier<Item> NETHERITE_KHOPESH = ITEMS.register(
            "netherite_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNIC_KHOPESH = ITEMS.register(
            "runic_khopesh",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );

    public static final RegistrySupplier<Item> IRON_DAGGER = ITEMS.register(
            "iron_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final RegistrySupplier<Item> GOLD_DAGGER = ITEMS.register(
            "gold_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final RegistrySupplier<Item> DIAMOND_DAGGER = ITEMS.register(
            "diamond_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final RegistrySupplier<Item> NETHERITE_DAGGER = ITEMS.register(
            "netherite_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNIC_DAGGER = ITEMS.register(
            "runic_dagger",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );

    public static final RegistrySupplier<Item> IRON_PERNACH = ITEMS.register(
            "iron_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getPernachDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final RegistrySupplier<Item> GOLD_PERNACH = ITEMS.register(
            "gold_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getPernachDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final RegistrySupplier<Item> DIAMOND_PERNACH = ITEMS.register(
            "diamond_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getPernachDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final RegistrySupplier<Item> NETHERITE_PERNACH = ITEMS.register(
            "netherite_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getPernachDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNIC_PERNACH = ITEMS.register(
            "runic_pernach",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getPernachDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
            )
    );

    public static final RegistrySupplier<Item> IRON_QUARTERSTAFF = ITEMS.register(
            "iron_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final RegistrySupplier<Item> GOLD_QUARTERSTAFF = ITEMS.register(
            "gold_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final RegistrySupplier<Item> DIAMOND_QUARTERSTAFF = ITEMS.register(
            "diamond_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final RegistrySupplier<Item> NETHERITE_QUARTERSTAFF = ITEMS.register(
            "netherite_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final RegistrySupplier<Item> RUNIC_QUARTERSTAFF = ITEMS.register(
            "runic_quarterstaff",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );


    public static final RegistrySupplier<Item> IRON_GREAT_SPEAR = ITEMS.register(
            "iron_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_GREAT_SPEAR = ITEMS.register(
            "gold_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_GREAT_SPEAR = ITEMS.register(
            "diamond_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_GREAT_SPEAR = ITEMS.register(
            "netherite_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_GREAT_SPEAR = ITEMS.register(
            "runic_great_spear",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );


    public static final RegistrySupplier<Item> IRON_DEER_HORNS = ITEMS.register(
            "iron_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_DEER_HORNS = ITEMS.register(
            "gold_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_DEER_HORNS = ITEMS.register(
            "diamond_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_DEER_HORNS = ITEMS.register(
            "netherite_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_DEER_HORNS = ITEMS.register(
            "runic_deer_horns",
            () -> new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );




    public static final RegistrySupplier<Item> GREAT_SLITHER = ITEMS.register(
            "great_slither",
            () -> new GreatSlitherItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getGreatSlitherDamage() - 6,
                    (float)attributes.getGreatSlitherSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final RegistrySupplier<Item> MOLTEN_FLARE = ITEMS.register(
            "molten_flare",
            () -> new MoltenFlareItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getMoltenFlareDamage() - 6,
                    (float)attributes.getMoltenFlareSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final RegistrySupplier<Item> GRANDFROST = ITEMS.register(
            "grandfrost",
            () -> new GrandfrostItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getGrandfrostDamage() - 6,
                    (float)attributes.getGrandfrostSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final RegistrySupplier<Item> GLIMMERSTEP = ITEMS.register(
            "glimmerstep",
            () -> new GlimmerstepItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getGlimmerstepDamage() - 6,
                    (float)attributes.getGlimmerstepSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );
    public static final RegistrySupplier<Item> THEBLOODHARVESTER = ITEMS.register(
            "the_blood_harvester",
            () -> new TheBloodHarvesterItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTheBloodHarvesterDamage() - 6,
                    (float)attributes.getTheBloodHarvesterSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );
    public static final RegistrySupplier<Item> JESTER_PENETRATE = ITEMS.register(
            "jester_penetrate",
            () -> new JesterPenetrateItem(SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    attributes.getJesterPenetrateDamage() - 6,
                    (float)attributes.getJesterPenetrateSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MYRMEDGE = ITEMS.register(
            "myrmedge",
            () -> new MyrmedgeItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getMyrmedgeDamage() - 6,
                    (float)attributes.getMyrmedgeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> SCARAB_ROLLER = ITEMS.register(
            "scarab_roller",
            () -> new RemovedItem(new Item.Settings().maxCount(1),
                    MYRMEDGE)
    );

    public static final RegistrySupplier<Item> BLACK_PEARL = ITEMS.register(
            "black_pearl",
            () -> new BlackPearlItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBlackPearlDamage() - 6,
                    (float)attributes.getBlackPearlSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> THE_PAN = ITEMS.register(
            "the_pan",
            () -> new ThePanItem(SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    attributes.getThePanDamage() - 6,
                    (float)attributes.getThePanSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> THEVESSELBREACH = ITEMS.register(
            "the_vessel_breach",
            () -> new TheVesselBreachItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTheVesselBreachDamage() - 6,
                    (float)attributes.getTheVesselBreachSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> BLADEOFTHEGROTESQUE = ITEMS.register(
            "blade_of_the_grotesque",
            () -> new BladeOfTheGrotesqueItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBladeOfTheGrotesqueDamage() - 6,
                    (float)attributes.getBladeOfTheGrotesqueSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> VIPERSCALL = ITEMS.register(
            "vipers_call",
            () -> new VipersCallItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getVipersCallDamage() - 6,
                    (float)attributes.getVipersCallSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> TIMEKEEPER = ITEMS.register(
            "timekeeper",
            () -> new TimekeeperItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTimekeeperDamage() - 6,
                    (float)attributes.getTimekeeperSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MATTERBANE = ITEMS.register(
            "matterbane",
            () -> new MatterbaneItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getMatterbaneDamage() - 6,
                    (float)attributes.getMatterbaneSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> SMOULDERING_RUIN = ITEMS.register(
            "smouldering_ruin",
            () -> new SmoulderingRuinItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getSmoulderingRuinDamage() - 6,
                    (float)attributes.getSmoulderingRuinSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> STASIS = ITEMS.register(
            "stasis",
            () -> new StasisItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getStasisDamage() - 6,
                    (float)attributes.getStasisSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> TIDEBREAKER = ITEMS.register(
            "tidebreaker",
            () -> new TidebreakerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTidebreakerDamage() - 6,
                    (float)attributes.getTidebreakerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> RUYI_JINGU_BANG = ITEMS.register(
            "ruyi_jingu_bang",
            () -> new RuyiJinguBangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getRuyiJinguBangDamage() - 6,
                    (float)attributes.getRuyiJinguBangSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> RUPTURED_IDOL = ITEMS.register(
            "ruptured_idol",
            () -> new RupturedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getRupturedIdolDamage() - 6,
                    (float)attributes.getRupturedIdolSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> ASCENDED_IDOL = ITEMS.register(
            "ascended_idol",
            () -> new AscendedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getAscendedIdolDamage() - 6,
                    (float)attributes.getAscendedIdolSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> TARNISHED_IDOL = ITEMS.register(
            "tarnished_idol",
            () -> new TarnishedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTarnishedIdolDamage() - 6,
                    (float)attributes.getTarnishedIdolSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> HOLYLIGHT = ITEMS.register(
            "holylight",
            () -> new HolyLightItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getHolylightDamage() - 6,
                    (float)attributes.getHolylightSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> DARKSENT = ITEMS.register(
            "darksent",
            () -> new DarksentItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getDarksentDamage() - 6,
                    (float)attributes.getDarksentSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> BOAS_FANG = ITEMS.register(
            "boas_fang",
            () -> new BoasFangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBoasFangDamage() - 6,
                    (float)attributes.getBoasFangSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> EARTHSHATTER = ITEMS.register(
            "earthshatter",
            () -> new EarthshatterItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getEarthshatterDamage() - 6,
                    (float)attributes.getEarthshatterSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> SOUL_FORESEER = ITEMS.register(
            "soul_foreseer",
            () -> new SoulForeseerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getSoulForeseerDamage() - 6,
                    (float)attributes.getSoulForeseerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> SERPENTINE_VALOUR = ITEMS.register(
            "serpentine_valour",
            () -> new SerpentineValourItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getSerpentineValourDamage() - 6,
                    (float)attributes.getSerpentineValourSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> LUSTROUS_MOXIE = ITEMS.register(
            "lustrous_moxie",
            () -> new LustrousMoxieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getLustrousMoxieDamage() - 6,
                    (float)attributes.getLustrousMoxieSwingspeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> BRASSTURN = ITEMS.register(
            "brassturn",
            () -> new BrassturnItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBrassturnDamage() - 6,
                    (float)attributes.getBrassturnMaxSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> CINDERGORGE = ITEMS.register(
            "cindergorge",
            () -> new CindergorgeItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getCindergorgeDamage() - 6,
                    (float)attributes.getCindergorgeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> DEATHS_EYRIE = ITEMS.register(
            "deaths_eyrie",
            () -> new DeathsEyrieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getDeathsEyrieDamage() - 6,
                    (float)attributes.getDeathsEyrieSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> PERFORISCUS = ITEMS.register(
            "perforiscus",
            () -> new PerforiscusItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getPerforiscusDamage() - 6,
                    (float)attributes.getPerforiscusSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> REVVENGINE = ITEMS.register(
            "revvengine",
            () -> new RevvengineItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getRevvengineDamage() - 6,
                    (float)attributes.getRevvengineSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> EXEDRILL = ITEMS.register(
            "exedrill",
            () -> new ExedrillItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getExedrillDamage() - 6,
                    (float)attributes.getExedrillSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> CULTEREX = ITEMS.register(
            "culterex",
            () -> new CulterexItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getCulterexDamage() - 6,
                    (float)attributes.getCulterexSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> RUNEFUSED_CARVER = ITEMS.register(
            "runefused_carver",
            () -> new RuneCarverItem(
                    new Item.Settings()
                            .maxCount(1)
                            .fireproof()
                            .rarity(Rarity.EPIC),
                    "runic"
            )
    );
    public static final RegistrySupplier<Item> NETHERFUSED_CARVER = ITEMS.register(
            "netherfused_carver",
            () -> new RuneCarverItem(
                    new Item.Settings()
                            .maxCount(1)
                            .fireproof()
                            .rarity(Rarity.EPIC),
                    "nether"
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_LONGSWORD = ITEMS.register(
            "mimicry_longsword",
            () -> new LongswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getLongswordDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getLongswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_TWINBLADE = ITEMS.register(
            "mimicry_twinblade",
            () -> new TwinbladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getTwinbladeDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getTwinbladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_RAPIER = ITEMS.register(
            "mimicry_rapier",
            () -> new RapierItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getRapierDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getRapierSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_KATANA = ITEMS.register(
            "mimicry_katana",
            () -> new KatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getKatanaDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SPEAR = ITEMS.register(
            "mimicry_spear",
            () -> new SpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getSpearDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getSpearSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SAI = ITEMS.register(
            "mimicry_sai",
            () -> new SaiItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getSaiDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getSaiSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GLAIVE = ITEMS.register(
            "mimicry_glaive",
            () -> new GlaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGlaiveDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGlaiveSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_WARGLAIVE = ITEMS.register(
            "mimicry_warglaive",
            () -> new WarglaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getWarglaiveDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getWarglaiveSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CUTLASS = ITEMS.register(
            "mimicry_cutlass",
            () -> new CutlassItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getCutlassDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getCutlassSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CLAYMORE = ITEMS.register(
            "mimicry_claymore",
            () -> new ClaymoreItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getClaymoreDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getClaymoreSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREATHAMMER = ITEMS.register(
            "mimicry_greathammer",
            () -> new GreathammerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreathammerDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreathammerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREATAXE = ITEMS.register(
            "mimicry_greataxe",
            () -> new GreataxeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreataxeDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreataxeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CHAKRAM = ITEMS.register(
            "mimicry_chakram",
            () -> new ChakramItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getChakramDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getChakramSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SCYTHE = ITEMS.register(
            "mimicry_scythe",
            () -> new ScytheItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getScytheDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getScytheSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_HALBERD = ITEMS.register(
            "mimicry_halberd",
            () -> new HalberdItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getHalberdDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getHalberdSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREAT_KATANA = ITEMS.register(
            "mimicry_great_katana",
            () -> new GreatKatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreatKatanaDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GRANDSWORD = ITEMS.register(
            "mimicry_grandsword",
            () -> new GrandswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGrandswordDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_BACKHAND_BLADE = ITEMS.register(
            "mimicry_backhand_blade",
            () -> new BackhandBladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getBackhandBladeDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_LANCE = ITEMS.register(
            "mimicry_lance",
            () -> new net.rosemarythyme.simplymore.item.uniques.mimicry.LanceItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getLanceDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_KHOPESH = ITEMS.register(
            "mimicry_khopesh",
            () -> new KhopeshItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getKhopeshDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_DAGGER = ITEMS.register(
            "mimicry_dagger",
            () -> new DaggerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getDaggerDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_PERNACH = ITEMS.register(
            "mimicry_pernach",
            () -> new PernachItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getPernachDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_QUARTERSTAFF = ITEMS.register(
            "mimicry_quarterstaff",
            () -> new QuarterstaffItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getQuarterstaffDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREAT_SPEAR = ITEMS.register(
            "mimicry_great_spear",
            () -> new GreatSpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreatSpearDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_DEER_HORNS = ITEMS.register(
            "mimicry_deer_horns",
            () -> new DeerHornsItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getDeerHornsDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY = ITEMS.register(
            "mimicry",
            () -> new RemovedItem(
                    new Item.Settings(),
                    MIMICRY_LONGSWORD)
    );

    public static void registerModItems() {
        SimplyMore.LOGGER.info("Registering Items for " + SimplyMore.ID);
        if (Platform.isModLoaded("sticknstone")) {
            SimplyMore.LOGGER.info("Registering Stick N Stone Compat for " + SimplyMore.ID);
            StickNStoneCompatRegistry.registerCompatItems();
        }

        if (Platform.isModLoaded("gobber2")) {
            SimplyMore.LOGGER.info("Registering Gobber2 Compat for " + SimplyMore.ID);
            Gobber2CompatRegistry.registerCompatItems();
        }

        ITEMS.register();
    }

    public static void registerItemGroup() {
        CreativeTabRegistry.append(ITEM_GROUP, itemsInTab().toArray(new Item[0]));
        TABS.register();
    }


    public static List<Item> itemsInTab() {
        List<Item> entries = new ArrayList<>();

        entries.add(RUNEFUSED_CARVER.get());
        entries.add(NETHERFUSED_CARVER.get());

        if (Platform.isModLoaded("sticknstone")) {
            StickNStoneCompatRegistry.addToGroup(entries);
        }

        entries.add(IRON_GREAT_KATANA.get());
        entries.add(IRON_GRANDSWORD.get());
        entries.add(IRON_BACKHAND_BLADE.get());
        entries.add(IRON_LANCE.get());
        entries.add(IRON_KHOPESH.get());
        entries.add(IRON_DAGGER.get());
        entries.add(IRON_PERNACH.get());
        entries.add(IRON_QUARTERSTAFF.get());
        entries.add(IRON_GREAT_SPEAR.get());
        entries.add(IRON_DEER_HORNS.get());
        entries.add(GOLD_GREAT_KATANA.get());
        entries.add(GOLD_GRANDSWORD.get());
        entries.add(GOLD_BACKHAND_BLADE.get());
        entries.add(GOLD_LANCE.get());
        entries.add(GOLD_KHOPESH.get());
        entries.add(GOLD_DAGGER.get());
        entries.add(GOLD_PERNACH.get());
        entries.add(GOLD_QUARTERSTAFF.get());
        entries.add(GOLD_GREAT_SPEAR.get());
        entries.add(GOLD_DEER_HORNS.get());
        entries.add(DIAMOND_GREAT_KATANA.get());
        entries.add(DIAMOND_GRANDSWORD.get());
        entries.add(DIAMOND_BACKHAND_BLADE.get());
        entries.add(DIAMOND_LANCE.get());
        entries.add(DIAMOND_KHOPESH.get());
        entries.add(DIAMOND_DAGGER.get());
        entries.add(DIAMOND_PERNACH.get());
        entries.add(DIAMOND_QUARTERSTAFF.get());
        entries.add(DIAMOND_GREAT_SPEAR.get());
        entries.add(DIAMOND_DEER_HORNS.get());
        entries.add(NETHERITE_GREAT_KATANA.get());
        entries.add(NETHERITE_GRANDSWORD.get());
        entries.add(NETHERITE_BACKHAND_BLADE.get());
        entries.add(NETHERITE_LANCE.get());
        entries.add(NETHERITE_KHOPESH.get());
        entries.add(NETHERITE_DAGGER.get());
        entries.add(NETHERITE_PERNACH.get());
        entries.add(NETHERITE_QUARTERSTAFF.get());
        entries.add(NETHERITE_GREAT_SPEAR.get());
        entries.add(NETHERITE_DEER_HORNS.get());
        entries.add(RUNIC_GREAT_KATANA.get());
        entries.add(RUNIC_GRANDSWORD.get());
        entries.add(RUNIC_BACKHAND_BLADE.get());
        entries.add(RUNIC_LANCE.get());
        entries.add(RUNIC_KHOPESH.get());
        entries.add(RUNIC_DAGGER.get());
        entries.add(RUNIC_PERNACH.get());
        entries.add(RUNIC_QUARTERSTAFF.get());
        entries.add(RUNIC_GREAT_SPEAR.get());
        entries.add(RUNIC_DEER_HORNS.get());

        if (Platform.isModLoaded("gobber2")) {
            Gobber2CompatRegistry.addToGroup(entries);
        }

        if (Platform.isModLoaded("mythicmetals")) {
            MythicMetalsCompatProxy.addToGroup(entries);
        }

        entries.add(GREAT_SLITHER.get());
        entries.add(MOLTEN_FLARE.get());
        entries.add(GRANDFROST.get());
        entries.add(MIMICRY_LONGSWORD.get());
        entries.add(GLIMMERSTEP.get());
        entries.add(THEBLOODHARVESTER.get());
        entries.add(MYRMEDGE.get());
        entries.add(BLACK_PEARL.get());
        entries.add(THEVESSELBREACH.get());
        entries.add(BLADEOFTHEGROTESQUE.get());
        entries.add(VIPERSCALL.get());
        entries.add(TIMEKEEPER.get());
        entries.add(MATTERBANE.get());
        entries.add(SMOULDERING_RUIN.get());
        entries.add(STASIS.get());
        entries.add(TIDEBREAKER.get());
        entries.add(RUYI_JINGU_BANG.get());
        entries.add(RUPTURED_IDOL.get());
        entries.add(ASCENDED_IDOL.get());
        entries.add(TARNISHED_IDOL.get());
        entries.add(HOLYLIGHT.get());
        entries.add(DARKSENT.get());
        entries.add(BOAS_FANG.get());
        entries.add(EARTHSHATTER.get());
        entries.add(SOUL_FORESEER.get());
        entries.add(SERPENTINE_VALOUR.get());
        entries.add(LUSTROUS_MOXIE.get());
        entries.add(BRASSTURN.get());
        entries.add(CINDERGORGE.get());
        entries.add(DEATHS_EYRIE.get());
        entries.add(PERFORISCUS.get());
        entries.add(REVVENGINE.get());
        entries.add(EXEDRILL.get());
        entries.add(CULTEREX.get());
        entries.add(JESTER_PENETRATE.get());
        entries.add(THE_PAN.get());

        return entries;
    }

    public static final DeferredRegister<ItemGroup> TABS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ITEM_GROUP);

    public static final RegistrySupplier<ItemGroup> ITEM_GROUP =
            TABS.register(
                    "simplymore",
                    () ->
                            CreativeTabRegistry.create(
                                    Text.translatable("item_group.simplymore"),
                                    () -> new ItemStack(RUNEFUSED_CARVER.get())
                            )
            );

    public static final Map<String, RegistrySupplier<Item>> MIMICRY_ITEMS = Map.ofEntries(
            entry("longsword", MIMICRY_LONGSWORD),
            entry("twinblade", MIMICRY_TWINBLADE),
            entry("rapier", MIMICRY_RAPIER),
            entry("katana", MIMICRY_KATANA),
            entry("spear", MIMICRY_SPEAR),
            entry("sai", MIMICRY_SAI),
            entry("glaive", MIMICRY_GLAIVE),
            entry("warglaive", MIMICRY_WARGLAIVE),
            entry("cutlass", MIMICRY_CUTLASS),
            entry("claymore", MIMICRY_CLAYMORE),
            entry("greathammer", MIMICRY_GREATHAMMER),
            entry("greataxe", MIMICRY_GREATAXE),
            entry("chakram", MIMICRY_CHAKRAM),
            entry("scythe", MIMICRY_SCYTHE),
            entry("halberd", MIMICRY_HALBERD),
            entry("great_katana", MIMICRY_GREAT_KATANA),
            entry("grandsword", MIMICRY_GRANDSWORD),
            entry("backhand_blade", MIMICRY_BACKHAND_BLADE),
            entry("lance", MIMICRY_LANCE),
            entry("khopesh", MIMICRY_KHOPESH),
            entry("dagger", MIMICRY_DAGGER),
            entry("pernach", MIMICRY_PERNACH),
            entry("quarterstaff", MIMICRY_QUARTERSTAFF),
            entry("great_spear", MIMICRY_GREAT_SPEAR),
            entry("deer_horns", MIMICRY_DEER_HORNS)
    );

    public static final List<RegistrySupplier<Item>> MIMICRY_AMPLIFIERS = List.of(
            MIMICRY_LONGSWORD,
            MIMICRY_TWINBLADE,
            MIMICRY_RAPIER,
            MIMICRY_KATANA,
            MIMICRY_SAI,
            MIMICRY_SPEAR,
            MIMICRY_GLAIVE,
            MIMICRY_WARGLAIVE,
            MIMICRY_CUTLASS,
            MIMICRY_CLAYMORE,
            MIMICRY_GREATHAMMER,
            MIMICRY_GREATAXE,
            MIMICRY_CHAKRAM,
            MIMICRY_SCYTHE,
            MIMICRY_HALBERD,
            MIMICRY_GREAT_KATANA,
            MIMICRY_GRANDSWORD,
            MIMICRY_BACKHAND_BLADE,
            MIMICRY_LANCE,
            MIMICRY_KHOPESH,
            MIMICRY_DAGGER,
            MIMICRY_PERNACH,
            MIMICRY_QUARTERSTAFF,
            MIMICRY_GREAT_SPEAR,
            MIMICRY_DEER_HORNS
    );
}
