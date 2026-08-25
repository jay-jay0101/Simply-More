package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ImplicitRegistry;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.item.component.WeaponImplicitComponent;
import net.sweenus.simplyswords.registry.ComponentTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Shadow public abstract boolean damage(DamageSource source, float amount);

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

	@ModifyReturnValue(at = @At("RETURN"), method = "isInvulnerableTo")
	private boolean simplymore$isInvulnerable(boolean original, DamageSource damageSource) {
		LivingEntity entity = (LivingEntity) (Object) this;

		if(!damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) && ActiveAbilityManager.SERVER.isDrilling(entity)) {
			return true;
		}

		if(!damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) && ActiveAbilityManager.SERVER.isInAbility(entity, ActiveAbilityManager.Type.STATUE)) {
			Entity source = damageSource.getSource();
			Entity attacker = damageSource.getAttacker();
			if(attacker != null && source != null) {
				if (attacker.getUuid().equals(source.getUuid())) {
					BladeOfTheGrotesqueItem.causeStun(entity);
				}
			}

			return true;
		}

		return original;
	}

	@Inject(at = @At("HEAD"), method = "swimUpward", cancellable = true)
	private void simplymore$preventSwim(TagKey<Fluid> fluid, CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if(EntityUtils.isStunned(entity, true)) {
			ci.cancel();
		}
	}

	@ModifyVariable(at = @At("HEAD"), method = "travel", ordinal = 0, argsOnly = true)
	private Vec3d simplymore$preventTravel(Vec3d value) {
		LivingEntity entity = (LivingEntity) (Object) this;

		if(EntityUtils.isStunned(entity, true)) {
			return new Vec3d(0, value.getY() > 0 ? 0 : value.getY(), 0);
		}

		return value;
	}

	@Inject(at = @At("HEAD"), method = "jump", cancellable = true)
	private void simplymore$preventJump(CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;

		if(EntityUtils.isStunned(entity, true)) {
			ci.cancel();
		}
	}


	@ModifyReturnValue(at = @At("RETURN"), method = "isClimbing")
	private boolean simplymore$preventClimb(boolean original) {
		LivingEntity entity = (LivingEntity) (Object) this;

		return original && !EntityUtils.isStunned(entity, true);
	}
}