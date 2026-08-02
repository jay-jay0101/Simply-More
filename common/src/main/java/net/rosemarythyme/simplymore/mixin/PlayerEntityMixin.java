package net.rosemarythyme.simplymore.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.item.uniques.CindergorgeItem;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {


	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void simplymore$applyDamage(DamageSource source, float amount, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		SimplyMoreHelperMethods.simplyMore$onDamageEffects(amount, source, info, player);

		if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CindergorgeItem || player.getStackInHand(Hand.OFF_HAND).getItem() instanceof CindergorgeItem ) {
			Entity attacker = source.getAttacker();
			if(attacker != null && MathUtils.chance(player, ConfigWrapper.unique.cindergorge.chance)) {
				if(attacker.isOnFire()) {
					attacker.damage(player.getDamageSources().onFire(), ConfigWrapper.unique.cindergorge.fireThornsDamage);
				} else {
					attacker.damage(player.getDamageSources().thorns(attacker), ConfigWrapper.unique.cindergorge.thornsDamage);
				}
			}
		}
	}
}