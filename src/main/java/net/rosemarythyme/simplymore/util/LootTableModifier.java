package net.rosemarythyme.simplymore.util;

import dev.architectury.event.events.common.LootEvent;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.rosemarythyme.simplymore.config.LootConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;

public class LootTableModifier {
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    static LootConfig loot = config.loot;

    public static void registerLootTableChanges() {
        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getIronLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getIronLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_DAGGER))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_DEER_HORNS))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_GRANDSWORD))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_KHOPESH))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_LANCE))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_PERNACH))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_QUARTERSTAFF));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getGoldLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getGoldLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_DAGGER))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_DEER_HORNS))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_GRANDSWORD))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_KHOPESH))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_LANCE))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_PERNACH))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_QUARTERSTAFF));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getDiamondLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getDiamondLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_DAGGER))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_DEER_HORNS))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GRANDSWORD))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_KHOPESH))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_LANCE))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_PERNACH))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_QUARTERSTAFF));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getNetheriteLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getNetheriteLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_DAGGER))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_DEER_HORNS))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_GRANDSWORD))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_KHOPESH))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_LANCE))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_PERNACH))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_QUARTERSTAFF));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getRunicLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getRunicLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_DAGGER))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_DEER_HORNS))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_GRANDSWORD))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_KHOPESH))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_LANCE))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_PERNACH))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_QUARTERSTAFF));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getUniqueLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getUniqueLootWeight()));
                lootTableContents(context, pool,loot.isEnableJokeUniqueChestLoot());
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getWitherUniqueWeight() > 0 && id.getPath().equals("entities/wither")) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float) loot.getWitherUniqueWeight()));
                lootTableContents(context, pool, loot.isEnableJokeUniqueBossDrops());
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getWitherUniqueWeight() > 0 && id.getPath().equals("entities/ender_dragon")) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float) loot.getEnderDragonUniqueWeight()));
                lootTableContents(context, pool, loot.isEnableJokeUniqueBossDrops());
            }
        });

    }

    private static void lootTableContents(LootEvent.LootTableModificationContext context, LootPool.Builder pool, boolean isAllowJokeUniques) {
        if (loot.isEnableBlackPearl()) pool.with(ItemEntry.builder(ModItemsRegistry.BLACK_PEARL));
        if (loot.isEnableBladeOfTheGrotesque()) pool.with(ItemEntry.builder(ModItemsRegistry.BLADEOFTHEGROTESQUE));
        if (loot.isEnableBoasFang()) pool.with(ItemEntry.builder(ModItemsRegistry.BOAS_FANG));
        if (loot.isEnableEarthshatter()) pool.with(ItemEntry.builder(ModItemsRegistry.EARTHSHATTER));
        if (loot.isEnableGlimmerstep()) pool.with(ItemEntry.builder(ModItemsRegistry.GLIMMERSTEP));
        if (loot.isEnableGrandfrost()) pool.with(ItemEntry.builder(ModItemsRegistry.GRANDFROST));
        if (loot.isEnableGreatSlither()) pool.with(ItemEntry.builder(ModItemsRegistry.GREAT_SLITHER));
        if (loot.isEnableJesterPenetrate() && isAllowJokeUniques) pool.with(ItemEntry.builder(ModItemsRegistry.JESTER_PENETRATE));
        if (loot.isEnableLustrousMoxie()) pool.with(ItemEntry.builder(ModItemsRegistry.LUSTROUS_MOXIE));
        if (loot.isEnableMatterbane()) pool.with(ItemEntry.builder(ModItemsRegistry.MATTERBANE));
        if (loot.isEnableMimicry()) pool.with(ItemEntry.builder(ModItemsRegistry.MIMICRY));
        if (loot.isEnableMoltenFlare()) pool.with(ItemEntry.builder(ModItemsRegistry.MOLTEN_FLARE));
        if (loot.isEnableRupturedIdol()) pool.with(ItemEntry.builder(ModItemsRegistry.RUPTURED_IDOL));
        if (loot.isEnableRuyiJinguBang()) pool.with(ItemEntry.builder(ModItemsRegistry.RUYI_JINGU_BANG));
        if (loot.isEnableScarabRoller()) pool.with(ItemEntry.builder(ModItemsRegistry.SCARAB_ROLLER));
        if (loot.isEnableSerpentineValour()) pool.with(ItemEntry.builder(ModItemsRegistry.SERPENTINE_VALOUR));
        if (loot.isEnableSmoulderingRuin()) pool.with(ItemEntry.builder(ModItemsRegistry.SMOULDERING_RUIN));
        if (loot.isEnableSoulForeseer()) pool.with(ItemEntry.builder(ModItemsRegistry.SOUL_FORESEER));
        if (loot.isEnableStasis()) pool.with(ItemEntry.builder(ModItemsRegistry.STASIS));
        if (loot.isEnableTheBloodHarvester()) pool.with(ItemEntry.builder(ModItemsRegistry.THEBLOODHARVESTER));
        if (loot.isEnableThePan() && isAllowJokeUniques) pool.with(ItemEntry.builder(ModItemsRegistry.THE_PAN));
        if (loot.isEnableTheVesselBreach()) pool.with(ItemEntry.builder(ModItemsRegistry.THEVESSELBREACH));
        if (loot.isEnableTidebreaker()) pool.with(ItemEntry.builder(ModItemsRegistry.TIDEBREAKER));
        if (loot.isEnableTimekeeper()) pool.with(ItemEntry.builder(ModItemsRegistry.TIMEKEEPER));
        if (loot.isEnableVipersCall()) pool.with(ItemEntry.builder(ModItemsRegistry.VIPERSCALL));
        context.addPool(pool);
    }
}
