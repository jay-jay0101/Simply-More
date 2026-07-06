package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "disablesShield")
	private boolean simplyMore$disablesShield(boolean originalReturnValue) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		ItemStack mainHandStack = livingEntity.getEquippedStack(EquipmentSlot.MAINHAND);
		if (mainHandStack.getItem() instanceof Weapon weapon && weapon.getSwordType() == Weapon.SwordType.GRANDSWORD) return true;
		return originalReturnValue;
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void simplyMore$applyDamage(DamageSource source, float amount, CallbackInfo info) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		SimplyMoreHelperMethods.simplyMore$onDamageEffects(amount, source, info, livingEntity);
	}

	@Inject(at = @At("HEAD"), method = "heal", cancellable = true)
	private void simplyMore$heal(float amount, CallbackInfo info) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if(livingEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLEED))) {
			float f = livingEntity.getHealth();
			if (f > 0.0F) {
				livingEntity.setHealth(f + amount/2);
			}
			info.cancel();
		}
	}

}