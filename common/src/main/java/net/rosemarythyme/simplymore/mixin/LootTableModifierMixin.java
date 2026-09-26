package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.RegistryKey;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.sweenus.simplyswords.util.ModLootTableModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ModLootTableModifiers.class)
public class LootTableModifierMixin {

    @Inject(method = "lambda$init$2",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/architectury/event/events/common/LootEvent$LootTableModificationContext;addPool(Lnet/minecraft/loot/LootPool$Builder;)V"
            ))
    private static void simplymore$addCommonWeapons(RegistryKey<?> key, LootEvent.LootTableModificationContext context, boolean builtin, CallbackInfo ci, @Local LootPool.Builder pool) {
        simplyMore$addAllToPool(ItemRegistry.IRON_WEAPONS, pool);
        simplyMore$addAllToPool(ItemRegistry.GOLD_WEAPONS, pool);
    }

    @Inject(method = "lambda$init$3",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/architectury/event/events/common/LootEvent$LootTableModificationContext;addPool(Lnet/minecraft/loot/LootPool$Builder;)V"
            ))
    private static void simplymore$addRareWeapons(RegistryKey<?> key, LootEvent.LootTableModificationContext context, boolean builtin, CallbackInfo ci, @Local LootPool.Builder pool) {
        simplyMore$addAllToPool(ItemRegistry.DIAMOND_WEAPONS, pool);
    }

    @Unique
    private static void simplyMore$addAllToPool(List<RegistrySupplier<Item>> items, LootPool.Builder pool) {
        for (RegistrySupplier<Item> item : items) {
            pool.with(ItemEntry.builder(item.get()));
        }
    }
}
