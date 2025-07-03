package net.rosemarythyme.simplymore.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.item.uniques.CindergorgeItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	@Unique
	private static final UniqueEffectConfig simplyMore$effect = ConfigWrapper.unique;

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void simplyMore$applyDamage(DamageSource source, float amount, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		SimplyMoreHelperMethods.simplyMore$onDamageEffects(amount, source, info, player);

		if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CindergorgeItem || player.getStackInHand(Hand.OFF_HAND).getItem() instanceof CindergorgeItem ) {
			Entity attacker = source.getAttacker();
			if(attacker != null && player.getRandom().nextBetween(1, 100) <= simplyMore$effect.cindergorge.chance) {
				if(attacker.isOnFire()) {
					attacker.damage(player.getDamageSources().onFire(), simplyMore$effect.cindergorge.fireDamage);
				} else {
					attacker.damage(player.getDamageSources().thorns(attacker), simplyMore$effect.cindergorge.thornsDamage);
				}
			}
		}
	}

}