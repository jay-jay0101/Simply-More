package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.rosemarythyme.simplymore.item.normal.GrandSwordItem;
import net.rosemarythyme.simplymore.item.runics.RunicGrandSwordItem;
import net.rosemarythyme.simplymore.item.uniques.EarthshatterItem;
import net.rosemarythyme.simplymore.item.uniques.GrandfrostItem;
import net.rosemarythyme.simplymore.item.uniques.MoltenFlareItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "disablesShield")
	private boolean disablesShield(boolean originalReturnValue) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		ItemStack mainHandStack = livingEntity.getEquippedStack(EquipmentSlot.MAINHAND);
		if (mainHandStack.getItem() instanceof EarthshatterItem || mainHandStack.getItem() instanceof RunicGrandSwordItem || mainHandStack.getItem() instanceof GrandSwordItem || mainHandStack.getItem() instanceof MoltenFlareItem || mainHandStack.getItem() instanceof GrandfrostItem) return true;
		return originalReturnValue;
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void applyDamage(DamageSource source, float amount, CallbackInfo info) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		SimplyMoreHelperMethods.simplyMore$applyBlessingOrCurse(source, info, livingEntity);
	}

}