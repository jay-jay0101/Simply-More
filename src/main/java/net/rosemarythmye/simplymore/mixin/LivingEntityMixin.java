package net.rosemarythmye.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.rosemarythmye.simplymore.item.itemclasses.normal.GrandSword;
import net.rosemarythmye.simplymore.item.itemclasses.runics.RunicGrandSword;
import net.rosemarythmye.simplymore.item.itemclasses.uniques.Grandfrost;
import net.rosemarythmye.simplymore.item.itemclasses.uniques.MoltenFlare;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraft.entity.LivingEntity.class)
public abstract class LivingEntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "disablesShield")
	private boolean disablesShield(boolean originalReturnValue) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		ItemStack mainHandStack = livingEntity.getEquippedStack(EquipmentSlot.MAINHAND);
		if (mainHandStack.getItem() instanceof RunicGrandSword || mainHandStack.getItem() instanceof GrandSword || mainHandStack.getItem() instanceof MoltenFlare || mainHandStack.getItem() instanceof Grandfrost) return true;
		return originalReturnValue;
	}
}