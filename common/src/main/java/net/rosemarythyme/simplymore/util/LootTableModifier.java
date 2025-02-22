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
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_BACKHAND_BLADE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_DAGGER.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_DEER_HORNS.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_GRANDSWORD.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_GREAT_KATANA.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_GREAT_SPEAR.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_KHOPESH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_LANCE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_PERNACH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.IRON_QUARTERSTAFF.get()));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getGoldLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getGoldLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_BACKHAND_BLADE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_DAGGER.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_DEER_HORNS.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_GRANDSWORD.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_GREAT_KATANA.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_GREAT_SPEAR.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_KHOPESH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_LANCE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_PERNACH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.GOLD_QUARTERSTAFF.get()));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getDiamondLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getDiamondLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_BACKHAND_BLADE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_DAGGER.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_DEER_HORNS.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GRANDSWORD.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GREAT_KATANA.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GREAT_SPEAR.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_KHOPESH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_LANCE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_PERNACH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.DIAMOND_QUARTERSTAFF.get()));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getNetheriteLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getNetheriteLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_BACKHAND_BLADE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_DAGGER.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_DEER_HORNS.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_GRANDSWORD.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_GREAT_KATANA.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_GREAT_SPEAR.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_KHOPESH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_LANCE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_PERNACH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.NETHERITE_QUARTERSTAFF.get()));
                context.addPool(pool);
            }
        });

        LootEvent.MODIFY_LOOT_TABLE.register((lootTables, id, context, builtin) -> {
            if (loot.getRunicLootWeight() > 0 && id.getPath().contains("chests") && (loot.isEnableLootInVillages() || !id.getPath().contains("village"))) {
                LootPool.Builder pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F)).conditionally(RandomChanceLootCondition.builder((float)loot.getRunicLootWeight()))
                        .apply(EnchantRandomlyLootFunction.builder())
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_BACKHAND_BLADE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_DAGGER.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_DEER_HORNS.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_GRANDSWORD.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_GREAT_KATANA.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_GREAT_SPEAR.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_KHOPESH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_LANCE.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_PERNACH.get()))
                        .with(ItemEntry.builder(ModItemsRegistry.RUNIC_QUARTERSTAFF.get()));
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
        if (loot.isEnableBlackPearl()) pool.with(ItemEntry.builder(ModItemsRegistry.BLACK_PEARL.get()));
        if (loot.isEnableBladeOfTheGrotesque()) pool.with(ItemEntry.builder(ModItemsRegistry.BLADEOFTHEGROTESQUE.get()));
        if (loot.isEnableBoasFang()) pool.with(ItemEntry.builder(ModItemsRegistry.BOAS_FANG.get()));
        if (loot.isEnableEarthshatter()) pool.with(ItemEntry.builder(ModItemsRegistry.EARTHSHATTER.get()));
        if (loot.isEnableGlimmerstep()) pool.with(ItemEntry.builder(ModItemsRegistry.GLIMMERSTEP.get()));
        if (loot.isEnableGrandfrost()) pool.with(ItemEntry.builder(ModItemsRegistry.GRANDFROST.get()));
        if (loot.isEnableGreatSlither()) pool.with(ItemEntry.builder(ModItemsRegistry.GREAT_SLITHER.get()));
        if (loot.isEnableJesterPenetrate() && isAllowJokeUniques) pool.with(ItemEntry.builder(ModItemsRegistry.JESTER_PENETRATE.get()));
        if (loot.isEnableLustrousMoxie()) pool.with(ItemEntry.builder(ModItemsRegistry.LUSTROUS_MOXIE.get()));
        if (loot.isEnableMatterbane()) pool.with(ItemEntry.builder(ModItemsRegistry.MATTERBANE.get()));
        if (loot.isEnableMimicry()) pool.with(ItemEntry.builder(ModItemsRegistry.MIMICRY_LONGSWORD.get()));
        if (loot.isEnableMoltenFlare()) pool.with(ItemEntry.builder(ModItemsRegistry.MOLTEN_FLARE.get()));
        if (loot.isEnableRupturedIdol()) pool.with(ItemEntry.builder(ModItemsRegistry.RUPTURED_IDOL.get()));
        if (loot.isEnableRuyiJinguBang()) pool.with(ItemEntry.builder(ModItemsRegistry.RUYI_JINGU_BANG.get()));
        if (loot.isEnableMyrmedge()) pool.with(ItemEntry.builder(ModItemsRegistry.MYRMEDGE.get()));
        if (loot.isEnableSerpentineValour()) pool.with(ItemEntry.builder(ModItemsRegistry.SERPENTINE_VALOUR.get()));
        if (loot.isEnableSmoulderingRuin()) pool.with(ItemEntry.builder(ModItemsRegistry.SMOULDERING_RUIN.get()));
        if (loot.isEnableSoulForeseer()) pool.with(ItemEntry.builder(ModItemsRegistry.SOUL_FORESEER.get()));
        if (loot.isEnableStasis()) pool.with(ItemEntry.builder(ModItemsRegistry.STASIS.get()));
        if (loot.isEnableTheBloodHarvester()) pool.with(ItemEntry.builder(ModItemsRegistry.THEBLOODHARVESTER.get()));
        if (loot.isEnableThePan() && isAllowJokeUniques) pool.with(ItemEntry.builder(ModItemsRegistry.THE_PAN.get()));
        if (loot.isEnableTheVesselBreach()) pool.with(ItemEntry.builder(ModItemsRegistry.THEVESSELBREACH.get()));
        if (loot.isEnableTidebreaker()) pool.with(ItemEntry.builder(ModItemsRegistry.TIDEBREAKER.get()));
        if (loot.isEnableTimekeeper()) pool.with(ItemEntry.builder(ModItemsRegistry.TIMEKEEPER.get()));
        if (loot.isEnableVipersCall()) pool.with(ItemEntry.builder(ModItemsRegistry.VIPERSCALL.get()));
        if (loot.isEnableBrassturn()) pool.with(ItemEntry.builder(ModItemsRegistry.BRASSTURN.get()));
        if (loot.isEnableCindergorge()) pool.with(ItemEntry.builder(ModItemsRegistry.CINDERGORGE.get()));
        if (loot.isEnableDeathsEyrie()) pool.with(ItemEntry.builder(ModItemsRegistry.DEATHS_EYRIE.get()));
        if (loot.isEnablePerforiscus()) pool.with(ItemEntry.builder(ModItemsRegistry.PERFORISCUS.get()));
        if (loot.isEnableRevvengine()) pool.with(ItemEntry.builder(ModItemsRegistry.REVVENGINE.get()));
        if (loot.isEnableExedrill()) pool.with(ItemEntry.builder(ModItemsRegistry.EXEDRILL.get()));
        if (loot.isEnableCulterex()) pool.with(ItemEntry.builder(ModItemsRegistry.CULTEREX.get()));
        context.addPool(pool);
    }
}
