package net.rosemarythmye.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythmye.simplymore.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Inject(at = @At("RETURN"), method = "attack")
	private void attack(Entity target, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if (target instanceof PlayerEntity targetPlayer && targetPlayer.hasStatusEffect(ModEffects.SOLIDIFIED)) {
			player.addStatusEffect(new StatusEffectInstance(ModEffects.PETRIFIED,90),targetPlayer);

		}
    }
}