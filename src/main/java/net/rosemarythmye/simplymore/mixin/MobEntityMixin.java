package net.rosemarythmye.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.architectury.platform.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.normal.GrandSword;
import net.rosemarythmye.simplymore.item.runics.RunicGrandSword;
import net.rosemarythmye.simplymore.item.uniques.Grandfrost;
import net.rosemarythmye.simplymore.item.uniques.MoltenFlare;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "tryAttack")
	private boolean tryAttack(boolean originalReturnValue, Entity target) {
		MobEntity mobEntity = (MobEntity) (Object) this;
		if(mobEntity.hasStatusEffect(ModEffects.PETRIFIED)) return false;
		if (originalReturnValue && target instanceof PlayerEntity player && player.hasStatusEffect(ModEffects.SOLIDIFIED)) {
			mobEntity.addStatusEffect(new StatusEffectInstance(ModEffects.PETRIFIED,90),player);
		}
        return originalReturnValue;
    }
}