//package net.rosemarythyme.simplymore.mixin;
//
//import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//import com.llamalad7.mixinextras.sugar.Local;
//import dev.architectury.event.events.common.LootEvent;
//import dev.architectury.registry.registries.RegistrySupplier;
//import net.minecraft.item.Item;
//import net.minecraft.loot.LootPool;
//import net.minecraft.loot.entry.ItemEntry;
//import net.minecraft.registry.RegistryKey;
//import net.rosemarythyme.simplymore.registry.ItemRegistry;
//import net.sweenus.simplyswords.util.ModLootTableModifiers;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Unique;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Mixin(ModLootTableModifiers.class)
//public class LootTableModifierMixin {
//
//    @Inject(method = "lambda$init$2",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Ldev/architectury/event/events/common/LootEvent$LootTableModificationContext;addPool(Lnet/minecraft/loot/LootPool$Builder;)V"
//            ))
//    private static void simplyMore$addCommonWeapons(RegistryKey<?> key, LootEvent.LootTableModificationContext context, boolean builtin, CallbackInfo ci, @Local LootPool.Builder pool) {
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_BACKHAND_BLADE.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_DAGGER.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_DEER_HORNS.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_GRANDSWORD.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_KHOPESH.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_GREAT_KATANA.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_GREAT_SPEAR.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_LANCE.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_QUARTERSTAFF.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.IRON_PERNACH.get()));
//
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_BACKHAND_BLADE.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_DAGGER.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_DEER_HORNS.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_GRANDSWORD.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_KHOPESH.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_GREAT_KATANA.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_GREAT_SPEAR.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_LANCE.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_QUARTERSTAFF.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.GOLD_PERNACH.get()));
//    }
//
//    @Inject(method = "lambda$init$3",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Ldev/architectury/event/events/common/LootEvent$LootTableModificationContext;addPool(Lnet/minecraft/loot/LootPool$Builder;)V"
//            ))
//    private static void simplyMore$addRareWeapons(RegistryKey<?> key, LootEvent.LootTableModificationContext context, boolean builtin, CallbackInfo ci, @Local LootPool.Builder pool) {
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_BACKHAND_BLADE.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_DAGGER.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_DEER_HORNS.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_GRANDSWORD.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_KHOPESH.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_GREAT_KATANA.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_GREAT_SPEAR.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_LANCE.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_QUARTERSTAFF.get()));
//        pool.with(ItemEntry.builder(ItemRegistry.DIAMOND_PERNACH.get()));
//    }
//
//    @Unique
//    private static final Set<RegistrySupplier<? extends Item>> simplyMore$lootableSuppliers = Set.of(
//            ItemRegistry.GREAT_SLITHER,
//            ItemRegistry.MOLTEN_FLARE,
//            ItemRegistry.GRANDFROST,
//            ItemRegistry.MIMICRY_LONGSWORD,
//            ItemRegistry.GLIMMERSTEP,
//            ItemRegistry.THE_BLOOD_HARVESTER,
//            ItemRegistry.MYRMEDGE,
//            ItemRegistry.BLACK_PEARL,
//            ItemRegistry.THE_VESSEL_BREACH,
//            ItemRegistry.BLADE_OF_THE_GROTESQUE,
//            ItemRegistry.VIPERS_CALL,
//            ItemRegistry.TIMEKEEPER,
//            ItemRegistry.MATTERBANE,
//            ItemRegistry.SMOULDERING_RUIN,
//            ItemRegistry.STASIS,
//            ItemRegistry.TIDEBREAKER,
//            ItemRegistry.RUYI_JINGU_BANG,
//            ItemRegistry.RUPTURED_IDOL,
//            ItemRegistry.BOAS_FANG,
//            ItemRegistry.EARTHSHATTER,
//            ItemRegistry.SOUL_FORESEER,
//            ItemRegistry.SERPENTINE_VALOUR,
//            ItemRegistry.LUSTROUS_MOXIE,
//            ItemRegistry.BRASSTURN,
//            ItemRegistry.CINDERGORGE,
//            ItemRegistry.DEATHS_EYRIE,
//            ItemRegistry.PERFORISCUS,
//            ItemRegistry.REVVENGINE,
//            ItemRegistry.EXEDRILL,
//            ItemRegistry.CULTEREX
//    );
//    @Unique
//    private static Set<Item> simplyMore$lootableItems = Set.of();
//
//    @ModifyReturnValue(method="isLootableUnique", at=@At("RETURN"))
//    private static boolean simplyMore$isLootableUnique(boolean original, Item item) {
//        if (simplyMore$lootableItems.isEmpty()) {
//            simplyMore$lootableItems = simplyMore$lootableSuppliers.stream().map(java.util.function.Supplier::get).collect(Collectors.toSet());
//        }
//
//        return original || simplyMore$lootableItems.contains(item.asItem());
//    }
//}
