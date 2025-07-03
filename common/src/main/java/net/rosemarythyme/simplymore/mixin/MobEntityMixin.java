package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

	@ModifyReturnValue(at = @At("RETURN"), method = "tryAttack")
	private boolean simplyMore$tryAttack(boolean originalReturnValue, Entity target) {
		MobEntity mobEntity = (MobEntity) (Object) this;
		if (mobEntity.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.STUNNED)))
			return false;

        return originalReturnValue;
    }
}