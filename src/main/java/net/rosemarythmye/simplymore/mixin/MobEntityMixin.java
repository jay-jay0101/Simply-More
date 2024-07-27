package net.rosemarythmye.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythmye.simplymore.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "tryAttack")
	private boolean tryAttack(boolean originalReturnValue, Entity target) {
		MobEntity mobEntity = (MobEntity) (Object) this;
		if(mobEntity.hasStatusEffect(ModEffects.STUNNED)) return false;
		if (originalReturnValue && target instanceof PlayerEntity player && player.hasStatusEffect(ModEffects.SOLIDIFIED)) {
			mobEntity.addStatusEffect(new StatusEffectInstance(ModEffects.STUNNED,90),player);
		}
        return originalReturnValue;
    }
}