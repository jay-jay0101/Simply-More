package net.rosemarythyme.simplymore.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.sweenus.simplyswords.api.SimplySwordsAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimplySwordsAPI.class)
public abstract class UniqueSwordMixin {
	@Inject(at = @At("HEAD"), method = "onClickedGemSocketLogic")
	private static void onClicked(ItemStack stack, ItemStack otherStack, PlayerEntity player, CallbackInfo info) {
		String powerType = null;
		if (otherStack.isOf((Item) ModItemsRegistry.RUNEFUSED_CARVER) && stack.getOrCreateNbt().getString("runic_power").equals("no_socket"))
			powerType = "runic_power";
		else if (otherStack.isOf((Item) ModItemsRegistry.NETHERFUSED_CARVER) && stack.getOrCreateNbt().getString("nether_power").equals("no_socket"))
			powerType = "nether_power";
		if (powerType != null) {
			stack.getOrCreateNbt().putString(powerType, "socket_empty");
			player.getWorld().playSoundFromEntity((PlayerEntity) null, player, SoundEvents.BLOCK_ANVIL_USE, player.getSoundCategory(), 1.0F, 1.0F);
			otherStack.decrement(1);
		}
	}
}