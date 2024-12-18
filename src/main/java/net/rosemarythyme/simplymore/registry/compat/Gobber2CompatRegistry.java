package net.rosemarythyme.simplymore.registry.compat;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.compat.CompatEndGobberSwordItem;
import net.rosemarythyme.simplymore.item.compat.CompatNetherGobberSwordItem;
import net.rosemarythyme.simplymore.item.compat.CompatSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreToolMaterial;

import static net.rosemarythyme.simplymore.registry.ModItemsRegistry.registerItem;

public class Gobber2CompatRegistry {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static WeaponAttributesConfig attributes = config.weaponAttributes;

    public static final Item GOBBER_GREAT_KATANA = registerItem(
            "gobber_great_katana",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_GRANDSWORD = registerItem(
            "gobber_grandsword",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_BACKHAND_BLADE = registerItem(
            "gobber_backhand_blade",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_LANCE = registerItem(
            "gobber_lance",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getLanceDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_KHOPESH = registerItem(
            "gobber_khopesh",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_DAGGER = registerItem(
            "gobber_dagger",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_PERNACH = registerItem(
            "gobber_pernach",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getPernachDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_QUARTERSTAFF = registerItem(
            "gobber_quarterstaff",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_GREAT_SPEAR = registerItem(
            "gobber_great_spear",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_DEER_HORNS = registerItem(
            "gobber_deer_horns",
            new CompatSwordItem(
                    SimplyMoreToolMaterial.GOBBER,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getGobberWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot"
            )
    );

    public static final Item GOBBER_NETHER_GREAT_KATANA = registerItem(
            "gobber_nether_great_katana",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_GRANDSWORD = registerItem(
            "gobber_nether_grandsword",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings(),
                    true,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_BACKHAND_BLADE = registerItem(
            "gobber_nether_backhand_blade",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_LANCE = registerItem(
            "gobber_nether_lance",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getLanceDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings(),
                    false,
                    true,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_KHOPESH = registerItem(
            "gobber_nether_khopesh",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_DAGGER = registerItem(
            "gobber_nether_dagger",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_PERNACH = registerItem(
            "gobber_nether_pernach",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getPernachDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_QUARTERSTAFF = registerItem(
            "gobber_nether_quarterstaff",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_GREAT_SPEAR = registerItem(
            "gobber_nether_great_spear",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_NETHER_DEER_HORNS = registerItem(
            "gobber_nether_deer_horns",
            new CompatNetherGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_NETHER,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getGobberNetherWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_nether"
            )
    );

    public static final Item GOBBER_END_GREAT_KATANA = registerItem(
            "gobber_end_great_katana",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getGreatKatanaDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getGreatKatanaSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_GRANDSWORD = registerItem(
            "gobber_end_grandsword",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getGrandswordDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getGrandswordSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    true,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_BACKHAND_BLADE = registerItem(
            "gobber_end_backhand_blade",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getBackhandBladeDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getBackhandBladeSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_LANCE = registerItem(
            "gobber_end_lance",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getLanceDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getLanceSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    true,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_KHOPESH = registerItem(
            "gobber_end_khopesh",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getKhopeshDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getKhopeshSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_DAGGER = registerItem(
            "gobber_end_dagger",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getDaggerDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getDaggerSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_PERNACH = registerItem(
            "gobber_end_pernach",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getPernachDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getPernachSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_QUARTERSTAFF = registerItem(
            "gobber_end_quarterstaff",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getQuarterstaffDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getQuarterstaffSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_GREAT_SPEAR = registerItem(
            "gobber_end_great_spear",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getGreatSpearDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getGreatSpearSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static final Item GOBBER_END_DEER_HORNS = registerItem(
            "gobber_end_deer_horns",
            new CompatEndGobberSwordItem(
                    SimplyMoreToolMaterial.GOBBER_END,
                    attributes.getDeerHornsDamageModifier() + 3 + attributes.getGobberEndWeaponDamageModifier(),
                    (float)attributes.getDeerHornsSwingSpeed(),
                    new Item.Settings()
                            .fireproof(),
                    false,
                    false,
                    "gobber2:gobber2_ingot_end"
            )
    );

    public static void addToGroup(ItemGroup.Entries entries) {
        entries.add(GOBBER_GREAT_KATANA);
        entries.add(GOBBER_GRANDSWORD);
        entries.add(GOBBER_BACKHAND_BLADE);
        entries.add(GOBBER_LANCE);
        entries.add(GOBBER_KHOPESH);
        entries.add(GOBBER_DAGGER);
        entries.add(GOBBER_PERNACH);
        entries.add(GOBBER_QUARTERSTAFF);
        entries.add(GOBBER_GREAT_SPEAR);
        entries.add(GOBBER_DEER_HORNS);
        entries.add(GOBBER_NETHER_GREAT_KATANA);
        entries.add(GOBBER_NETHER_GRANDSWORD);
        entries.add(GOBBER_NETHER_BACKHAND_BLADE);
        entries.add(GOBBER_NETHER_LANCE);
        entries.add(GOBBER_NETHER_KHOPESH);
        entries.add(GOBBER_NETHER_DAGGER);
        entries.add(GOBBER_NETHER_PERNACH);
        entries.add(GOBBER_NETHER_QUARTERSTAFF);
        entries.add(GOBBER_NETHER_GREAT_SPEAR);
        entries.add(GOBBER_NETHER_DEER_HORNS);
        entries.add(GOBBER_END_GREAT_KATANA);
        entries.add(GOBBER_END_GRANDSWORD);
        entries.add(GOBBER_END_BACKHAND_BLADE);
        entries.add(GOBBER_END_LANCE);
        entries.add(GOBBER_END_KHOPESH);
        entries.add(GOBBER_END_DAGGER);
        entries.add(GOBBER_END_PERNACH);
        entries.add(GOBBER_END_QUARTERSTAFF);
        entries.add(GOBBER_END_GREAT_SPEAR);
        entries.add(GOBBER_END_DEER_HORNS);
    }

    public static void registerCompatItems() {
    }
}
