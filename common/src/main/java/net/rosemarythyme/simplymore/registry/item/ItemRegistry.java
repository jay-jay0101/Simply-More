package net.rosemarythyme.simplymore.registry.item;

import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.item.RemovedItem;
import net.rosemarythyme.simplymore.item.SimplyMoreRunicSwordItem;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.item.uniques.*;
import net.rosemarythyme.simplymore.item.uniques.joke.JesterPenetrateItem;
import net.rosemarythyme.simplymore.item.uniques.joke.ThePanItem;
import net.rosemarythyme.simplymore.item.uniques.mimicry.*;
import net.rosemarythyme.simplymore.registry.item.compat.MythicMetalsCompatRegistry;
import net.rosemarythyme.simplymore.registry.item.compat.StickNStoneCompatRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreToolMaterial;
import net.sweenus.simplyswords.config.Config;
import net.sweenus.simplyswords.item.component.AwakeningComponent;
import net.sweenus.simplyswords.item.component.AwakeningRouteComponent;
import net.sweenus.simplyswords.registry.ComponentTypeRegistry;
import net.sweenus.simplyswords.registry.ItemsRegistry;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    static final WeaponAttributesConfig ATTRIBUTES_CONFIG = ConfigWrapper.ATTRIBUTES;
    static final WeaponAttributesConfig.WeaponTypesDamage TYPE_DAMAGE_CONFIG = ATTRIBUTES_CONFIG.weaponTypesDamage;
    static final WeaponAttributesConfig.WeaponTypesSwingSpeed TYPE_SPEED_CONFIG = ATTRIBUTES_CONFIG.weaponTypesSwingSpeed;

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ITEM);

    public static final DeferredRegister<ItemGroup> TABS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.ITEM_GROUP);

    public static final List<WeaponType> WEAPON_TYPES = List.of(
            new WeaponType("great_katana", TYPE_DAMAGE_CONFIG.greatkatana_damage_modifier, TYPE_SPEED_CONFIG.greatkatana_attack_speed),
            new WeaponType("grandsword", TYPE_DAMAGE_CONFIG.grandsword_damage_modifier, TYPE_SPEED_CONFIG.grandsword_attack_speed),
            new WeaponType("backhand_blade", TYPE_DAMAGE_CONFIG.backhandblade_damage_modifier, TYPE_SPEED_CONFIG.backhandblade_attack_speed),
            new WeaponType("lance", TYPE_DAMAGE_CONFIG.lance_damage_modifier, TYPE_SPEED_CONFIG.lance_attack_speed),
            new WeaponType("khopesh", TYPE_DAMAGE_CONFIG.khopesh_damage_modifier, TYPE_SPEED_CONFIG.khopesh_attack_speed),
            new WeaponType("dagger", TYPE_DAMAGE_CONFIG.dagger_damage_modifier, TYPE_SPEED_CONFIG.dagger_attack_speed),
            new WeaponType("pernach", TYPE_DAMAGE_CONFIG.pernach_damage_modifier, TYPE_SPEED_CONFIG.pernach_attack_speed),
            new WeaponType("quarterstaff", TYPE_DAMAGE_CONFIG.quarterstaff_damage_modifier, TYPE_SPEED_CONFIG.quarterstaff_attack_speed),
            new WeaponType("great_spear", TYPE_DAMAGE_CONFIG.greatspear_damage_modifier, TYPE_SPEED_CONFIG.greatspear_attack_speed),
            new WeaponType("deer_horns", TYPE_DAMAGE_CONFIG.deerhorns_damage_modifier, TYPE_SPEED_CONFIG.deerhorns_attack_speed)
    );

    public record WeaponType(String name, int damage, float swingSpeed) {}


    public static final List<RegistrySupplier<Item>> IRON_WEAPONS = registerSet(
            SimplyMoreSwordItem.class,
            "iron",
            (int) Config.weaponAttribute.materialDamageModifier.iron_damageModifier,
            ToolMaterials.IRON
    );

    public static final List<RegistrySupplier<Item>> GOLD_WEAPONS = registerSet(
            SimplyMoreSwordItem.class,
            "gold",
            (int) Config.weaponAttribute.materialDamageModifier.gold_damageModifier,
            ToolMaterials.GOLD
    );

    public static final List<RegistrySupplier<Item>> DIAMOND_WEAPONS = registerSet(
            SimplyMoreSwordItem.class,
            "diamond",
            (int) Config.weaponAttribute.materialDamageModifier.diamond_damageModifier,
            ToolMaterials.DIAMOND
    );

    public static final List<RegistrySupplier<Item>> NETHERITE_WEAPONS = registerSet(
            SimplyMoreSwordItem.class,
            "netherite",
            (int) Config.weaponAttribute.materialDamageModifier.netherite_damageModifier,
            ToolMaterials.NETHERITE,
            new Item.Settings().fireproof()
    );

    public static final List<RegistrySupplier<Item>> RUNIC_WEAPONS = registerSet(
            SimplyMoreRunicSwordItem.class,
            "runic",
            (int) Config.weaponAttribute.materialDamageModifier.runic_damageModifier,
            SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
            new Item.Settings().fireproof()
    );

    @Deprecated
    public static final RegistrySupplier<Item> RUNEFUSED_CARVER = ITEMS.register(
            "runefused_carver",
            () -> new RemovedItem(() -> new ItemStack(ItemsRegistry.EMPOWERED_REMNANT.get()))
    );

    @Deprecated
    public static final RegistrySupplier<Item> NETHERFUSED_CARVER = ITEMS.register(
            "netherfused_carver",
            () -> new RemovedItem(() -> new ItemStack(ItemsRegistry.EMPOWERED_REMNANT.get()))
    );

    public static final RegistrySupplier<Item> GREAT_SLITHER = ITEMS.register(
            "great_slither",
            () -> new GreatSlitherItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.greatslither_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.greatslither_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MAGMASEEP = ITEMS.register(
            "magmaseep",
            () -> new MagmaseepItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.moltenflare_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.moltenflare_attack_speed
            )
    );

    @Deprecated
    public static final RegistrySupplier<Item> MOLTEN_FLARE = ITEMS.register(
            "molten_flare",
            () -> new RemovedItem(() -> new ItemStack(MAGMASEEP.get()))
    );

    public static final RegistrySupplier<Item> GRANDFROST = ITEMS.register(
            "grandfrost",
            () -> new GrandfrostItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.grandfrost_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.grandfrost_attack_speed
            )
    );

    public static final RegistrySupplier<Item> GLIMMERSTEP = ITEMS.register(
            "glimmerstep",
            () -> new GlimmerstepItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.glimmerstep_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.glimmerstep_attack_speed
            )
    );

    public static final RegistrySupplier<Item> THE_BLOOD_HARVESTER = ITEMS.register(
            "the_blood_harvester",
            () -> new TheBloodHarvesterItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.thebloodharvester_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.thebloodharvester_attack_speed
            )
    );

    public static final RegistrySupplier<Item> JESTER_PENETRATE = ITEMS.register(
            "jester_penetrate",
            () -> new JesterPenetrateItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.jesterpenetrate_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.jesterpenetrate_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MYRMEDGE = ITEMS.register(
            "myrmedge",
            () -> new MyrmedgeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.myrmedge_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.myrmedge_attack_speed
            )
    );

    public static final RegistrySupplier<Item> BLACK_PEARL = ITEMS.register(
            "black_pearl",
            () -> new BlackPearlItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.blackpearl_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.blackpearl_attack_speed
            )
    );

    public static final RegistrySupplier<Item> THE_PAN = ITEMS.register(
            "the_pan",
            () -> new ThePanItem(SimplyMoreToolMaterial.SIMPLY_MORE_JOKE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.thepan_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.thepan_attack_speed
            )
    );

    public static final RegistrySupplier<Item> THE_VESSEL_BREACH = ITEMS.register(
            "the_vessel_breach",
            () -> new TheVesselBreachItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.thevesselbreach_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.thevesselbreach_attack_speed
            )
    );

    public static final RegistrySupplier<Item> BLADE_OF_THE_GROTESQUE = ITEMS.register(
            "blade_of_the_grotesque",
            () -> new BladeOfTheGrotesqueItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.bladeofthegrotesque_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.bladeofthegrotesque_attack_speed
            )
    );

    public static final RegistrySupplier<Item> VIPERS_CALL = ITEMS.register(
            "vipers_call",
            () -> new VipersCallItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.viperscall_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.viperscall_attack_speed
            )
    );

    public static final RegistrySupplier<Item> TIMEKEEPER = ITEMS.register(
            "timekeeper",
            () -> new TimekeeperItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.timekeeper_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.timekeeper_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MATTERBANE = ITEMS.register(
            "matterbane",
            () -> new MatterbaneItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.matterbane_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.matterbane_attack_speed
            )
    );

    public static final RegistrySupplier<Item> SMOULDERING_RUIN = ITEMS.register(
            "smouldering_ruin",
            () -> new SmoulderingRuinItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.smoulderingruin_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.smoulderingruin_attack_speed
            )
    );

    public static final RegistrySupplier<Item> STASIS = ITEMS.register(
            "stasis",
            () -> new StasisItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.stasis_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.stasis_attack_speed
            )
    );

    public static final RegistrySupplier<Item> TIDEBREAKER = ITEMS.register(
            "tidebreaker",
            () -> new TidebreakerItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.tidebreaker_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.tidebreaker_attack_speed
            )
    );

    public static final RegistrySupplier<Item> RUYI_JINGU_BANG = ITEMS.register(
            "ruyi_jingu_bang",
            () -> new RuyiJinguBangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.ruyijingubang_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.ruyijingubang_attack_speed
            )
    );

    public static final RegistrySupplier<Item> RUPTURED_IDOL = ITEMS.register(
            "ruptured_idol",
            () -> new IdolItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.rupturedidol_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.rupturedidol_attack_speed
            )
    );

    @Deprecated
    public static final RegistrySupplier<Item> ASCENDED_IDOL = ITEMS.register(
            "ascended_idol",
            () -> new RemovedItem(() -> getAwakenedStack(RUPTURED_IDOL, 4, AwakeningProfileRegistry.HOLYLIGHT))
    );

    @Deprecated
    public static final RegistrySupplier<Item> TARNISHED_IDOL = ITEMS.register(
            "tarnished_idol",
            () -> new RemovedItem(() -> getAwakenedStack(RUPTURED_IDOL, 4, AwakeningProfileRegistry.DARKSENT))
    );

    @Deprecated
    public static final RegistrySupplier<Item> HOLYLIGHT = ITEMS.register(
            "holylight",
            () -> new RemovedItem(() -> getAwakenedStack(RUPTURED_IDOL, 8, AwakeningProfileRegistry.HOLYLIGHT))
    );

    @Deprecated
    public static final RegistrySupplier<Item> DARKSENT = ITEMS.register(
            "darksent",
            () -> new RemovedItem(() -> getAwakenedStack(RUPTURED_IDOL, 8, AwakeningProfileRegistry.DARKSENT))
    );

    @Deprecated
    public static final RegistrySupplier<Item> SCARAB_ROLLER = ITEMS.register(
            "scarab_roller",
            () -> new RemovedItem(() -> new ItemStack(MYRMEDGE.get()))
    );


    public static final RegistrySupplier<Item> BOAS_FANG = ITEMS.register(
            "boas_fang",
            () -> new BoasFangItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.boasfang_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.boasfang_attack_speed
            )
    );

    public static final RegistrySupplier<Item> EARTHSHATTER = ITEMS.register(
            "earthshatter",
            () -> new EarthshatterItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.earthshatter_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.earthshatter_attack_speed
            )
    );

    public static final RegistrySupplier<Item> SOULFRACTURE = ITEMS.register(
            "soulfracture",
            () -> new SoulfractureItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.soulfracture_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.soulfracture_attack_speed
            )
    );

    @Deprecated
    public static final RegistrySupplier<Item> SOUL_FORESEER = ITEMS.register(
            "soul_foreseer", () -> new RemovedItem(() -> new ItemStack(SOULFRACTURE.get()))
    );

    public static final RegistrySupplier<Item> SERPENTINE_VALOUR = ITEMS.register(
            "serpentine_valour",
            () -> new SerpentineValourItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.serpentinevalour_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.serpentinevalour_attack_speed
            )
    );

    public static final RegistrySupplier<Item> LUSTROUS_MOXIE = ITEMS.register(
            "lustrous_moxie",
            () -> new LustrousMoxieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.lustrousmoxie_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.lustrousmoxie_attack_speed
            )
    );

    public static final RegistrySupplier<Item> BRASSTURN = ITEMS.register(
            "brassturn",
            () -> new BrassturnItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.brassturn_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.brassturn_attack_speed
            )
    );

    public static final RegistrySupplier<Item> CINDERGORGE = ITEMS.register(
            "cindergorge",
            () -> new CindergorgeItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.cindergorge_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.cindergorge_attack_speed
            )
    );

    public static final RegistrySupplier<Item> DEATHS_EYRIE = ITEMS.register(
            "deaths_eyrie",
            () -> new DeathsEyrieItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.deathseyrie_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.deathseyrie_attack_speed
            )
    );

    public static final RegistrySupplier<Item> PERFORISCUS = ITEMS.register(
            "perforiscus",
            () -> new PerforiscusItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.perforiscus_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.perforiscus_attack_speed
            )
    );

    public static final RegistrySupplier<Item> REVVENGINE = ITEMS.register(
            "revvengine",
            () -> new RevvengineItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.revvengine_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.revvengine_attack_speed
            )
    );

    public static final RegistrySupplier<Item> EXEDRILL = ITEMS.register(
            "exedrill",
            () -> new ExedrillItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.exedrill_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.exedrill_attack_speed
            )
    );

    public static final RegistrySupplier<Item> CULTEREX = ITEMS.register(
            "culterex",
            () -> new CulterexItem(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.uniqueWeaponsDamage.culterex_damage_modifier,
                    ATTRIBUTES_CONFIG.uniqueWeaponsSwingSpeed.culterex_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_LONGSWORD = ITEMS.register(
            "mimicry_longsword",
            () -> new LongswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.longsword_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.longsword_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_TWINBLADE = ITEMS.register(
            "mimicry_twinblade",
            () -> new TwinbladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.twinblade_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.twinblade_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_RAPIER = ITEMS.register(
            "mimicry_rapier",
            () -> new RapierItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.rapier_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.rapier_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_KATANA = ITEMS.register(
            "mimicry_katana",
            () -> new KatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.katana_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.katana_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SPEAR = ITEMS.register(
            "mimicry_spear",
            () -> new SpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.spear_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.spear_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SAI = ITEMS.register(
            "mimicry_sai",
            () -> new SaiItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.sai_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.sai_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GLAIVE = ITEMS.register(
            "mimicry_glaive",
            () -> new GlaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.glaive_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.glaive_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_WARGLAIVE = ITEMS.register(
            "mimicry_warglaive",
            () -> new WarglaiveItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.warglaive_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.warglaive_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CUTLASS = ITEMS.register(
            "mimicry_cutlass",
            () -> new CutlassItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.cutlass_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.cutlass_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CLAYMORE = ITEMS.register(
            "mimicry_claymore",
            () -> new ClaymoreItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.claymore_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.claymore_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREATHAMMER = ITEMS.register(
            "mimicry_greathammer",
            () -> new GreathammerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.greathammer_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.greathammer_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREATAXE = ITEMS.register(
            "mimicry_greataxe",
            () -> new GreataxeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.greataxe_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.greataxe_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_CHAKRAM = ITEMS.register(
            "mimicry_chakram",
            () -> new ChakramItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.chakram_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.chakram_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_SCYTHE = ITEMS.register(
            "mimicry_scythe",
            () -> new ScytheItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.scythe_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.scythe_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_HALBERD = ITEMS.register(
            "mimicry_halberd",
            () -> new HalberdItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    (int) (Config.weaponAttribute.typeDamageModifier.halberd_damageModifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier),
                    Config.weaponAttribute.typeAttackSpeed.halberd_attackSpeed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREAT_KATANA = ITEMS.register(
            "mimicry_great_katana",
            () -> new GreatKatanaItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.greatkatana_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.greatkatana_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GRANDSWORD = ITEMS.register(
            "mimicry_grandsword",
            () -> new GrandswordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.grandsword_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.grandsword_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_BACKHAND_BLADE = ITEMS.register(
            "mimicry_backhand_blade",
            () -> new BackhandBladeItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.backhandblade_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.backhandblade_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_LANCE = ITEMS.register(
            "mimicry_lance",
            () -> new net.rosemarythyme.simplymore.item.uniques.mimicry.LanceItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.lance_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.lance_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_KHOPESH = ITEMS.register(
            "mimicry_khopesh",
            () -> new KhopeshItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.khopesh_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.khopesh_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_DAGGER = ITEMS.register(
            "mimicry_dagger",
            () -> new DaggerItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.dagger_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.dagger_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_PERNACH = ITEMS.register(
            "mimicry_pernach",
            () -> new PernachItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.pernach_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.pernach_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_QUARTERSTAFF = ITEMS.register(
            "mimicry_quarterstaff",
            () -> new QuarterstaffItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.quarterstaff_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.quarterstaff_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_GREAT_SPEAR = ITEMS.register(
            "mimicry_great_spear",
            () -> new GreatSpearItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.greatspear_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.greatspear_attack_speed
            )
    );

    public static final RegistrySupplier<Item> MIMICRY_DEER_HORNS = ITEMS.register(
            "mimicry_deer_horns",
            () -> new DeerHornsItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    ATTRIBUTES_CONFIG.weaponTypesDamage.deerhorns_damage_modifier + ATTRIBUTES_CONFIG.uniqueWeaponsDamage.mimicry_damage_modifier,
                    ATTRIBUTES_CONFIG.weaponTypesSwingSpeed.deerhorns_attack_speed
            )
    );

    @Deprecated
    public static final RegistrySupplier<Item> MIMICRY = ITEMS.register(
            "mimicry",
            () -> new RemovedItem(() -> new ItemStack(MIMICRY_LONGSWORD.get()))
    );

    public static void register() {
        if (Platform.isModLoaded("sticknstone")) {
            StickNStoneCompatRegistry.registerCompatItems();
        }

        if (Platform.isModLoaded("mythicmetals")) {
            MythicMetalsCompatRegistry.registerCompatItems();
        }

        ITEMS.register();
    }

    public static List<RegistrySupplier<Item>> registerSet(Class<? extends Item> clazz, String name, int damage, ToolMaterial material, Item.Settings settings) {
        ArrayList<RegistrySupplier<Item>> items = new ArrayList<>();

        Constructor<? extends Item> constructor;
        try {
            constructor = clazz.getConstructor(ToolMaterial.class, int.class, float.class, Item.Settings.class);
        } catch (NoSuchMethodException ignored) {
            return items;
        }

        for (WeaponType type : WEAPON_TYPES) {
            String id = name + "_" + type.name;
            Item item;

            try {
                item = constructor.newInstance(
                        material,
                        damage + type.damage,
                        type.swingSpeed,
                        settings
                );
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            items.add(ITEMS.register(id, () -> item));
        }

        return List.copyOf(items);
    }

    public static List<RegistrySupplier<Item>> registerSet(Class<? extends Item> clazz, String name, int damage, ToolMaterial material) {
        return registerSet(clazz, name, damage, material, new Item.Settings());
    }

    public static final RegistrySupplier<ItemGroup> ITEM_GROUP =
            TABS.register(
                    "simplymore",
                    () ->
                            CreativeTabRegistry.create(
                                    Text.translatable("item_group.simplymore"),
                                    () -> new ItemStack(IRON_WEAPONS.get(5))
                            )
            );

    public static void registerItemGroup() {
        if (Platform.isModLoaded("sticknstone")) {
            StickNStoneCompatRegistry.addToGroup();
        }

        addToItemGroup(IRON_WEAPONS);
        addToItemGroup(GOLD_WEAPONS);
        addToItemGroup(DIAMOND_WEAPONS);
        addToItemGroup(NETHERITE_WEAPONS);
        addToItemGroup(RUNIC_WEAPONS);

        if (Platform.isModLoaded("mythicmetals")) {
            MythicMetalsCompatRegistry.addToGroup();
        }
        
        addToItemGroup(GREAT_SLITHER);
        addToItemGroup(MAGMASEEP);
        addToItemGroup(GRANDFROST);
        addToItemGroup(MIMICRY_LONGSWORD);
        addToItemGroup(GLIMMERSTEP);
        addToItemGroup(THE_BLOOD_HARVESTER);
        addToItemGroup(MYRMEDGE);
        addToItemGroup(BLACK_PEARL);
        addToItemGroup(THE_VESSEL_BREACH);
        addToItemGroup(BLADE_OF_THE_GROTESQUE);
        addToItemGroup(VIPERS_CALL);
        addToItemGroup(TIMEKEEPER);
        addToItemGroup(MATTERBANE);
        addToItemGroup(SMOULDERING_RUIN);
        addToItemGroup(STASIS);
        addToItemGroup(TIDEBREAKER);
        addToItemGroup(RUYI_JINGU_BANG);

        addToItemGroup(getAwakenedStack(RUPTURED_IDOL, 0, null));
        addToItemGroup(getAwakenedStack(RUPTURED_IDOL, 4, AwakeningProfileRegistry.HOLYLIGHT));
        addToItemGroup(getAwakenedStack(RUPTURED_IDOL, 4, AwakeningProfileRegistry.DARKSENT));
        addToItemGroup(getAwakenedStack(RUPTURED_IDOL, 8, AwakeningProfileRegistry.HOLYLIGHT));
        addToItemGroup(getAwakenedStack(RUPTURED_IDOL, 8, AwakeningProfileRegistry.DARKSENT));

        addToItemGroup(BOAS_FANG);
        addToItemGroup(EARTHSHATTER);
        addToItemGroup(SOULFRACTURE);
        addToItemGroup(SERPENTINE_VALOUR);
        addToItemGroup(LUSTROUS_MOXIE);
        addToItemGroup(BRASSTURN);
        addToItemGroup(CINDERGORGE);
        addToItemGroup(DEATHS_EYRIE);
        addToItemGroup(PERFORISCUS);
        addToItemGroup(REVVENGINE);
        addToItemGroup(EXEDRILL);
        addToItemGroup(CULTEREX);
        addToItemGroup(JESTER_PENETRATE);
        addToItemGroup(THE_PAN);

        TABS.register();
    }


    public static void addToItemGroup(ItemStack stack) {
        //noinspection UnstableApiUsage
        CreativeTabRegistry.appendStack(ITEM_GROUP, stack);
    }

    public static void addToItemGroup(List<RegistrySupplier<Item>> items) {
        //noinspection UnstableApiUsage, unchecked
        CreativeTabRegistry.append(ITEM_GROUP, items.toArray(new RegistrySupplier[0]));
    }

    public static void addToItemGroup(RegistrySupplier<Item> item) {
        //noinspection UnstableApiUsage, unchecked
        CreativeTabRegistry.append(ITEM_GROUP, item);
    }

    public static ItemStack getAwakenedStack(RegistrySupplier<Item> item, int level, @Nullable Identifier path) {
        ItemStack stack = new ItemStack(item);
        stack.set(ComponentTypeRegistry.AWAKENING.get(), new AwakeningComponent(level));

        if(path != null) {
            stack.set(ComponentTypeRegistry.AWAKENING_ROUTE.get(), new AwakeningRouteComponent(path));
        }

        return stack;
    }
}
