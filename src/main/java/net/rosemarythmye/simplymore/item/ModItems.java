package net.rosemarythmye.simplymore.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.rosemarythmye.simplymore.Simplymore;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.normal.GrandSword;
import net.rosemarythmye.simplymore.item.normal.Lance;
import net.rosemarythmye.simplymore.item.normal.Sword;
import net.rosemarythmye.simplymore.item.runics.RunicGrandSword;
import net.rosemarythmye.simplymore.item.runics.RunicLance;
import net.rosemarythmye.simplymore.item.uniques.*;
import net.rosemarythmye.simplymore.item.uniques.joke.JesterPenetrate;
import net.rosemarythmye.simplymore.util.SimplyMoreToolMaterial;
import net.sweenus.simplyswords.config.Config;
import net.sweenus.simplyswords.config.ConfigDefaultValues;
import net.sweenus.simplyswords.item.RunicSwordItem;
import net.sweenus.simplyswords.registry.ItemsRegistry;

public class ModItems {
    static float iron_modifier = Config.getFloat("iron_damageModifier", "WeaponAttributes", ConfigDefaultValues.iron_damageModifier);
    static float gold_modifier = Config.getFloat("gold_damageModifier", "WeaponAttributes", ConfigDefaultValues.gold_damageModifier);
    static float diamond_modifier = Config.getFloat("diamond_damageModifier", "WeaponAttributes", ConfigDefaultValues.diamond_damageModifier);
    static float netherite_modifier = Config.getFloat("netherite_damageModifier", "WeaponAttributes", ConfigDefaultValues.netherite_damageModifier);
    static float runic_modifier = Config.getFloat("runic_damageModifier", "WeaponAttributes", ConfigDefaultValues.runic_damageModifier);



    public static final Item IRON_GREAT_KATANA = registerItem(
            "iron_great_katana",
            new Sword(
                    ToolMaterials.IRON,
                    (int)(iron_modifier + 1),
                    -2.6f,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GREAT_KATANA = registerItem(
            "gold_great_katana",
            new Sword(
                    ToolMaterials.GOLD,
                    (int)(gold_modifier + 1),
                    -2.6f,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GREAT_KATANA = registerItem(
            "diamond_great_katana",
            new Sword(
                    ToolMaterials.DIAMOND,
                    (int)(diamond_modifier + 1),
                    -2.6f,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GREAT_KATANA = registerItem(
            "netherite_great_katana",
            new Sword(
                    ToolMaterials.NETHERITE,
                    (int)(netherite_modifier + 1),
                    -2.6f,
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_GREAT_KATANA = registerItem(
            "runic_great_katana",
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    (int)(runic_modifier + 1),
                    -2.6f,
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_GRANDSWORD = registerItem(
            "iron_grandsword",
            new GrandSword(
                    ToolMaterials.IRON,
                    (int)(iron_modifier+6),
                    -3.5f,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_GRANDSWORD = registerItem(
            "gold_grandsword",
            new GrandSword(
                    ToolMaterials.GOLD,
                    (int)(gold_modifier+6),
                    -3.5f,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_GRANDSWORD = registerItem(
            "diamond_grandsword",
            new GrandSword(
                    ToolMaterials.DIAMOND,
                    (int)(diamond_modifier+6),
                    -3.5f,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_GRANDSWORD = registerItem(
            "netherite_grandsword",
            new GrandSword(
                    ToolMaterials.NETHERITE,
                    (int)(netherite_modifier+6),
                    -3.5f,
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_GRANDSWORD = registerItem(
            "runic_grandsword",
            new RunicGrandSword(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    (int)(runic_modifier+6),
                    -3.5f,
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    
    public static final Item IRON_BACKHAND_BLADE = registerItem(
            "iron_backhand_blade", 
            new Sword(
                    ToolMaterials.IRON,
                    (int)(iron_modifier - 2),
                    -1.7f,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_BACKHAND_BLADE = registerItem("gold_backhand_blade",
            new Sword(
                    ToolMaterials.GOLD,
                    (int)(gold_modifier - 2),
                    -1.7f,
                    new Item.Settings(),
                    "minecraft:gold_ingot"
            )
    );
    public static final Item DIAMOND_BACKHAND_BLADE = registerItem(
            "diamond_backhand_blade",
            new Sword(
                    ToolMaterials.DIAMOND,
                    (int)(diamond_modifier - 2),
                    -1.7f,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_BACKHAND_BLADE = registerItem(
            "netherite_backhand_blade",
            new Sword(
                    ToolMaterials.NETHERITE,
                    (int)(netherite_modifier - 2),
                    -1.7f,
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_BACKHAND_BLADE = registerItem(
            "runic_backhand_blade", 
            new RunicSwordItem(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    (int)(runic_modifier - 2),
                    -1.7f,
                    new Item.Settings()
                            .fireproof()
            )
    );

    public static final Item IRON_LANCE = registerItem(
            "iron_lance",
            new Lance(
                    ToolMaterials.IRON,
                    (int)(iron_modifier+0),
                    -3f,
                    new Item.Settings(),
                    "minecraft:iron_ingot"
            )
    );
    public static final Item GOLD_LANCE = registerItem(
            "gold_lance",
            new Lance(
                    ToolMaterials.GOLD,
                    (int)(gold_modifier + 0),
                    -3f,
                    new Item.Settings(),
                    "minecraft:gold_ingot"));
    public static final Item DIAMOND_LANCE = registerItem(
            "diamond_lance",
            new Lance(
                    ToolMaterials.DIAMOND,
                    (int)(diamond_modifier + 0),
                    -3f,
                    new Item.Settings(),
                    "minecraft:diamond"
            )
    );
    public static final Item NETHERITE_LANCE = registerItem(
            "netherite_lance",
            new Lance(
                    ToolMaterials.NETHERITE,
                    (int)(netherite_modifier + 0),
                    -3f,
                    new Item.Settings()
                            .fireproof(),
                    "minecraft:netherite_ingot"
            )
    );
    public static final Item RUNIC_LANCE = registerItem(
            "runic_lance",
            new RunicLance(
                    SimplyMoreToolMaterial.SIMPLY_MORE_RUNIC,
                    (int)(runic_modifier + 0),
                    -3f,
                    new Item.Settings()
                            .fireproof()
            )
    );


    public static final Item GREAT_SLITHER = registerItem(
            "great_slither",
            new GreatSlither(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    5,
                    -2.6f,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final Item MOLTEN_FLARE = registerItem(
            "molten_flare",
            new MoltenFlare(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    7,
                    -3.5f,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final Item GRANDFROST = registerItem(
            "grandfrost",
            new Grandfrost(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    8,
                    -3.5f,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final Item MIMICRY = registerItem(
            "mimicry",
            new Mimicry(
                    SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    2,
                    -2f,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)       
            )
    );
    public static final Item GLIMMERSTEP = registerItem(
            "glimmerstep",
            new Glimmerstep(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    1,
                    -3f,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );
    public static final Item THEBLOODHARVESTER = registerItem(
            "the_blood_harvester",
            new TheBloodHarvester(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    2,
                    -2.4f,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );
    public static final Item JESTER_PENETRATE = registerItem(
            "jester_penetrate",
            new JesterPenetrate(SimplyMoreToolMaterial.SIMPLY_MORE_UNIQUE,
                    0,
                    -3f,
                    new Item.Settings()
                            .fireproof()
                            .rarity(Rarity.EPIC)
            )
    );


    public static final Item RUNEFUSED_CARVER = registerItem(
            "runefused_carver",
            new RuneCarver(
                    new Item.Settings()
                            .maxCount(1)
                            .fireproof()
                            .rarity(Rarity.EPIC),
                    "runic"
            )
    );
    public static final Item NETHERFUSED_CARVER = registerItem(
            "netherfused_carver",
            new RuneCarver(
                    new Item.Settings()
                            .maxCount(1)
                            .fireproof()
                            .rarity(Rarity.EPIC),
                    "nether"
            )
    );

    public static Item registerItem (String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Simplymore.ID, name),item);
    }
    public static void registerModItems() {
        if (FabricLoader.getInstance().isModLoaded("simplyswords")) {
            Simplymore.LOGGER.info("Registering Items for " + Simplymore.ID);
            Registry.register(Registries.ITEM_GROUP, Identifier.of(Simplymore.ID, "items"), ITEM_GROUP);
            registerModelPredicates();
        }
    }


    private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemsRegistry.RUNEFUSED_GEM.get()))
            .displayName(Text.translatable("item_group.simplymore"))
            .entries((context, entries) -> {
                entries.add(RUNEFUSED_CARVER);
                entries.add(NETHERFUSED_CARVER);
                entries.add(IRON_GREAT_KATANA);
                entries.add(IRON_GRANDSWORD);
                entries.add(IRON_BACKHAND_BLADE);
                entries.add(IRON_LANCE);
                entries.add(GOLD_GREAT_KATANA);
                entries.add(GOLD_GRANDSWORD);
                entries.add(GOLD_BACKHAND_BLADE);
                entries.add(GOLD_LANCE);
                entries.add(DIAMOND_GREAT_KATANA);
                entries.add(DIAMOND_GRANDSWORD);
                entries.add(DIAMOND_BACKHAND_BLADE);
                entries.add(DIAMOND_LANCE);
                entries.add(NETHERITE_GREAT_KATANA);
                entries.add(NETHERITE_GRANDSWORD);
                entries.add(NETHERITE_BACKHAND_BLADE);
                entries.add(NETHERITE_LANCE);
                entries.add(RUNIC_GREAT_KATANA);
                entries.add(RUNIC_GRANDSWORD);
                entries.add(RUNIC_BACKHAND_BLADE);
                entries.add(RUNIC_LANCE);
                entries.add(GREAT_SLITHER);
                entries.add(MOLTEN_FLARE);
                entries.add(GRANDFROST);
                entries.add(MIMICRY);
                entries.add(GLIMMERSTEP);
                entries.add(THEBLOODHARVESTER);

                entries.add(JESTER_PENETRATE);
            })
            .build();

    private static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(MIMICRY, new Identifier(Simplymore.ID, "mimicry_form"), (itemStack, clientWorld, livingEntity, a) -> {
            Object form = itemStack.getOrCreateNbt().get("simplymore:form");
            if (form==null) return 0f;
            return form.toString().equals("\"twisted\"")? 1f:0f;
        });

        ModelPredicateProviderRegistry.register(THEBLOODHARVESTER, new Identifier(Simplymore.ID, "harvest"), (itemStack, clientWorld, livingEntity, a) -> {
            if (livingEntity == null) return 0f;
            return livingEntity.hasStatusEffect(ModEffects.HARVEST)? 1f:0f;
        });
    }
}
