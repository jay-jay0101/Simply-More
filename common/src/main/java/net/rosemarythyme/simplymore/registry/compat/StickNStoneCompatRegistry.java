package net.rosemarythyme.simplymore.registry.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.rosemarythyme.simplymore.registry.ItemRegistry;

import java.util.List;

public class StickNStoneCompatRegistry {
    static WeaponAttributesConfig attributes = ConfigWrapper.attributes;

    static final int wooden_modifier = attributes.typeDamageModifier.wooden_damage_modifier.get().intValue();
    static final int stone_modifier = attributes.typeDamageModifier.stone_damage_modifier.get().intValue();
    public static final RegistrySupplier<Item> WOODEN_GREAT_KATANA = ItemRegistry.ITEMS.register(
            "wooden_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.greatkatana_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_GREAT_KATANA = ItemRegistry.ITEMS.register(
            "stone_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.greatkatana_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.greatkatana_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_GRANDSWORD = ItemRegistry.ITEMS.register(
            "wooden_grandsword",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.grandsword_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    Weapon.SwordTypes.GRANDSWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_GRANDSWORD = ItemRegistry.ITEMS.register(
            "stone_grandsword",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.grandsword_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.grandsword_attack_speed,
                    Weapon.SwordTypes.GRANDSWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_BACKHAND_BLADE = ItemRegistry.ITEMS.register(
            "wooden_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.backhandblade_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_BACKHAND_BLADE = ItemRegistry.ITEMS.register(
            "stone_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.backhandblade_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.backhandblade_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_LANCE = ItemRegistry.ITEMS.register(
            "wooden_lance",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.lance_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    Weapon.SwordTypes.LANCE,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_LANCE = ItemRegistry.ITEMS.register(
            "stone_lance",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.lance_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.lance_attack_speed,
                    Weapon.SwordTypes.LANCE,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_KHOPESH = ItemRegistry.ITEMS.register(
            "wooden_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.khopesh_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_KHOPESH = ItemRegistry.ITEMS.register(
            "stone_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.khopesh_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.khopesh_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_DAGGER = ItemRegistry.ITEMS.register(
            "wooden_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.dagger_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_DAGGER = ItemRegistry.ITEMS.register(
            "stone_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.dagger_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.dagger_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_PERNACH = ItemRegistry.ITEMS.register(
            "wooden_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.pernach_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_PERNACH = ItemRegistry.ITEMS.register(
            "stone_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.pernach_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.pernach_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_QUARTERSTAFF = ItemRegistry.ITEMS.register(
            "wooden_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.quarterstaff_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_QUARTERSTAFF = ItemRegistry.ITEMS.register(
            "stone_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.quarterstaff_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_GREAT_SPEAR = ItemRegistry.ITEMS.register(
            "wooden_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.greatspear_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_GREAT_SPEAR = ItemRegistry.ITEMS.register(
            "stone_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.greatspear_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.greatspear_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_DEER_HORNS = ItemRegistry.ITEMS.register(
            "wooden_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.weaponTypesDamage.deerhorns_damage_modifier + wooden_modifier,
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_DEER_HORNS = ItemRegistry.ITEMS.register(
            "stone_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.weaponTypesDamage.deerhorns_damage_modifier + stone_modifier,
                    attributes.weaponTypesSwingSpeed.deerhorns_attack_speed,
                    Weapon.SwordTypes.SWORD,
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static void addToGroup(List<RegistrySupplier<? extends Item>> entries) {
        entries.add(WOODEN_GREAT_KATANA);
        entries.add(WOODEN_GRANDSWORD);
        entries.add(WOODEN_BACKHAND_BLADE);
        entries.add(WOODEN_LANCE);
        entries.add(WOODEN_KHOPESH);
        entries.add(WOODEN_DAGGER);
        entries.add(WOODEN_PERNACH);
        entries.add(WOODEN_QUARTERSTAFF);
        entries.add(WOODEN_GREAT_SPEAR);
        entries.add(WOODEN_DEER_HORNS);
        entries.add(STONE_GREAT_KATANA);
        entries.add(STONE_GRANDSWORD);
        entries.add(STONE_BACKHAND_BLADE);
        entries.add(STONE_LANCE);
        entries.add(STONE_KHOPESH);
        entries.add(STONE_DAGGER);
        entries.add(STONE_PERNACH);
        entries.add(STONE_QUARTERSTAFF);
        entries.add(STONE_GREAT_SPEAR);
        entries.add(STONE_DEER_HORNS);
    }

    public static void registerCompatItems() {
    }
}
