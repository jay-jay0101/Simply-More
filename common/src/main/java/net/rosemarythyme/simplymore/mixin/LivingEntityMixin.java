package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ImplicitRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.item.component.WeaponImplicitComponent;
import net.sweenus.simplyswords.registry.ComponentTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@ModifyReturnValue(at= @At("RETURN"), method = "disablesShield")
	private boolean simplymore$shouldBreakShields(boolean original) {
		WeaponImplicitComponent component = ((LivingEntity)(Object)this).getWeaponStack().get(ComponentTypeRegistry.WEAPON_IMPLICIT.get());
		return original || (component != null && component.implicitId() == ImplicitRegistry.GRANDSWORD.id());
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void simplymore$applyDamage(DamageSource source, float amount, CallbackInfo info) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		SimplyMoreHelperMethods.simplyMore$onDamageEffects(amount, source, info, livingEntity);
	}

	@Inject(at = @At("HEAD"), method = "heal", cancellable = true)
	private void simplymore$heal(float amount, CallbackInfo info) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if(livingEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED))) {
			float f = livingEntity.getHealth();
			if (f > 0.0F) {
				livingEntity.setHealth(f + amount/2);
			}
			info.cancel();
		}
	}

}