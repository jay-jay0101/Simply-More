package net.rosemarythyme.simplymore.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythyme.simplymore.util.EntityUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@ModifyVariable(at = @At("HEAD"), method = "applyDamage", argsOnly = true, ordinal = 0)
	private float simplymore$modifyDamageTaken(float original, DamageSource source) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;

		return EntityUtils.modifyDamageTaken(livingEntity, source, original);
	}
}