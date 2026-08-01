package net.rosemarythyme.simplymore.registry;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.rosemarythyme.simplymore.SimplyMore;

import java.util.Map;

import static java.util.Map.entry;

public class TagRegistry {
    public static TagKey<Item> LONGSWORD = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/longswords"));
    public static TagKey<Item> TWINBLADE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/twinblades"));
    public static TagKey<Item> RAPIER = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/rapiers"));
    public static TagKey<Item> KATANA = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/katanas"));
    public static TagKey<Item> SPEAR = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/spears"));
    public static TagKey<Item> GLAIVE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/glaives"));
    public static TagKey<Item> WARGLAIVE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/warglaives"));
    public static TagKey<Item> CUTLASS = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/cutlasses"));
    public static TagKey<Item> CLAYMORE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/claymores"));
    public static TagKey<Item> SAI = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/sais"));
    public static TagKey<Item> GREATHAMMER = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/greathammers"));
    public static TagKey<Item> GREATAXE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/greataxes"));
    public static TagKey<Item> CHAKRAM = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/chakrams"));
    public static TagKey<Item> SCYTHE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/scythes"));
    public static TagKey<Item> HALBERD = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/halberds"));
    public static TagKey<Item> GREAT_KATANA = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/great_katanas"));
    public static TagKey<Item> GRANDSWORD = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/grandswords"));
    public static TagKey<Item> BACKHAND_BLADE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/backhand_blades"));
    public static TagKey<Item> LANCE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/lances"));
    public static TagKey<Item> KHOPESH = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/khopeshs"));
    public static TagKey<Item> DAGGER = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/daggers"));
    public static TagKey<Item> PERNACH = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/pernachs"));
    public static TagKey<Item> QUARTERSTAFF = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/quarterstaffs"));
    public static TagKey<Item> GREAT_SPEAR = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/great_spears"));
    public static TagKey<Item> DEER_HORNS = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/deer_horns"));

    public static TagKey<Item> ALL = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("weapon_types/all"));
    public static TagKey<Item> UNIQUE = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("uniques"));

    public static TagKey<Item> LANCE_EFFECT = TagKey.of(RegistryKeys.ITEM, SimplyMore.identifier("lance_effect_override"));

    public static void register() {
    }

    public static final Map<String, TagKey<Item>> MIMICRY_TAGS = Map.ofEntries(
            entry("longsword", LONGSWORD),
            entry("twinblade", TWINBLADE),
            entry("rapier", RAPIER),
            entry("katana", KATANA),
            entry("spear", SPEAR),
            entry("sai", SAI),
            entry("glaive", GLAIVE),
            entry("warglaive", WARGLAIVE),
            entry("cutlass", CUTLASS),
            entry("claymore", CLAYMORE),
            entry("greathammer", GREATHAMMER),
            entry("greataxe", GREATAXE),
            entry("chakram", CHAKRAM),
            entry("scythe", SCYTHE),
            entry("halberd", HALBERD),
            entry("great_katana", GREAT_KATANA),
            entry("grandsword", GRANDSWORD),
            entry("backhand_blade", BACKHAND_BLADE),
            entry("lance", LANCE),
            entry("khopesh", KHOPESH),
            entry("dagger", DAGGER),
            entry("pernach", PERNACH),
            entry("quarterstaff", QUARTERSTAFF),
            entry("great_spear", GREAT_SPEAR),
            entry("deer_horns", DEER_HORNS)
    );
}
