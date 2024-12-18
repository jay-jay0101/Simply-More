package net.rosemarythyme.simplymore.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.item.uniques.CindergorgeItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	private static final WrapperConfig config = ModConfigs.safeGetConfig();
	private static final UniqueEffectConfig effect = config.uniqueEffects;

	private final int stunTime = effect.getGrotesqueSolidifyAuraStunTime();

	@Inject(at = @At("RETURN"), method = "attack")
	private void simplyMore$attack(Entity target, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if (target instanceof PlayerEntity targetPlayer && targetPlayer.hasStatusEffect(ModEffectsRegistry.SOLIDIFIED)) {
			player.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED,stunTime),targetPlayer);
			targetPlayer.removeStatusEffect(ModEffectsRegistry.SOLIDIFIED);
			BladeOfTheGrotesqueItem.causeStun(targetPlayer);
		}
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void simplyMore$applyDamage(DamageSource source, float amount, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		SimplyMoreHelperMethods.simplyMore$onDamageEffects(amount, source, info, player);

		if(player.hasStatusEffect(ModEffectsRegistry.BLOOM)) {
			if(player.getStatusEffect(ModEffectsRegistry.BLOOM).getAmplifier() > 1) {
				StatusEffectInstance bloom = player.getStatusEffect(ModEffectsRegistry.BLOOM);
				player.removeStatusEffect(ModEffectsRegistry.BLOOM);
				player.addStatusEffect(new StatusEffectInstance(
						ModEffectsRegistry.BLOOM,
						bloom.getDuration(),
						bloom.getAmplifier() - 1
				));
			} else {
				player.removeStatusEffect(ModEffectsRegistry.BLOOM);
			}
		}

		if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CindergorgeItem || player.getStackInHand(Hand.OFF_HAND).getItem() instanceof CindergorgeItem ) {
			Entity attacker = source.getAttacker();
			if(player.getRandom().nextBetween(1, 100) <= effect.getCindergorgeThornsChance()) {
				if(attacker.isOnFire()) {
					attacker.damage(player.getDamageSources().onFire(), effect.getCindergorgeThornsFireDamage());
				} else {
					attacker.damage(player.getDamageSources().thorns(attacker), effect.getCindergorgeThornsDamage());
				}
			}
		}
	}

}