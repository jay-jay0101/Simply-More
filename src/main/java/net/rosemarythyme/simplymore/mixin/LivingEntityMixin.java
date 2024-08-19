package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.item.normal.GrandSwordItem;
import net.rosemarythyme.simplymore.item.runics.RunicGrandSwordItem;
import net.rosemarythyme.simplymore.item.uniques.EarthshatterItem;
import net.rosemarythyme.simplymore.item.uniques.GrandfrostItem;
import net.rosemarythyme.simplymore.item.uniques.MoltenFlareItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.entity.LivingEntity.class)
public abstract class LivingEntityMixin {
	@ModifyReturnValue(at = @At("RETURN"), method = "disablesShield")
	private boolean disablesShield(boolean originalReturnValue) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		ItemStack mainHandStack = livingEntity.getEquippedStack(EquipmentSlot.MAINHAND);
		if (mainHandStack.getItem() instanceof EarthshatterItem || mainHandStack.getItem() instanceof RunicGrandSwordItem || mainHandStack.getItem() instanceof GrandSwordItem || mainHandStack.getItem() instanceof MoltenFlareItem || mainHandStack.getItem() instanceof GrandfrostItem) return true;
		return originalReturnValue;
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void applyDamage(DamageSource source, float amount, CallbackInfo info) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if(!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.BLESSING)) {

			livingEntity.removeStatusEffect(ModEffectsRegistry.BLESSING);

			livingEntity.heal(4);


			livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
			((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.WAX_ON,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
			info.cancel();
			return;
		}

		if(!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.CURSE)) {

			livingEntity.removeStatusEffect(ModEffectsRegistry.CURSE);

			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,100,3));
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,100,0));


			livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
			((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
		}
	}

}