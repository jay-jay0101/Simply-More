package net.rosemarythyme.simplymore.registry.item.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;

import java.util.List;

public class StickNStoneCompatRegistry {
    static final WeaponAttributesConfig ATTRIBUTES_CONFIG = ConfigWrapper.ATTRIBUTES;

    public static final List<RegistrySupplier<Item>> WOODEN_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "wooden",
            ATTRIBUTES_CONFIG.typeDamageModifier.wooden_damage_modifier.get(),
            ToolMaterials.WOOD,
            new Item.Settings()
    );

    public static final List<RegistrySupplier<Item>> STONE_WEAPONS = ItemRegistry.registerSet(
            SimplyMoreSwordItem.class,
            "stone",
            ATTRIBUTES_CONFIG.typeDamageModifier.stone_damage_modifier.get(),
            ToolMaterials.STONE,
            new Item.Settings()
    );

    public static void addToGroup() {
        ItemRegistry.addToItemGroup(WOODEN_WEAPONS);
        ItemRegistry.addToItemGroup(STONE_WEAPONS);
    }

    public static void registerCompatItems() {
    }
}
