package net.rosemarythyme.simplymore.registry.compat;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.normal.GrandSwordItem;
import net.rosemarythyme.simplymore.item.normal.LanceItem;
import net.rosemarythyme.simplymore.item.normal.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;

public class StickNStoneCompatRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;

    public static final Item WOODEN_GREAT_KATANA = ModItemsRegistry.registerItem(
            "wooden_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_GREAT_KATANA = ModItemsRegistry.registerItem(
            "stone_great_katana",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_GRANDSWORD = ModItemsRegistry.registerItem(
            "wooden_grandsword",
            new GrandSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_GRANDSWORD = ModItemsRegistry.registerItem(
            "stone_grandsword",
            new GrandSwordItem(
                    ToolMaterials.STONE,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_BACKHAND_BLADE = ModItemsRegistry.registerItem(
            "wooden_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_BACKHAND_BLADE = ModItemsRegistry.registerItem(
            "stone_backhand_blade",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_LANCE = ModItemsRegistry.registerItem(
            "wooden_lance",
            new LanceItem(
                    ToolMaterials.WOOD,
                    attributes.getLanceDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_LANCE = ModItemsRegistry.registerItem(
            "stone_lance",
            new LanceItem(
                    ToolMaterials.STONE,
                    attributes.getLanceDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_KHOPESH = ModItemsRegistry.registerItem(
            "wooden_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_KHOPESH = ModItemsRegistry.registerItem(
            "stone_khopesh",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_DAGGER = ModItemsRegistry.registerItem(
            "wooden_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_DAGGER = ModItemsRegistry.registerItem(
            "stone_dagger",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_PERNACH = ModItemsRegistry.registerItem(
            "wooden_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getPernachDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_PERNACH = ModItemsRegistry.registerItem(
            "stone_pernach",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getPernachDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_QUARTERSTAFF = ModItemsRegistry.registerItem(
            "wooden_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_QUARTERSTAFF = ModItemsRegistry.registerItem(
            "stone_quarterstaff",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_GREAT_SPEAR = ModItemsRegistry.registerItem(
            "wooden_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_GREAT_SPEAR = ModItemsRegistry.registerItem(
            "stone_great_spear",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final Item WOODEN_DEER_HORNS = ModItemsRegistry.registerItem(
            "wooden_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final Item STONE_DEER_HORNS = ModItemsRegistry.registerItem(
            "stone_deer_horns",
            new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static void addToGroup(ItemGroup.Entries entries) {
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
