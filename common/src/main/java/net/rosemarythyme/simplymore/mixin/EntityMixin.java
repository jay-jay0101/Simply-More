package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.rosemarythyme.simplymore.util.EntityUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "isFireImmune")
	private boolean simplymore$isFireImmune(boolean original) {
		Entity entity = (Entity) (Object) this;
		if(entity instanceof LivingEntity livingEntity && EntityUtils.shouldOverrideFireImmunity(livingEntity)) {
			return false;
		}

		return original;
	}
}