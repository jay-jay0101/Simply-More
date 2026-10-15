package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.MobLunacyManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

	@ModifyReturnValue(at = @At("RETURN"), method = "tryAttack")
	private boolean simplymore$tryAttack(boolean originalReturnValue, Entity target) {
		MobEntity mobEntity = (MobEntity) (Object) this;
		if (mobEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN))) return false;
		if (ActiveAbilityManager.SERVER.isDrilling(mobEntity)) return false;
		if (EntityUtils.isStunned(mobEntity)) return false;

        return originalReturnValue;
    }

	@Inject(method = "tick", at=@At("TAIL"))
	private void simplymore$preventTarget(CallbackInfo ci) {
		MobEntity entity = (MobEntity) (Object) this;

		if(entity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.DAZZLED))) {
			simplymore$updateTarget(entity, null);
			return;
		}

		MobLunacyManager.MobLunacyData lunacy = MobLunacyManager.get(entity);
		if(lunacy != null) {
			simplymore$updateTarget(entity, lunacy.target().orElse(null));
		}
	}

	@ModifyReturnValue(method = "getTarget", at=@At("RETURN"))
	private LivingEntity simplymore$preventNewTarget(LivingEntity original) {
		MobEntity entity = (MobEntity) (Object) this;

		if(entity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.DAZZLED))) {
			simplymore$updateTarget(entity, null);
			return null;
		}

		MobLunacyManager.MobLunacyData lunacy = MobLunacyManager.get(entity);
		if(lunacy != null && lunacy.target().isEmpty()) {
			simplymore$updateTarget(entity, null);
			return null;
		}

		return original;
	}

	@Unique
	private void simplymore$updateTarget(MobEntity entity, LivingEntity target) {
		entity.setTarget(target);
		entity.setAttacking(target != null);
	}
}