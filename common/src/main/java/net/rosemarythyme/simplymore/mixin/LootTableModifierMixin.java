package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.RegistryKey;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.sweenus.simplyswords.util.ModLootTableModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.stream.Collectors;

@Mixin(ModLootTableModifiers.class)
public class LootTableModifierMixin {

    @Inject(method = "lambda$init$2",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/architectury/event/events/common/LootEvent$LootTableModificationContext;addPool(Lnet/minecraft/loot/LootPool$Builder;)V"
            ))
    private static void simplyMore$addCommonWeapons(RegistryKey<?> key, LootEvent.LootTableModificationContext context, boolean builtin, CallbackInfo ci, @Local LootPool.Builder pool) {
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_BACKHAND_BLADE.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_DAGGER.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_DEER_HORNS.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_GRANDSWORD.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_KHOPESH.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_GREAT_KATANA.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_GREAT_SPEAR.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_LANCE.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_QUARTERSTAFF.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.IRON_PERNACH.get()));

        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_BACKHAND_BLADE.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_DAGGER.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_DEER_HORNS.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_GRANDSWORD.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_KHOPESH.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_GREAT_KATANA.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_GREAT_SPEAR.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_LANCE.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_QUARTERSTAFF.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.GOLD_PERNACH.get()));
    }

    @Inject(method = "lambda$init$3",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/architectury/event/events/common/LootEvent$LootTableModificationContext;addPool(Lnet/minecraft/loot/LootPool$Builder;)V"
            ))
    private static void simplyMore$addRareWeapons(RegistryKey<?> key, LootEvent.LootTableModificationContext context, boolean builtin, CallbackInfo ci, @Local LootPool.Builder pool) {
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_BACKHAND_BLADE.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_DAGGER.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_DEER_HORNS.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GRANDSWORD.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_KHOPESH.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GREAT_KATANA.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_GREAT_SPEAR.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_LANCE.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_QUARTERSTAFF.get()));
        pool.with(ItemEntry.builder(ModItemsRegistry.DIAMOND_PERNACH.get()));
    }

    @Unique
    private static final Set<RegistrySupplier<? extends Item>> simplyMore$lootableSuppliers = Set.of(
            ModItemsRegistry.GREAT_SLITHER,
            ModItemsRegistry.MOLTEN_FLARE,
            ModItemsRegistry.GRANDFROST,
            ModItemsRegistry.MIMICRY_LONGSWORD,
            ModItemsRegistry.GLIMMERSTEP,
            ModItemsRegistry.THE_BLOOD_HARVESTER,
            ModItemsRegistry.MYRMEDGE,
            ModItemsRegistry.BLACK_PEARL,
            ModItemsRegistry.THE_VESSEL_BREACH,
            ModItemsRegistry.BLADE_OF_THE_GROTESQUE,
            ModItemsRegistry.VIPERS_CALL,
            ModItemsRegistry.TIMEKEEPER,
            ModItemsRegistry.MATTERBANE,
            ModItemsRegistry.SMOULDERING_RUIN,
            ModItemsRegistry.STASIS,
            ModItemsRegistry.TIDEBREAKER,
            ModItemsRegistry.RUYI_JINGU_BANG,
            ModItemsRegistry.RUPTURED_IDOL,
            ModItemsRegistry.BOAS_FANG,
            ModItemsRegistry.EARTHSHATTER,
            ModItemsRegistry.SOUL_FORESEER,
            ModItemsRegistry.SERPENTINE_VALOUR,
            ModItemsRegistry.LUSTROUS_MOXIE,
            ModItemsRegistry.BRASSTURN,
            ModItemsRegistry.CINDERGORGE,
            ModItemsRegistry.DEATHS_EYRIE,
            ModItemsRegistry.PERFORISCUS,
            ModItemsRegistry.REVVENGINE,
            ModItemsRegistry.EXEDRILL,
            ModItemsRegistry.CULTEREX
    );
    @Unique
    private static Set<Item> simplyMore$lootableItems = Set.of();

    @ModifyReturnValue(method="isLootableUnique", at=@At("RETURN"))
    private static boolean simplyMore$isLootableUnique(boolean original, Item item) {
        if (simplyMore$lootableItems.isEmpty()) {
            simplyMore$lootableItems = simplyMore$lootableSuppliers.stream().map(java.util.function.Supplier::get).collect(Collectors.toSet());
        }

        return original || simplyMore$lootableItems.contains(item.asItem());
    }
}
