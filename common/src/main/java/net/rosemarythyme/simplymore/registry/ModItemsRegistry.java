package net.rosemarythyme.simplymore.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.item.RemovedItem;
import net.rosemarythyme.simplymore.item.RuneCarverItem;
import net.rosemarythyme.simplymore.item.SimplyMoreRunicSwordItem;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.rosemarythyme.simplymore.item.uniques.*;
import net.rosemarythyme.simplymore.item.uniques.idols.*;
import net.rosemarythyme.simplymore.item.uniques.joke.JesterPenetrateItem;
import net.rosemarythyme.simplymore.item.uniques.joke.ThePanItem;
import net.rosemarythyme.simplymore.item.uniques.mimicry.*;
import net.rosemarythyme.simplymore.registry.compat.StickNStoneCompatRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreToolMaterial;
import net.sweenus.simplyswords.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class ModItemsRegistry {
    static WeaponAttributesConfig attributes = ConfigWrapper.attributes;
    static UniqueEffectConfig effect = ConfigWrapper.unique;

    static final int iron_modifier = (int) Config.weaponAttribute.materialDamageModifier.iron_damageModifier;
    static final int gold_modifier = (int) Config.weaponAttribute.materialDamageModifier.gold_damageModifier;
    static final int diamond_modifier = (int) Config.weaponAttribute.materialDamageModifier.diamond_damageModifier;
    static final int netherite_modifier = (int) Config.weaponAttribute.materialDamageModifier.netherite_damageModifier;
    static final int runic_modifier = (int) Config.weaponAttribute.materialDamageModifier.runic_damageModifier;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ITEM);

    // Great Katanas
    public static final RegistrySupplier<Item> IRON_GREAT_KATANA = ITEMS.register(
            "iron_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.greatkatana_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_GREAT_KATANA = ITEMS.register(
            "gold_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.greatkatana_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_GREAT_KATANA = ITEMS.register(
            "diamond_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.greatkatana_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_GREAT_KATANA = ITEMS.register(
            "netherite_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.greatkatana_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_GREAT_KATANA = ITEMS.register(
            "runic_great_katana",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.greatkatana_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof()
            )
    );

    // Grandswords
    public static final RegistrySupplier<Item> IRON_GRANDSWORD = ITEMS.register(
            "iron_grandsword",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.grandsword_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    Weapon.SwordTypes.GRANDSWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_GRANDSWORD = ITEMS.register(
            "gold_grandsword",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.grandsword_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    Weapon.SwordTypes.GRANDSWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_GRANDSWORD = ITEMS.register(
            "diamond_grandsword",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.grandsword_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    Weapon.SwordTypes.GRANDSWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_GRANDSWORD = ITEMS.register(
            "netherite_grandsword",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.grandsword_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    Weapon.SwordTypes.GRANDSWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_GRANDSWORD = ITEMS.register(
            "runic_grandsword",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.grandsword_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    Weapon.SwordTypes.GRANDSWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );

    // Backhand Blades
    public static final RegistrySupplier<Item> IRON_BACKHAND_BLADE = ITEMS.register(
            "iron_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.backhandblade_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_BACKHAND_BLADE = ITEMS.register(
            "gold_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.backhandblade_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_BACKHAND_BLADE = ITEMS.register(
            "diamond_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.backhandblade_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_BACKHAND_BLADE = ITEMS.register(
            "netherite_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.backhandblade_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_BACKHAND_BLADE = ITEMS.register(
            "runic_backhand_blade",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.backhandblade_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof()
            )
    );

    // Lances
    public static final RegistrySupplier<Item> IRON_LANCE = ITEMS.register(
            "iron_lance",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.lance_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    Weapon.SwordTypes.LANCE,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_LANCE = ITEMS.register(
            "gold_lance",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.lance_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    Weapon.SwordTypes.LANCE,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_LANCE = ITEMS.register(
            "diamond_lance",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.lance_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    Weapon.SwordTypes.LANCE,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_LANCE = ITEMS.register(
            "netherite_lance",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.lance_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    Weapon.SwordTypes.LANCE,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_LANCE = ITEMS.register(
            "runic_lance",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.lance_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    Weapon.SwordTypes.LANCE,
                    new Item.Settings().fireproof()
            )
    );

    // Khopesh
    public static final RegistrySupplier<Item> IRON_KHOPESH = ITEMS.register(
            "iron_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.khopesh_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_KHOPESH = ITEMS.register(
            "gold_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.khopesh_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_KHOPESH = ITEMS.register(
            "diamond_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.khopesh_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_KHOPESH = ITEMS.register(
            "netherite_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.khopesh_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_KHOPESH = ITEMS.register(
            "runic_khopesh",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.khopesh_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof()
            )
    );

    // Daggers
    public static final RegistrySupplier<Item> IRON_DAGGER = ITEMS.register(
            "iron_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.dagger_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_DAGGER = ITEMS.register(
            "gold_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.dagger_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_DAGGER = ITEMS.register(
            "diamond_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.dagger_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_DAGGER = ITEMS.register(
            "netherite_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.dagger_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_DAGGER = ITEMS.register(
            "runic_dagger",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.dagger_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof()
            )
    );

    // Pernach
    public static final RegistrySupplier<Item> IRON_PERNACH = ITEMS.register(
            "iron_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.pernach_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_PERNACH = ITEMS.register(
            "gold_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.pernach_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_PERNACH = ITEMS.register(
            "diamond_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.pernach_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_PERNACH = ITEMS.register(
            "netherite_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.pernach_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_PERNACH = ITEMS.register(
            "runic_pernach",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.pernach_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings()
            )
    );

    // Quarterstaff
    public static final RegistrySupplier<Item> IRON_QUARTERSTAFF = ITEMS.register(
            "iron_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.quarterstaff_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_QUARTERSTAFF = ITEMS.register(
            "gold_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.quarterstaff_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_QUARTERSTAFF = ITEMS.register(
            "diamond_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.quarterstaff_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_QUARTERSTAFF = ITEMS.register(
            "netherite_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.quarterstaff_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_QUARTERSTAFF = ITEMS.register(
            "runic_quarterstaff",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.quarterstaff_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof()
            )
    );

    // Great Spear
    public static final RegistrySupplier<Item> IRON_GREAT_SPEAR = ITEMS.register(
            "iron_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.greatspear_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_GREAT_SPEAR = ITEMS.register(
            "gold_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.greatspear_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_GREAT_SPEAR = ITEMS.register(
            "diamond_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.greatspear_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_GREAT_SPEAR = ITEMS.register(
            "netherite_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.greatspear_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_GREAT_SPEAR = ITEMS.register(
            "runic_great_spear",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.greatspear_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof()
            )
    );

    // Deer Horns
    public static final RegistrySupplier<Item> IRON_DEER_HORNS = ITEMS.register(
            "iron_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.IRON,
                    attributes.weaponTypesDamage.deerhorns_damage_modifier + iron_modifier,
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final RegistrySupplier<Item> GOLD_DEER_HORNS = ITEMS.register(
            "gold_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.GOLD,
                    attributes.weaponTypesDamage.deerhorns_damage_modifier + gold_modifier,
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final RegistrySupplier<Item> DIAMOND_DEER_HORNS = ITEMS.register(
            "diamond_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.DIAMOND,
                    attributes.weaponTypesDamage.deerhorns_damage_modifier + diamond_modifier,
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final RegistrySupplier<Item> NETHERITE_DEER_HORNS = ITEMS.register(
            "netherite_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.NETHERITE,
                    attributes.weaponTypesDamage.deerhorns_damage_modifier + netherite_modifier,
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final RegistrySupplier<Item> RUNIC_DEER_HORNS = ITEMS.register(
            "runic_deer_horns",
            () -> new SimplyMoreRunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    attributes.weaponTypesDamage.deerhorns_damage_modifier + runic_modifier,
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings().fireproof()
            )
    );

    // Uniques

    public static final RegistrySupplier<Item> GREAT_SLITHER = ITEMS.register(
            "great_slither",
            () -> new GreatSlitherItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.greatslither_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.greatslither_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MOLTEN_FLARE = ITEMS.register(
            "molten_flare",
            () -> new MoltenFlareItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.moltenflare_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.moltenflare_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> GRANDFROST = ITEMS.register(
            "grandfrost",
            () -> new GrandfrostItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.grandfrost_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.grandfrost_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> GLIMMERSTEP = ITEMS.register(
            "glimmerstep",
            () -> new GlimmerstepItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.glimmerstep_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.glimmerstep_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> THE_BLOOD_HARVESTER = ITEMS.register(
            "the_blood_harvester",
            () -> new TheBloodHarvesterItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.thebloodharvester_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.thebloodharvester_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> JESTER_PENETRATE = ITEMS.register(
            "jester_penetrate",
            () -> new JesterPenetrateItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    attributes.uniqueWeaponsDamage.jesterpenetrate_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.jesterpenetrate_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.COMMON)
            )
    );

    public static final RegistrySupplier<Item> MYRMEDGE = ITEMS.register(
            "myrmedge",
            () -> new MyrmedgeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.myrmedge_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.myrmedge_attack_speed,
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
            () -> new BlackPearlItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.blackpearl_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.blackpearl_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> THE_PAN = ITEMS.register(
            "the_pan",
            () -> new ThePanItem(SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    attributes.uniqueWeaponsDamage.thepan_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.thepan_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.COMMON)
            )
    );

    public static final RegistrySupplier<Item> THE_VESSEL_BREACH = ITEMS.register(
            "the_vessel_breach",
            () -> new TheVesselBreachItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.thevesselbreach_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.thevesselbreach_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> BLADE_OF_THE_GROTESQUE = ITEMS.register(
            "blade_of_the_grotesque",
            () -> new BladeOfTheGrotesqueItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.bladeofthegrotesque_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.bladeofthegrotesque_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> VIPERS_CALL = ITEMS.register(
            "vipers_call",
            () -> new VipersCallItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.viperscall_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.viperscall_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> TIMEKEEPER = ITEMS.register(
            "timekeeper",
            () -> new TimekeeperItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.timekeeper_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.timekeeper_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MATTERBANE = ITEMS.register(
            "matterbane",
            () -> new MatterbaneItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.matterbane_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.matterbane_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> SMOULDERING_RUIN = ITEMS.register(
            "smouldering_ruin",
            () -> new SmoulderingRuinItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.smoulderingruin_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.smoulderingruin_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> STASIS = ITEMS.register(
            "stasis",
            () -> new StasisItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.stasis_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.stasis_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> TIDEBREAKER = ITEMS.register(
            "tidebreaker",
            () -> new TidebreakerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.tidebreaker_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.tidebreaker_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> RUYI_JINGU_BANG = ITEMS.register(
            "ruyi_jingu_bang",
            () -> new RuyiJinguBangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.ruyijingubang_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.ruyijingubang_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> RUPTURED_IDOL = ITEMS.register(
            "ruptured_idol",
            () -> new RupturedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.rupturedidol_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.rupturedidol_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> ASCENDED_IDOL = ITEMS.register(
            "ascended_idol",
            () -> new AscendedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.ascendedidol_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.ascendedidol_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> TARNISHED_IDOL = ITEMS.register(
            "tarnished_idol",
            () -> new TarnishedIdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.tarnishedidol_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.tarnishedidol_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> HOLYLIGHT = ITEMS.register(
            "holylight",
            () -> new HolylightItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.holylight_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.holylight_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> DARKSENT = ITEMS.register(
            "darksent",
            () -> new DarksentItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.darksent_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.darksent_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> BOAS_FANG = ITEMS.register(
            "boas_fang",
            () -> new BoasFangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.boasfang_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.boasfang_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> EARTHSHATTER = ITEMS.register(
            "earthshatter",
            () -> new EarthshatterItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.earthshatter_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.earthshatter_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> SOUL_FORESEER = ITEMS.register(
            "soul_foreseer",
            () -> new SoulForeseerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.soulforeseer_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.soulforeseer_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> SERPENTINE_VALOUR = ITEMS.register(
            "serpentine_valour",
            () -> new SerpentineValourItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.serpentinevalour_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.serpentinevalour_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> LUSTROUS_MOXIE = ITEMS.register(
            "lustrous_moxie",
            () -> new LustrousMoxieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.lustrousmoxie_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.lustrousmoxie_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> BRASSTURN = ITEMS.register(
            "brassturn",
            () -> new BrassturnItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.brassturn_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.brassturn_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> CINDERGORGE = ITEMS.register(
            "cindergorge",
            () -> new CindergorgeItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.cindergorge_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.cindergorge_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> DEATHS_EYRIE = ITEMS.register(
            "deaths_eyrie",
            () -> new DeathsEyrieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.deathseyrie_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.deathseyrie_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> PERFORISCUS = ITEMS.register(
            "perforiscus",
            () -> new PerforiscusItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.perforiscus_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.perforiscus_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> REVVENGINE = ITEMS.register(
            "revvengine",
            () -> new RevvengineItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.revvengine_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.revvengine_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> EXEDRILL = ITEMS.register(
            "exedrill",
            () -> new ExedrillItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.exedrill_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.exedrill_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> CULTEREX = ITEMS.register(
            "culterex",
            () -> new CulterexItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    attributes.uniqueWeaponsDamage.culterex_damage_modifier,
                    attributes.uniqueWeaponsSwingSpeed.culterex_attack_speed,
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
                    RuneCarverItem.Types.RUNEFUSED
            )
    );
    public static final RegistrySupplier<Item> NETHERFUSED_CARVER = ITEMS.register(
            "netherfused_carver",
            () -> new RuneCarverItem(
                    new Item.Settings()
                            .maxCount(1)
                            .fireproof()
                            .rarity(Rarity.EPIC),
                    RuneCarverItem.Types.NETHERFUSED
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_LONGSWORD = ITEMS.register(
            "mimicry_longsword",
            () -> new LongswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.longsword_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.longsword_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_TWINBLADE = ITEMS.register(
            "mimicry_twinblade",
            () -> new TwinbladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.twinblade_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.twinblade_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_RAPIER = ITEMS.register(
            "mimicry_rapier",
            () -> new RapierItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.rapier_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.rapier_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_KATANA = ITEMS.register(
            "mimicry_katana",
            () -> new KatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.katana_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.katana_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SPEAR = ITEMS.register(
            "mimicry_spear",
            () -> new SpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.spear_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.spear_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SAI = ITEMS.register(
            "mimicry_sai",
            () -> new SaiItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.sai_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.sai_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GLAIVE = ITEMS.register(
            "mimicry_glaive",
            () -> new GlaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.glaive_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.glaive_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_WARGLAIVE = ITEMS.register(
            "mimicry_warglaive",
            () -> new WarglaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.warglaive_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.warglaive_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CUTLASS = ITEMS.register(
            "mimicry_cutlass",
            () -> new CutlassItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.cutlass_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.cutlass_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CLAYMORE = ITEMS.register(
            "mimicry_claymore",
            () -> new ClaymoreItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.claymore_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.claymore_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREATHAMMER = ITEMS.register(
            "mimicry_greathammer",
            () -> new GreathammerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.greathammer_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.greathammer_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREATAXE = ITEMS.register(
            "mimicry_greataxe",
            () -> new GreataxeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.greataxe_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.greataxe_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CHAKRAM = ITEMS.register(
            "mimicry_chakram",
            () -> new ChakramItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.chakram_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.chakram_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SCYTHE = ITEMS.register(
            "mimicry_scythe",
            () -> new ScytheItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.scythe_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.scythe_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_HALBERD = ITEMS.register(
            "mimicry_halberd",
            () -> new HalberdItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.halberd_damageModifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.halberd_attackSpeed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREAT_KATANA = ITEMS.register(
            "mimicry_great_katana",
            () -> new GreatKatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.greatkatana_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GRANDSWORD = ITEMS.register(
            "mimicry_grandsword",
            () -> new GrandswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.grandsword_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_BACKHAND_BLADE = ITEMS.register(
            "mimicry_backhand_blade",
            () -> new BackhandBladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.backhandblade_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_LANCE = ITEMS.register(
            "mimicry_lance",
            () -> new net.rosemarythyme.simplymore.item.uniques.mimicry.LanceItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.lance_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_KHOPESH = ITEMS.register(
            "mimicry_khopesh",
            () -> new KhopeshItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.khopesh_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_DAGGER = ITEMS.register(
            "mimicry_dagger",
            () -> new DaggerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.dagger_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_PERNACH = ITEMS.register(
            "mimicry_pernach",
            () -> new PernachItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.pernach_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_QUARTERSTAFF = ITEMS.register(
            "mimicry_quarterstaff",
            () -> new QuarterstaffItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.quarterstaff_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREAT_SPEAR = ITEMS.register(
            "mimicry_great_spear",
            () -> new GreatSpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.greatspear_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_DEER_HORNS = ITEMS.register(
            "mimicry_deer_horns",
            () -> new DeerHornsItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int)(attributes.weaponTypesDamage.deerhorns_damage_modifier + attributes.uniqueWeaponsDamage.mimicry_damage_modifier),
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
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
//
//        if (Platform.isModLoaded("gobber2")) {
//            SimplyMore.LOGGER.info("Registering Gobber2 Compat for " + SimplyMore.ID);
//            Gobber2CompatRegistry.registerCompatItems();
//        }

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

//        if (Platform.isModLoaded("gobber2")) {
//            Gobber2CompatRegistry.addToGroup(entries);
//        }
//
//        if (Platform.isModLoaded("mythicmetals")) {
//            MythicMetalsCompatProxy.addToGroup(entries);
//        }

        entries.add(GREAT_SLITHER.get());
        entries.add(MOLTEN_FLARE.get());
        entries.add(GRANDFROST.get());
        entries.add(MIMICRY_LONGSWORD.get());
        entries.add(GLIMMERSTEP.get());
        entries.add(THE_BLOOD_HARVESTER.get());
        entries.add(MYRMEDGE.get());
        entries.add(BLACK_PEARL.get());
        entries.add(THE_VESSEL_BREACH.get());
        entries.add(BLADE_OF_THE_GROTESQUE.get());
        entries.add(VIPERS_CALL.get());
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
