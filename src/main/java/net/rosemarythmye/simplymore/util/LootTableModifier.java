package net.rosemarythmye.simplymore.util;

import dev.architectury.event.events.common.LootEvent;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.rosemarythmye.simplymore.config.LootConfig;
import net.rosemarythmye.simplymore.config.WeaponAttributesConfig;
import net.rosemarythmye.simplymore.config.WrapperConfig;
import net.rosemarythmye.simplymore.item.ModItems;
import net.sweenus.simplyswords.config.Config;
import net.sweenus.simplyswords.config.ConfigDefaultValues;
import net.sweenus.simplyswords.registry.ItemsRegistry;

public class LootTableModifier {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static LootConfig loot = config.loot;

    public static void registerLootTableChanges() {
        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getIronLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getIronLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItems.IRON_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItems.IRON_GRANDSWORD))
                        .with(ItemEntry.builder(ModItems.IRON_KHOPESH))
                        .with(ItemEntry.builder(ModItems.IRON_LANCE))
                        .with(ItemEntry.builder(ModItems.IRON_GREAT_KATANA));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getGoldLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getGoldLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItems.GOLD_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItems.GOLD_GRANDSWORD))
                        .with(ItemEntry.builder(ModItems.GOLD_KHOPESH))
                        .with(ItemEntry.builder(ModItems.GOLD_LANCE))
                        .with(ItemEntry.builder(ModItems.GOLD_GREAT_KATANA));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getDiamondLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getDiamondLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItems.DIAMOND_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItems.DIAMOND_GRANDSWORD))
                        .with(ItemEntry.builder(ModItems.DIAMOND_KHOPESH))
                        .with(ItemEntry.builder(ModItems.DIAMOND_LANCE))
                        .with(ItemEntry.builder(ModItems.DIAMOND_GREAT_KATANA));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getNetheriteLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getNetheriteLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItems.NETHERITE_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItems.NETHERITE_GRANDSWORD))
                        .with(ItemEntry.builder(ModItems.NETHERITE_KHOPESH))
                        .with(ItemEntry.builder(ModItems.NETHERITE_LANCE))
                        .with(ItemEntry.builder(ModItems.NETHERITE_GREAT_KATANA));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getRunicLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getRunicLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItems.RUNIC_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItems.RUNIC_GRANDSWORD))
                        .with(ItemEntry.builder(ModItems.RUNIC_KHOPESH))
                        .with(ItemEntry.builder(ModItems.RUNIC_LANCE))
                        .with(ItemEntry.builder(ModItems.RUNIC_GREAT_KATANA));
                context.addPool(pool);
            }
        });

    }
}
