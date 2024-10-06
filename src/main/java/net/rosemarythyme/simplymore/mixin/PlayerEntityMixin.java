package net.rosemarythyme.simplymore.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
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

	private final int stunTime = effect.getSolidifyAttackerStunTime();

	@Inject(at = @At("RETURN"), method = "attack")
	private void simplyMore$attack(Entity target, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if (target instanceof PlayerEntity targetPlayer && targetPlayer.hasStatusEffect(ModEffectsRegistry.SOLIDIFIED)) {
			player.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED,stunTime),targetPlayer);

		}
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void simplyMore$applyDamage(DamageSource source, float amount, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		SimplyMoreHelperMethods.simplyMore$applyBlessingOrCurse(amount, source, info, player);
	}

}