package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "tryAttack")
	private boolean tryAttack(boolean originalReturnValue, Entity target) {
		MobEntity mobEntity = (MobEntity) (Object) this;
		if(mobEntity.hasStatusEffect(ModEffectsRegistry.STUNNED))
			return false;
		if (originalReturnValue && target instanceof PlayerEntity player && player.hasStatusEffect(ModEffectsRegistry.SOLIDIFIED)) {
			mobEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED,90),player);
		}
        return originalReturnValue;
    }
}