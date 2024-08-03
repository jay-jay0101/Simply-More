package net.rosemarythmye.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.normal.GrandSword;
import net.rosemarythmye.simplymore.item.runics.RunicGrandSword;
import net.rosemarythmye.simplymore.item.uniques.Earthshatter;
import net.rosemarythmye.simplymore.item.uniques.Grandfrost;
import net.rosemarythmye.simplymore.item.uniques.MoltenFlare;
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
		if (mainHandStack.getItem() instanceof Earthshatter || mainHandStack.getItem() instanceof RunicGrandSword || mainHandStack.getItem() instanceof GrandSword || mainHandStack.getItem() instanceof MoltenFlare || mainHandStack.getItem() instanceof Grandfrost) return true;
		return originalReturnValue;
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void applyDamage(DamageSource source, float amount, CallbackInfo info) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if(!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffects.BLESSING)) {

			livingEntity.removeStatusEffect(ModEffects.BLESSING);

			livingEntity.heal(4);


			livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
			((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.WAX_ON,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
			info.cancel();
			return;
		}

		if(!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffects.CURSE)) {

			livingEntity.removeStatusEffect(ModEffects.CURSE);

			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,100,3));
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,100,0));


			livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
			((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
		}
	}

}