package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

	@ModifyReturnValue(at = @At("RETURN"), method = "tryAttack")
	private boolean simplymore$tryAttack(boolean originalReturnValue, Entity target) {
		MobEntity mobEntity = (MobEntity) (Object) this;
		if (mobEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN))) return false;
		if (ActiveAbilityManager.SERVER.isDrilling(mobEntity)) return false;

        return originalReturnValue;
    }
}