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
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
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
import net.rosemarythyme.simplymore.util.SimplyMoreToolMaterial;
import net.sweenus.simplyswords.item.RunicSwordItem;

public class ModItemsRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;
    public static final Item IRON_GREAT_KATANA = registerItem(
            "iron_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGreatKatanaDamageModifier() + 3,
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GREAT_KATANA = registerItem(
            "gold_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGreatKatanaDamageModifier() + 3,
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GREAT_KATANA = registerItem(
            "diamond_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGreatKatanaDamageModifier() + 3,
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GREAT_KATANA = registerItem(
            "netherite_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGreatKatanaDamageModifier() + 3,
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
                    attributes.getGreatKatanaDamageModifier() + 3,
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_GRANDSWORD = registerItem(
            "iron_grandsword",
            new GrandSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGrandswordDamageModifier() + 3,
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GRANDSWORD = registerItem(
            "gold_grandsword",
            new GrandSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGrandswordDamageModifier() + 3,
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GRANDSWORD = registerItem(
            "diamond_grandsword",
            new GrandSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGrandswordDamageModifier() + 3,
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GRANDSWORD = registerItem(
            "netherite_grandsword",
            new GrandSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGrandswordDamageModifier() + 3,
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
                    attributes.getGrandswordDamageModifier() + 3,
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
                    attributes.getBackhandBladeDamageModifier() + 3,
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_BACKHAND_BLADE = registerItem("gold_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getBackhandBladeDamageModifier() + 3,
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_BACKHAND_BLADE = registerItem(
            "diamond_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getBackhandBladeDamageModifier() + 3,
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_BACKHAND_BLADE = registerItem(
            "netherite_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getBackhandBladeDamageModifier() + 3,
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
                    attributes.getBackhandBladeDamageModifier() + 3,
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_LANCE = registerItem(
            "iron_lance",
            new LanceItem(
                    ToolMaterials.IRON,
                    attributes.getLanceDamageModifier() + 3,
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_LANCE = registerItem(
            "gold_lance",
            new LanceItem(
                    ToolMaterials.GOLD,
                    attributes.getLanceDamageModifier() + 3,
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"));
    public static final Item DIAMOND_LANCE = registerItem(
            "diamond_lance",
            new LanceItem(
                    ToolMaterials.DIAMOND,
                    attributes.getLanceDamageModifier() + 3,
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_LANCE = registerItem(
            "netherite_lance",
            new LanceItem(
                    ToolMaterials.NETHERITE,
                    attributes.getLanceDamageModifier() + 3,
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
                    attributes.getLanceDamageModifier() + 3,
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_KHOPESH = registerItem(
            "iron_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getKhopeshDamageModifier() + 3,
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_KHOPESH = registerItem(
            "gold_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getKhopeshDamageModifier() + 3,
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_KHOPESH = registerItem(
            "diamond_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getKhopeshDamageModifier() + 3,
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_KHOPESH = registerItem(
            "netherite_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getKhopeshDamageModifier() + 3,
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_KHOPESH = registerItem(
            "runic_khopesh",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getKhopeshDamageModifier() + 3,
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );

    public static final Item IRON_DAGGER = registerItem(
            "iron_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getDaggerDamageModifier() + 3,
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_DAGGER = registerItem(
            "gold_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getDaggerDamageModifier() + 3,
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_DAGGER = registerItem(
            "diamond_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getDaggerDamageModifier() + 3,
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_DAGGER = registerItem(
            "netherite_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getDaggerDamageModifier() + 3,
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_DAGGER = registerItem(
            "runic_dagger",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getDaggerDamageModifier() + 3,
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );

    public static final Item IRON_PERNACH = registerItem(
            "iron_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getPernachDamageModifier() + 3,
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_PERNACH = registerItem(
            "gold_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getPernachDamageModifier() + 3,
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_PERNACH = registerItem(
            "diamond_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getPernachDamageModifier() + 3,
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_PERNACH = registerItem(
            "netherite_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getPernachDamageModifier() + 3,
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_PERNACH = registerItem(
            "runic_pernach",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getPernachDamageModifier() + 3,
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
            )
    );

    public static final Item IRON_QUARTERSTAFF = registerItem(
            "iron_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getQuarterstaffDamageModifier() + 3,
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );

    public static final Item GOLD_QUARTERSTAFF = registerItem(
            "gold_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getQuarterstaffDamageModifier() + 3,
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );

    public static final Item DIAMOND_QUARTERSTAFF = registerItem(
            "diamond_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getQuarterstaffDamageModifier() + 3,
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );

    public static final Item NETHERITE_QUARTERSTAFF = registerItem(
            "netherite_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getQuarterstaffDamageModifier() + 3,
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    public static final Item RUNIC_QUARTERSTAFF = registerItem(
            "runic_quarterstaff",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getQuarterstaffDamageModifier() + 3,
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );


    public static final Item IRON_GREAT_SPEAR = registerItem(
            "iron_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getGreatSpearDamageModifier() + 3,
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GREAT_SPEAR = registerItem(
            "gold_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getGreatSpearDamageModifier() + 3,
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GREAT_SPEAR = registerItem(
            "diamond_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getGreatSpearDamageModifier() + 3,
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GREAT_SPEAR = registerItem(
            "netherite_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getGreatSpearDamageModifier() + 3,
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_GREAT_SPEAR = registerItem(
            "runic_great_spear",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getGreatSpearDamageModifier() + 3,
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings().fireproof()
            )
    );


    public static final Item IRON_DEER_HORNS = registerItem(
            "iron_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.getDeerHornsDamageModifier() + 3,
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_DEER_HORNS = registerItem(
            "gold_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.getDeerHornsDamageModifier() + 3,
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_DEER_HORNS = registerItem(
            "diamond_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.getDeerHornsDamageModifier() + 3,
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_DEER_HORNS = registerItem(
            "netherite_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.getDeerHornsDamageModifier() + 3,
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_DEER_HORNS = registerItem(
            "runic_deer_horns",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.getDeerHornsDamageModifier() + 3,
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
    public static final Item MIMICRY = registerItem(
            "mimicry",
            new MimicryItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getMimicryPurityDamage() - 6,
                    (float)attributes.getMimicryPuritySwingSpeed(),
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

    public static final Item SCARAB_ROLLER = registerItem(
            "scarab_roller",
            new ScarabRollerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.getScarabRollerDamage() - 6,
                    (float)attributes.getScarabRollerSwingSpeed(),
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
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

    public static Item registerItem (String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SimplyMore.ID, name),item);
    }
    public static void registerModItems() {
        if (FabricLoader.getInstance().isModLoaded("simplyswords")) {
            SimplyMore.LOGGER.info("Registering Items for " + SimplyMore.ID);
            Registry.register(Registries.ITEM_GROUP, Identifier.of(SimplyMore.ID, "items"), ITEM_GROUP);
        }
    }

    private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItemsRegistry.RUNEFUSED_CARVER))
            .displayName(Text.translatable("item_group.simplymore"))
            .entries((context, entries) -> {
                entries.add(RUNEFUSED_CARVER);
                entries.add(NETHERFUSED_CARVER);
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
                entries.add(GREAT_SLITHER);
                entries.add(MOLTEN_FLARE);
                entries.add(GRANDFROST);
                entries.add(MIMICRY);
                entries.add(GLIMMERSTEP);
                entries.add(THEBLOODHARVESTER);
                entries.add(SCARAB_ROLLER);
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
                entries.add(JESTER_PENETRATE);
                entries.add(THE_PAN);
            })
            .build();
}
