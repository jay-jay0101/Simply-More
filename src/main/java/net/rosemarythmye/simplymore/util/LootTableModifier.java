package net.rosemarythmye.simplymore.util;

import dev.architectury.event.events.common.LootEvent;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.rosemarythmye.simplymore.config.LootConfig;
import net.rosemarythmye.simplymore.config.WrapperConfig;
import net.rosemarythmye.simplymore.item.ModItems;

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
                        .with(ItemEntry.builder(ModItems.IRON_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItems.IRON_DAGGER))
                        .with(ItemEntry.builder(ModItems.IRON_PERNACH))
                        .with(ItemEntry.builder(ModItems.IRON_QUARTERSTAFF))
                        .with(ItemEntry.builder(ModItems.IRON_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItems.IRON_DEER_HORNS));
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
                        .with(ItemEntry.builder(ModItems.GOLD_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItems.GOLD_DAGGER))
                        .with(ItemEntry.builder(ModItems.GOLD_PERNACH))
                        .with(ItemEntry.builder(ModItems.GOLD_QUARTERSTAFF))
                        .with(ItemEntry.builder(ModItems.GOLD_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItems.GOLD_DEER_HORNS));
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
                        .with(ItemEntry.builder(ModItems.DIAMOND_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItems.DIAMOND_DAGGER))
                        .with(ItemEntry.builder(ModItems.DIAMOND_PERNACH))
                        .with(ItemEntry.builder(ModItems.DIAMOND_QUARTERSTAFF))
                        .with(ItemEntry.builder(ModItems.DIAMOND_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItems.DIAMOND_DEER_HORNS));
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
                        .with(ItemEntry.builder(ModItems.NETHERITE_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItems.NETHERITE_DAGGER))
                        .with(ItemEntry.builder(ModItems.NETHERITE_PERNACH))
                        .with(ItemEntry.builder(ModItems.NETHERITE_QUARTERSTAFF))
                        .with(ItemEntry.builder(ModItems.NETHERITE_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItems.NETHERITE_DEER_HORNS));
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
                        .with(ItemEntry.builder(ModItems.RUNIC_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItems.RUNIC_DAGGER))
                        .with(ItemEntry.builder(ModItems.RUNIC_PERNACH))
                        .with(ItemEntry.builder(ModItems.RUNIC_QUARTERSTAFF))
                        .with(ItemEntry.builder(ModItems.RUNIC_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItems.RUNIC_DEER_HORNS));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getUniqueLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getUniqueLootWeight()));
                if(loot.isEnableGlimmerstep()) pool.with(ItemEntry.builder(ModItems.GLIMMERSTEP));
                if(loot.isEnableGrandfrost()) pool.with(ItemEntry.builder(ModItems.GRANDFROST));
                if(loot.isEnableGreatSlither()) pool.with(ItemEntry.builder(ModItems.GREAT_SLITHER));
                if(loot.isEnableJesterPenetrate() && loot.isEnableJokeUniqueChestLoot()) pool.with(ItemEntry.builder(ModItems.JESTER_PENETRATE));
                if(loot.isEnableMimicry()) pool.with(ItemEntry.builder(ModItems.MIMICRY));
                if(loot.isEnableScarabRoller()) pool.with(ItemEntry.builder(ModItems.SCARAB_ROLLER));
                if(loot.isEnableMoltenFlare()) pool.with(ItemEntry.builder(ModItems.MOLTEN_FLARE));
                if(loot.isEnableTheBloodHarvester()) pool.with(ItemEntry.builder(ModItems.THEBLOODHARVESTER));
                if(loot.isEnableBlackPearl()) pool.with(ItemEntry.builder(ModItems.BLACK_PEARL));
                if(loot.isEnableThePan() && loot.isEnableJokeUniqueChestLoot()) pool.with(ItemEntry.builder(ModItems.THE_PAN));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.isEnableWitherDropsUnique() && id.getPath().equals("entities/wither")) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder(0.05f));
                if(loot.isEnableGlimmerstep()) pool.with(ItemEntry.builder(ModItems.GLIMMERSTEP));
                if(loot.isEnableGrandfrost()) pool.with(ItemEntry.builder(ModItems.GRANDFROST));
                if(loot.isEnableGreatSlither()) pool.with(ItemEntry.builder(ModItems.GREAT_SLITHER));
                if(loot.isEnableJesterPenetrate() && loot.isEnableJokeUniqueBossDrops()) pool.with(ItemEntry.builder(ModItems.JESTER_PENETRATE));
                if(loot.isEnableMimicry()) pool.with(ItemEntry.builder(ModItems.MIMICRY));
                if(loot.isEnableScarabRoller()) pool.with(ItemEntry.builder(ModItems.SCARAB_ROLLER));
                if(loot.isEnableMoltenFlare()) pool.with(ItemEntry.builder(ModItems.MOLTEN_FLARE));
                if(loot.isEnableTheBloodHarvester()) pool.with(ItemEntry.builder(ModItems.THEBLOODHARVESTER));
                if(loot.isEnableBlackPearl()) pool.with(ItemEntry.builder(ModItems.BLACK_PEARL));
                if(loot.isEnableThePan() && loot.isEnableJokeUniqueBossDrops()) pool.with(ItemEntry.builder(ModItems.THE_PAN));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.isEnableDragonDropsUnique() && id.getPath().equals("entities/ender_dragon")) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder(0.5f));
                if(loot.isEnableGlimmerstep()) pool.with(ItemEntry.builder(ModItems.GLIMMERSTEP));
                if(loot.isEnableGrandfrost()) pool.with(ItemEntry.builder(ModItems.GRANDFROST));
                if(loot.isEnableGreatSlither()) pool.with(ItemEntry.builder(ModItems.GREAT_SLITHER));
                if(loot.isEnableJesterPenetrate() && loot.isEnableJokeUniqueBossDrops()) pool.with(ItemEntry.builder(ModItems.JESTER_PENETRATE));
                if(loot.isEnableMimicry()) pool.with(ItemEntry.builder(ModItems.MIMICRY));
                if(loot.isEnableScarabRoller()) pool.with(ItemEntry.builder(ModItems.SCARAB_ROLLER));
                if(loot.isEnableMoltenFlare()) pool.with(ItemEntry.builder(ModItems.MOLTEN_FLARE));
                if(loot.isEnableTheBloodHarvester()) pool.with(ItemEntry.builder(ModItems.THEBLOODHARVESTER));
                if(loot.isEnableBlackPearl()) pool.with(ItemEntry.builder(ModItems.BLACK_PEARL));
                if(loot.isEnableThePan() && loot.isEnableJokeUniqueBossDrops()) pool.with(ItemEntry.builder(ModItems.THE_PAN));
                context.addPool(pool);
            }
        });

    }
}
