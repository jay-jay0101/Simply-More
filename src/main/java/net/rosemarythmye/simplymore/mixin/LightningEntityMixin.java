package net.rosemarythmye.simplymore.mixin;

import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.rosemarythmye.simplymore.item.ModItems;
import net.sweenus.simplyswords.api.SimplySwordsAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {
	@Inject(at = @At("HEAD"), method = "spawnFire")
	private void spawnFire(int spreadAttempts, CallbackInfo info) {
//		info.cancel();
	}
}