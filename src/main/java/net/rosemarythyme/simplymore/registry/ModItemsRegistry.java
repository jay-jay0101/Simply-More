package net.rosemarythyme.simplymore.registry;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
import net.rosemarythyme.simplymore.registry.compat.MythicMetalsCompat;
import net.rosemarythyme.simplymore.registry.compat.StickNStoneCompatRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreToolMaterial;
import net.sweenus.simplyswords.item.RunicSwordItem;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class ModItemsRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;
    static UniqueEffectConfig effect = config.uniqueEffects;
    static MimicryAttributesConfig mimicryAttributes = config.mimicry;

    public static final Item IRON_GREAT_KATANA = registerItem(
            "iron_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GREAT_KATANA = registerItem(
            "gold_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GREAT_KATANA = registerItem(
            "diamond_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GREAT_KATANA = registerItem(
            "netherite_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_GREAT_KATANA = registerItem(
            "runic_great_katana",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_GRANDSWORD = registerItem(
            "iron_grandsword",
            new GrandSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GRANDSWORD = registerItem(
            "gold_grandsword",
            new GrandSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GRANDSWORD = registerItem(
            "diamond_grandsword",
            new GrandSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GRANDSWORD = registerItem(
            "netherite_grandsword",
            new GrandSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_GRANDSWORD = registerItem(
            "runic_grandsword",
            new RunicGrandSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    
    public static final Item IRON_BACKHAND_BLADE = registerItem(
            "iron_backhand_blade", 
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_BACKHAND_BLADE = registerItem("gold_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_BACKHAND_BLADE = registerItem(
            "diamond_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_BACKHAND_BLADE = registerItem(
            "netherite_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_BACKHAND_BLADE = registerItem(
            "runic_backhand_blade", 
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_LANCE = registerItem(
            "iron_lance",
            new LanceItem(
                    ToolMaterials.IRON,
                    attributes.getLanceDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_LANCE = registerItem(
            "gold_lance",
            new LanceItem(
                    ToolMaterials.GOLD,
                    attributes.getLanceDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"));
    public static final Item DIAMOND_LANCE = registerItem(
            "diamond_lance",
            new LanceItem(
                    ToolMaterials.DIAMOND,
                    attributes.getLanceDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_LANCE = registerItem(
            "netherite_lance",
            new LanceItem(
                    ToolMaterials.NETHERITE,
                    attributes.getLanceDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_LANCE = registerItem(
            "runic_lance",
            new RunicLanceItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getLanceDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_KHOPESH = registerItem(
            "iron_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_KHOPESH = registerItem(
            "gold_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_KHOPESH = registerItem(
            "diamond_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_KHOPESH = registerItem(
            "netherite_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_KHOPESH = registerItem(
            "runic_khopesh",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );

    public static final Item IRON_DAGGER = registerItem(
            "iron_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_DAGGER = registerItem(
            "gold_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_DAGGER = registerItem(
            "diamond_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_DAGGER = registerItem(
            "netherite_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_DAGGER = registerItem(
            "runic_dagger",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );

    public static final Item IRON_PERNACH = registerItem(
            "iron_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getPernachDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_PERNACH = registerItem(
            "gold_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getPernachDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_PERNACH = registerItem(
            "diamond_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getPernachDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_PERNACH = registerItem(
            "netherite_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getPernachDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_PERNACH = registerItem(
            "runic_pernach",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getPernachDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
            )
    );

    public static final Item IRON_QUARTERSTAFF = registerItem(
            "iron_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_QUARTERSTAFF = registerItem(
            "gold_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_QUARTERSTAFF = registerItem(
            "diamond_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_QUARTERSTAFF = registerItem(
            "netherite_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_QUARTERSTAFF = registerItem(
            "runic_quarterstaff",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );


    public static final Item IRON_GREAT_SPEAR = registerItem(
            "iron_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GREAT_SPEAR = registerItem(
            "gold_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GREAT_SPEAR = registerItem(
            "diamond_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GREAT_SPEAR = registerItem(
            "netherite_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_GREAT_SPEAR = registerItem(
            "runic_great_spear",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );


    public static final Item IRON_DEER_HORNS = registerItem(
            "iron_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getIronWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_DEER_HORNS = registerItem(
            "gold_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getGoldWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_DEER_HORNS = registerItem(
            "diamond_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getDiamondWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_DEER_HORNS = registerItem(
            "netherite_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getNetheriteWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_DEER_HORNS = registerItem(
            "runic_deer_horns",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getRunicWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );




    public static final Item GREAT_SLITHER = registerItem(
            "great_slither",
            new GreatSlitherItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getGreatSlitherDamage() - 6,
                    (float)attributes.getGreatSlitherSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final Item MOLTEN_FLARE = registerItem(
            "molten_flare",
            new MoltenFlareItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getMoltenFlareDamage() - 6,
                    (float)attributes.getMoltenFlareSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final Item GRANDFROST = registerItem(
            "grandfrost",
            new GrandfrostItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getGrandfrostDamage() - 6,
                    (float)attributes.getGrandfrostSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final Item GLIMMERSTEP = registerItem(
            "glimmerstep",
            new GlimmerstepItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getGlimmerstepDamage() - 6,
                    (float)attributes.getGlimmerstepSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );
    public static final Item THEBLOODHARVESTER = registerItem(
            "the_blood_harvester",
            new TheBloodHarvesterItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTheBloodHarvesterDamage() - 6,
                    (float)attributes.getTheBloodHarvesterSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );
    public static final Item JESTER_PENETRATE = registerItem(
            "jester_penetrate",
            new JesterPenetrateItem(SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    attributes.getJesterPenetrateDamage() - 6,
                    (float)attributes.getJesterPenetrateSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MYRMEDGE = registerItem(
            "myrmedge",
            new MyrmedgeItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getMyrmedgeDamage() - 6,
                    (float)attributes.getMyrmedgeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item SCARAB_ROLLER = registerItem(
            "scarab_roller",
            new RemovedItem(new Item.Settings().maxCount(1), MYRMEDGE)
    );

    public static final Item BLACK_PEARL = registerItem(
            "black_pearl",
            new BlackPearlItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBlackPearlDamage() - 6,
                    (float)attributes.getBlackPearlSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item THE_PAN = registerItem(
            "the_pan",
            new ThePanItem(SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    attributes.getThePanDamage() - 6,
                    (float)attributes.getThePanSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item THEVESSELBREACH = registerItem(
            "the_vessel_breach",
            new TheVesselBreachItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTheVesselBreachDamage() - 6,
                    (float)attributes.getTheVesselBreachSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item BLADEOFTHEGROTESQUE = registerItem(
            "blade_of_the_grotesque",
            new BladeOfTheGrotesqueItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBladeOfTheGrotesqueDamage() - 6,
                    (float)attributes.getBladeOfTheGrotesqueSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item VIPERSCALL = registerItem(
            "vipers_call",
            new VipersCallItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getVipersCallDamage() - 6,
                    (float)attributes.getVipersCallSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item TIMEKEEPER = registerItem(
            "timekeeper",
            new TimekeeperItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTimekeeperDamage() - 6,
                    (float)attributes.getTimekeeperSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MATTERBANE = registerItem(
            "matterbane",
            new MatterbaneItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getMatterbaneDamage() - 6,
                    (float)attributes.getMatterbaneSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item SMOULDERING_RUIN = registerItem(
            "smouldering_ruin",
            new SmoulderingRuinItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getSmoulderingRuinDamage() - 6,
                    (float)attributes.getSmoulderingRuinSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item STASIS = registerItem(
            "stasis",
            new StasisItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getStasisDamage() - 6,
                    (float)attributes.getStasisSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item TIDEBREAKER = registerItem(
            "tidebreaker",
            new TidebreakerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTidebreakerDamage() - 6,
                    (float)attributes.getTidebreakerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item RUYI_JINGU_BANG = registerItem(
            "ruyi_jingu_bang",
            new RuyiJinguBangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getRuyiJinguBangDamage() - 6,
                    (float)attributes.getRuyiJinguBangSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item RUPTURED_IDOL = registerItem(
            "ruptured_idol",
            new RupturedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getRupturedIdolDamage() - 6,
                    (float)attributes.getRupturedIdolSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item ASCENDED_IDOL = registerItem(
            "ascended_idol",
            new AscendedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getAscendedIdolDamage() - 6,
                    (float)attributes.getAscendedIdolSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item TARNISHED_IDOL = registerItem(
            "tarnished_idol",
            new TarnishedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getTarnishedIdolDamage() - 6,
                    (float)attributes.getTarnishedIdolSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item HOLYLIGHT = registerItem(
            "holylight",
            new HolyLightItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getHolylightDamage() - 6,
                    (float)attributes.getHolylightSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item DARKSENT = registerItem(
            "darksent",
            new DarksentItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getDarksentDamage() - 6,
                    (float)attributes.getDarksentSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item BOAS_FANG = registerItem(
            "boas_fang",
            new BoasFangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBoasFangDamage() - 6,
                    (float)attributes.getBoasFangSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item EARTHSHATTER = registerItem(
            "earthshatter",
            new EarthshatterItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getEarthshatterDamage() - 6,
                    (float)attributes.getEarthshatterSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item SOUL_FORESEER = registerItem(
            "soul_foreseer",
            new SoulForeseerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getSoulForeseerDamage() - 6,
                    (float)attributes.getSoulForeseerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item SERPENTINE_VALOUR = registerItem(
            "serpentine_valour",
            new SerpentineValourItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getSerpentineValourDamage() - 6,
                    (float)attributes.getSerpentineValourSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item LUSTROUS_MOXIE = registerItem(
            "lustrous_moxie",
            new LustrousMoxieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getLustrousMoxieDamage() - 6,
                    (float)attributes.getLustrousMoxieSwingspeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item BRASSTURN = registerItem(
            "brassturn",
            new BrassturnItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getBrassturnDamage() - 6,
                    (float)attributes.getBrassturnMaxSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item CINDERGORGE = registerItem(
            "cindergorge",
            new CindergorgeItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getCindergorgeDamage() - 6,
                    (float)attributes.getCindergorgeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item DEATHS_EYRIE = registerItem(
            "deaths_eyrie",
            new DeathsEyrieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getDeathsEyrieDamage() - 6,
                    (float)attributes.getDeathsEyrieSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item PERFORISCUS = registerItem(
            "perforiscus",
            new PerforiscusItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getPerforiscusDamage() - 6,
                    (float)attributes.getPerforiscusSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item REVVENGINE = registerItem(
            "revvengine",
            new RevvengineItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getRevvengineDamage() - 6,
                    (float)attributes.getRevvengineSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item EXEDRILL = registerItem(
            "exedrill",
            new ExedrillItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getExedrillDamage() - 6,
                    (float)attributes.getExedrillSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item CULTEREX = registerItem(
            "culterex",
            new CulterexItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getCulterexDamage() - 6,
                    (float)attributes.getCulterexSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item RUNEFUSED_CARVER = registerItem(
            "runefused_carver",
            new RuneCarverItem(
                    new Item.Settings()
                            .maxCount(1)
                            .fireproof()
                            .rarity(Rarity.EPIC),
                    "runic"
            )
    );
    public static final Item NETHERFUSED_CARVER = registerItem(
            "netherfused_carver",
            new RuneCarverItem(
                    new Item.Settings()
                            .maxCount(1)
                            .fireproof()
                            .rarity(Rarity.EPIC),
                    "nether"
            )
    );

    public static final Item MIMICRY_LONGSWORD = registerItem(
            "mimicry_longsword",
            new LongswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getLongswordDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getLongswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_TWINBLADE = registerItem(
            "mimicry_twinblade",
            new TwinbladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getTwinbladeDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getTwinbladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_RAPIER = registerItem(
            "mimicry_rapier",
            new RapierItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getRapierDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getRapierSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_KATANA = registerItem(
            "mimicry_katana",
            new KatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getKatanaDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_SPEAR = registerItem(
            "mimicry_spear",
            new SpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getSpearDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getSpearSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_SAI = registerItem(
            "mimicry_sai",
            new SaiItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getSaiDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getSaiSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_GLAIVE = registerItem(
            "mimicry_glaive",
            new GlaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGlaiveDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGlaiveSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_WARGLAIVE = registerItem(
            "mimicry_warglaive",
            new WarglaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getWarglaiveDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getWarglaiveSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_CUTLASS = registerItem(
            "mimicry_cutlass",
            new CutlassItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getCutlassDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getCutlassSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_CLAYMORE = registerItem(
            "mimicry_claymore",
            new ClaymoreItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getClaymoreDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getClaymoreSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_GREATHAMMER = registerItem(
            "mimicry_greathammer",
            new GreathammerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreathammerDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreathammerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_GREATAXE = registerItem(
            "mimicry_greataxe",
            new GreataxeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreataxeDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreataxeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_CHAKRAM = registerItem(
            "mimicry_chakram",
            new ChakramItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getChakramDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getChakramSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_SCYTHE = registerItem(
            "mimicry_scythe",
            new ScytheItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getScytheDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getScytheSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_HALBERD = registerItem(
            "mimicry_halberd",
            new HalberdItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getHalberdDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getHalberdSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_GREAT_KATANA = registerItem(
            "mimicry_great_katana",
            new GreatKatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreatKatanaDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_GRANDSWORD = registerItem(
            "mimicry_grandsword",
            new GrandswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGrandswordDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_BACKHAND_BLADE = registerItem(
            "mimicry_backhand_blade",
            new BackhandBladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getBackhandBladeDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_LANCE = registerItem(
            "mimicry_lance",
            new net.rosemarythyme.simplymore.item.uniques.mimicry.LanceItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getLanceDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_KHOPESH = registerItem(
            "mimicry_khopesh",
            new KhopeshItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getKhopeshDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_DAGGER = registerItem(
            "mimicry_dagger",
            new DaggerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getDaggerDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_PERNACH = registerItem(
            "mimicry_pernach",
            new PernachItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getPernachDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_QUARTERSTAFF = registerItem(
            "mimicry_quarterstaff",
            new QuarterstaffItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getQuarterstaffDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_GREAT_SPEAR = registerItem(
            "mimicry_great_spear",
            new GreatSpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getGreatSpearDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY_DEER_HORNS = registerItem(
            "mimicry_deer_horns",
            new DeerHornsItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    mimicryAttributes.getDeerHornsDamageModifier() + 3 + effect.getMimicryDamageModifierFromRunic(),
                    (float)mimicryAttributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final Item MIMICRY = registerItem(
            "mimicry",
            new RemovedItem(
                    new Item.Settings(),
                    MIMICRY_LONGSWORD
            )
    );

    public static Item registerItem (String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SimplyMore.ID, name),item);
    }
    public static void registerModItems() {
        if (FabricLoader.getInstance().isModLoaded("simplyswords")) {
            SimplyMore.LOGGER.info("Registering Items for " + SimplyMore.ID);

            if (FabricLoader.getInstance().isModLoaded("sticknstone")) {
                SimplyMore.LOGGER.info("Registering Stick N Stone Compat for " + SimplyMore.ID);
                StickNStoneCompatRegistry.registerCompatItems();
            }

            if (FabricLoader.getInstance().isModLoaded("gobber2")) {
                SimplyMore.LOGGER.info("Registering Gobber2 Compat for " + SimplyMore.ID);
                Gobber2CompatRegistry.registerCompatItems();
            }

            if (FabricLoader.getInstance().isModLoaded("mythicmetals")) {
                SimplyMore.LOGGER.info("Registering Mythic Metals Compat for " + SimplyMore.ID);
                MythicMetalsCompat.registerCompatItems();
            }

            Registry.register(Registries.ITEM_GROUP, Identifier.of(SimplyMore.ID, "items"), ITEM_GROUP);

        }
    }

    private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItemsRegistry.RUNEFUSED_CARVER))
            .displayName(Text.translatable("item_group.simplymore"))
            .entries((context, entries) -> {
                entries.add(RUNEFUSED_CARVER);
                entries.add(NETHERFUSED_CARVER);

                if (FabricLoader.getInstance().isModLoaded("sticknstone")) {
                    StickNStoneCompatRegistry.addToGroup(entries);
                }

                entries.add(IRON_GREAT_KATANA);
                entries.add(IRON_GRANDSWORD);
                entries.add(IRON_BACKHAND_BLADE);
                entries.add(IRON_LANCE);
                entries.add(IRON_KHOPESH);
                entries.add(IRON_DAGGER);
                entries.add(IRON_PERNACH);
                entries.add(IRON_QUARTERSTAFF);
                entries.add(IRON_GREAT_SPEAR);
                entries.add(IRON_DEER_HORNS);
                entries.add(GOLD_GREAT_KATANA);
                entries.add(GOLD_GRANDSWORD);
                entries.add(GOLD_BACKHAND_BLADE);
                entries.add(GOLD_LANCE);
                entries.add(GOLD_KHOPESH);
                entries.add(GOLD_DAGGER);
                entries.add(GOLD_PERNACH);
                entries.add(GOLD_QUARTERSTAFF);
                entries.add(GOLD_GREAT_SPEAR);
                entries.add(GOLD_DEER_HORNS);
                entries.add(DIAMOND_GREAT_KATANA);
                entries.add(DIAMOND_GRANDSWORD);
                entries.add(DIAMOND_BACKHAND_BLADE);
                entries.add(DIAMOND_LANCE);
                entries.add(DIAMOND_KHOPESH);
                entries.add(DIAMOND_DAGGER);
                entries.add(DIAMOND_PERNACH);
                entries.add(DIAMOND_QUARTERSTAFF);
                entries.add(DIAMOND_GREAT_SPEAR);
                entries.add(DIAMOND_DEER_HORNS);
                entries.add(NETHERITE_GREAT_KATANA);
                entries.add(NETHERITE_GRANDSWORD);
                entries.add(NETHERITE_BACKHAND_BLADE);
                entries.add(NETHERITE_LANCE);
                entries.add(NETHERITE_KHOPESH);
                entries.add(NETHERITE_DAGGER);
                entries.add(NETHERITE_PERNACH);
                entries.add(NETHERITE_QUARTERSTAFF);
                entries.add(NETHERITE_GREAT_SPEAR);
                entries.add(NETHERITE_DEER_HORNS);
                entries.add(RUNIC_GREAT_KATANA);
                entries.add(RUNIC_GRANDSWORD);
                entries.add(RUNIC_BACKHAND_BLADE);
                entries.add(RUNIC_LANCE);
                entries.add(RUNIC_KHOPESH);
                entries.add(RUNIC_DAGGER);
                entries.add(RUNIC_PERNACH);
                entries.add(RUNIC_QUARTERSTAFF);
                entries.add(RUNIC_GREAT_SPEAR);
                entries.add(RUNIC_DEER_HORNS);

                if (FabricLoader.getInstance().isModLoaded("gobber2")) {
                    Gobber2CompatRegistry.addToGroup(entries);
                }

                if (FabricLoader.getInstance().isModLoaded("mythicmetals")) {
                    MythicMetalsCompat.addToGroup(entries);
                }

                entries.add(GREAT_SLITHER);
                entries.add(MOLTEN_FLARE);
                entries.add(GRANDFROST);
                entries.add(MIMICRY_LONGSWORD);
                entries.add(GLIMMERSTEP);
                entries.add(THEBLOODHARVESTER);
                entries.add(MYRMEDGE);
                entries.add(BLACK_PEARL);
                entries.add(THEVESSELBREACH);
                entries.add(BLADEOFTHEGROTESQUE);
                entries.add(VIPERSCALL);
                entries.add(TIMEKEEPER);
                entries.add(MATTERBANE);
                entries.add(SMOULDERING_RUIN);
                entries.add(STASIS);
                entries.add(TIDEBREAKER);
                entries.add(RUYI_JINGU_BANG);
                entries.add(RUPTURED_IDOL);
                entries.add(ASCENDED_IDOL);
                entries.add(TARNISHED_IDOL);
                entries.add(HOLYLIGHT);
                entries.add(DARKSENT);
                entries.add(BOAS_FANG);
                entries.add(EARTHSHATTER);
                entries.add(SOUL_FORESEER);
                entries.add(SERPENTINE_VALOUR);
                entries.add(LUSTROUS_MOXIE);
                entries.add(BRASSTURN);
                entries.add(CINDERGORGE);
                entries.add(DEATHS_EYRIE);
                entries.add(PERFORISCUS);
                entries.add(REVVENGINE);
                entries.add(EXEDRILL);
                entries.add(CULTEREX);
                entries.add(JESTER_PENETRATE);
                entries.add(THE_PAN);
            })
            .build();

    public static final Map<String, Item> MIMICRY_ITEMS = Map.ofEntries(
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

    public static final List<MimicryItem> MIMICRY_AMPLIFIERS = List.of(
            ((MimicryItem) ModItemsRegistry.MIMICRY_LONGSWORD),
            ((MimicryItem) ModItemsRegistry.MIMICRY_TWINBLADE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_RAPIER),
            ((MimicryItem) ModItemsRegistry.MIMICRY_KATANA),
            ((MimicryItem) ModItemsRegistry.MIMICRY_SAI),
            ((MimicryItem) ModItemsRegistry.MIMICRY_SPEAR),
            ((MimicryItem) ModItemsRegistry.MIMICRY_GLAIVE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_WARGLAIVE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_CUTLASS),
            ((MimicryItem) ModItemsRegistry.MIMICRY_CLAYMORE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_GREATHAMMER),
            ((MimicryItem) ModItemsRegistry.MIMICRY_GREATAXE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_CHAKRAM),
            ((MimicryItem) ModItemsRegistry.MIMICRY_SCYTHE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_HALBERD),
            ((MimicryItem) ModItemsRegistry.MIMICRY_GREAT_KATANA),
            ((MimicryItem) ModItemsRegistry.MIMICRY_GRANDSWORD),
            ((MimicryItem) ModItemsRegistry.MIMICRY_BACKHAND_BLADE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_LANCE),
            ((MimicryItem) ModItemsRegistry.MIMICRY_KHOPESH),
            ((MimicryItem) ModItemsRegistry.MIMICRY_DAGGER),
            ((MimicryItem) ModItemsRegistry.MIMICRY_PERNACH),
            ((MimicryItem) ModItemsRegistry.MIMICRY_QUARTERSTAFF),
            ((MimicryItem) ModItemsRegistry.MIMICRY_GREAT_SPEAR),
            ((MimicryItem) ModItemsRegistry.MIMICRY_DEER_HORNS)
    );
}
