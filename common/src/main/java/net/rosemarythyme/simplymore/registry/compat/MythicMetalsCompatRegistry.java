package net.rosemarythyme.simplymore.registry.compat;

import com.mythicmetals.component.MythicDataComponents;
import com.mythicmetals.component.PrometheumComponent;
import com.mythicmetals.item.tools.MythicToolMaterials;
import com.mythicmetals.item.tools.MythicTools;
import com.mythicmetals.item.tools.ToolSet;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Rarity;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.item.compat.mythicmetals.LegendaryBanglumSwordItem;
import net.rosemarythyme.simplymore.item.compat.mythicmetals.PalladiumSwordItem;
import net.rosemarythyme.simplymore.item.compat.mythicmetals.TidesingerSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.sweenus.simplyswords.config.Config;

import java.util.ArrayList;
import java.util.List;

public class MythicMetalsCompatRegistry {
    static WeaponAttributesConfig attributes = ConfigWrapper.attributes;

    static float adamantite_modifier = Config.weaponAttribute.materialDamageModifier.adamantite_damageModifier.get();
    static float aquarium_modifier = Config.weaponAttribute.materialDamageModifier.aquarium_damageModifier.get();
    static float banglum_modifier = Config.weaponAttribute.materialDamageModifier.banglum_damageModifier.get();
    static float bronze_modifier = Config.weaponAttribute.materialDamageModifier.bronze_damageModifier.get();
    static float carmot_modifier = Config.weaponAttribute.materialDamageModifier.carmot_damageModifier.get();
    static float celestium_modifier = Config.weaponAttribute.materialDamageModifier.celestium_damageModifier.get();
    static float copper_modifier = Config.weaponAttribute.materialDamageModifier.copper_damageModifier.get();
    static float durasteel_modifier = Config.weaponAttribute.materialDamageModifier.durasteel_damageModifier.get();
    static float kyber_modifier = Config.weaponAttribute.materialDamageModifier.kyber_damageModifier.get();
    static float metallurgium_modifier = Config.weaponAttribute.materialDamageModifier.metallurgium_damageModifier.get();
    static float mythril_modifier = Config.weaponAttribute.materialDamageModifier.mythril_damageModifier.get();
    static float orichalcum_modifier = Config.weaponAttribute.materialDamageModifier.orichalcum_damageModifier.get();
    static float osmium_modifier = Config.weaponAttribute.materialDamageModifier.osmium_damageModifier.get();
    static float palladium_modifier = Config.weaponAttribute.materialDamageModifier.palladium_damageModifier.get();
    static float prometheum_modifier = Config.weaponAttribute.materialDamageModifier.prometheum_damageModifier.get();
    static float quadrillum_modifier = Config.weaponAttribute.materialDamageModifier.quadrillum_damageModifier.get();
    static float runite_modifier = Config.weaponAttribute.materialDamageModifier.runite_damageModifier.get();
    static float star_platinum_modifier = Config.weaponAttribute.materialDamageModifier.starPlatinum_damageModifier.get();
    static float steel_modifier = Config.weaponAttribute.materialDamageModifier.steel_damageModifier.get();
    static float stormyx_modifier = Config.weaponAttribute.materialDamageModifier.stormyx_damageModifier.get();
    static float hallowed_modifier = ConfigWrapper.attributes.typeDamageModifier.hallowed_damage_modifier.get();
    static float legendary_banglum_modifier = ConfigWrapper.attributes.typeDamageModifier.legendary_banglum_damage_modifier.get();
    static float tidesinger_modifier = ConfigWrapper.attributes.typeDamageModifier.tidesinger_damage_modifier.get();

    static List<RegistrySupplier<Item>> itemList = new ArrayList<>();

    private static void registerSet(String name, int damageModifier, Rarity rarity) {
        ToolSet set = MythicTools.TOOL_MAP.get(name);
        if(set == null) return;

        ToolMaterial material = set.getSword().getMaterial();
        Item.Settings settings = name.equals("prometheum") ?
                new Item.Settings().component(MythicDataComponents.PROMETHEUM, PrometheumComponent.DEFAULT) :
                new Item.Settings().rarity(rarity);

        itemList.add(ItemRegistry.ITEMS.register(name + "_great_katana", () -> createSword(material,
                attributes.weaponTypesDamage.greatkatana_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.greatkatana_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_grandsword", () -> createSword(material,
                attributes.weaponTypesDamage.grandsword_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.grandsword_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_backhand_blade", () -> createSword(material,
                attributes.weaponTypesDamage.backhandblade_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.backhandblade_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_lance", () -> createSword(material,
                attributes.weaponTypesDamage.lance_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.lance_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_khopesh", () -> createSword(material,
                attributes.weaponTypesDamage.khopesh_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.khopesh_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_dagger", () -> createSword(material,
                attributes.weaponTypesDamage.dagger_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.dagger_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_pernach", () -> createSword(material,
                attributes.weaponTypesDamage.pernach_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.pernach_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_quarterstaff", () -> createSword(material,
                attributes.weaponTypesDamage.quarterstaff_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.quarterstaff_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_great_spear", () -> createSword(material,
                attributes.weaponTypesDamage.greatspear_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.greatspear_attack_speed, settings)));
        itemList.add(ItemRegistry.ITEMS.register(name + "_deer_horns", () -> createSword(material,
                attributes.weaponTypesDamage.deerhorns_damage_modifier + damageModifier,
                attributes.weaponTypesSwingSpeed.deerhorns_attack_speed, settings)));
    }

    public static SwordItem createSword(ToolMaterial material, int damage, float attackSpeed, Item.Settings settings) {
        return switch (material) {
            case MythicToolMaterials.LEGENDARY_BANGLUM ->
                    new LegendaryBanglumSwordItem(material, damage, attackSpeed, settings);
            case MythicToolMaterials.TIDESINGER ->
                    new TidesingerSwordItem(material, damage, attackSpeed, settings);
            case MythicToolMaterials.PALLADIUM ->
                    new PalladiumSwordItem(material, damage, attackSpeed, settings);
            default ->
                    new SimplyMoreSwordItem(material, damage, attackSpeed, settings);
        };
    }

    public static void registerCompatItems() {
        registerSet("adamantite", (int) adamantite_modifier, Rarity.COMMON);
        registerSet("aquarium", (int) aquarium_modifier, Rarity.COMMON);
        registerSet("banglum", (int) banglum_modifier, Rarity.COMMON);
        registerSet("bronze", (int) bronze_modifier, Rarity.COMMON);
        registerSet("carmot", (int) carmot_modifier, Rarity.COMMON);
        registerSet("celestium", (int) celestium_modifier, Rarity.RARE);
        registerSet("copper", (int) copper_modifier, Rarity.COMMON);
        registerSet("durasteel", (int) durasteel_modifier, Rarity.COMMON);
        registerSet("hallowed", (int) hallowed_modifier, Rarity.UNCOMMON);
        registerSet("kyber", (int) kyber_modifier, Rarity.COMMON);
        registerSet("legendary_banglum", (int) legendary_banglum_modifier, Rarity.UNCOMMON);
        registerSet("metallurgium", (int) metallurgium_modifier, Rarity.RARE);
        registerSet("mythril", (int) mythril_modifier, Rarity.COMMON);
        registerSet("orichalcum", (int) orichalcum_modifier, Rarity.COMMON);
        registerSet("osmium", (int) osmium_modifier, Rarity.COMMON);
        registerSet("palladium", (int) palladium_modifier, Rarity.COMMON);
        registerSet("prometheum", (int) prometheum_modifier, Rarity.COMMON);
        registerSet("quadrillum", (int) quadrillum_modifier, Rarity.COMMON);
        registerSet("runite", (int) runite_modifier, Rarity.COMMON);
        registerSet("star_platinum", (int) star_platinum_modifier, Rarity.COMMON);
        registerSet("steel", (int) steel_modifier, Rarity.COMMON);
        registerSet("stormyx", (int) stormyx_modifier, Rarity.COMMON);
        registerSet("tidesinger", (int) tidesinger_modifier, Rarity.COMMON);
    }

    public static void addToGroup(List<RegistrySupplier<? extends Item>> entries) {
        entries.addAll(itemList);
    }
}
