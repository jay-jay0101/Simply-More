package net.rosemarythyme.simplymore.registry.compat;

import dev.architectury.registry.registries.RegistrySupplier;
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

import java.util.List;

public class StickNStoneCompatRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;

    public static final RegistrySupplier<Item> WOODEN_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "wooden_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_GREAT_KATANA = ModItemsRegistry.ITEMS.register(
            "stone_great_katana",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "wooden_grandsword",
            () -> new GrandSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_GRANDSWORD = ModItemsRegistry.ITEMS.register(
            "stone_grandsword",
            () -> new GrandSwordItem(
                    ToolMaterials.STONE,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "wooden_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_BACKHAND_BLADE = ModItemsRegistry.ITEMS.register(
            "stone_backhand_blade",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_LANCE = ModItemsRegistry.ITEMS.register(
            "wooden_lance",
            () -> new LanceItem(
                    ToolMaterials.WOOD,
                    attributes.getLanceDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_LANCE = ModItemsRegistry.ITEMS.register(
            "stone_lance",
            () -> new LanceItem(
                    ToolMaterials.STONE,
                    attributes.getLanceDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_KHOPESH = ModItemsRegistry.ITEMS.register(
            "wooden_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_KHOPESH = ModItemsRegistry.ITEMS.register(
            "stone_khopesh",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_DAGGER = ModItemsRegistry.ITEMS.register(
            "wooden_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_DAGGER = ModItemsRegistry.ITEMS.register(
            "stone_dagger",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_PERNACH = ModItemsRegistry.ITEMS.register(
            "wooden_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getPernachDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_PERNACH = ModItemsRegistry.ITEMS.register(
            "stone_pernach",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getPernachDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "wooden_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_QUARTERSTAFF = ModItemsRegistry.ITEMS.register(
            "stone_quarterstaff",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "wooden_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_GREAT_SPEAR = ModItemsRegistry.ITEMS.register(
            "stone_great_spear",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static final RegistrySupplier<Item> WOODEN_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "wooden_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.WOOD,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getWoodenWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "wooden"
            )
    );

    public static final RegistrySupplier<Item> STONE_DEER_HORNS = ModItemsRegistry.ITEMS.register(
            "stone_deer_horns",
            () -> new SimplyMoreSwordItem(
                    ToolMaterials.STONE,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getStoneWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    "tag",
                    "stone"
            )
    );

    public static void addToGroup(List<Item> entries) {
        entries.add(WOODEN_GREAT_KATANA.get());
        entries.add(WOODEN_GRANDSWORD.get());
        entries.add(WOODEN_BACKHAND_BLADE.get());
        entries.add(WOODEN_LANCE.get());
        entries.add(WOODEN_KHOPESH.get());
        entries.add(WOODEN_DAGGER.get());
        entries.add(WOODEN_PERNACH.get());
        entries.add(WOODEN_QUARTERSTAFF.get());
        entries.add(WOODEN_GREAT_SPEAR.get());
        entries.add(WOODEN_DEER_HORNS.get());
        entries.add(STONE_GREAT_KATANA.get());
        entries.add(STONE_GRANDSWORD.get());
        entries.add(STONE_BACKHAND_BLADE.get());
        entries.add(STONE_LANCE.get());
        entries.add(STONE_KHOPESH.get());
        entries.add(STONE_DAGGER.get());
        entries.add(STONE_PERNACH.get());
        entries.add(STONE_QUARTERSTAFF.get());
        entries.add(STONE_GREAT_SPEAR.get());
        entries.add(STONE_DEER_HORNS.get());
    }

    public static void registerCompatItems() {
    }
}
