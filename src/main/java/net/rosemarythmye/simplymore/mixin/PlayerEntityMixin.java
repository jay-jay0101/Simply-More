package net.rosemarythmye.simplymore.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.rosemarythmye.simplymore.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Inject(at = @At("RETURN"), method = "attack")
	private void attack(Entity target, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if (target instanceof PlayerEntity targetPlayer && targetPlayer.hasStatusEffect(ModEffects.SOLIDIFIED)) {
			player.addStatusEffect(new StatusEffectInstance(ModEffects.STUNNED,90),targetPlayer);

		}
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void applyDamage(DamageSource source, float amount, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if(!player.isInvulnerableTo(source) && player.hasStatusEffect(ModEffects.BLESSING)) {

			player.removeStatusEffect(ModEffects.BLESSING);

			player.heal(4);


			player.getWorld().playSound(null,player.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
			((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.WAX_ON,player.getX(),player.getY()+1,player.getZ(),50,0.25,0.5,0.25,0.1);
			info.cancel();
			return;
		}

		if(!player.isInvulnerableTo(source) && player.hasStatusEffect(ModEffects.CURSE)) {

			player.removeStatusEffect(ModEffects.CURSE);

			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,100,3));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,100,0));


			player.getWorld().playSound(null,player.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
			((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,player.getX(),player.getY()+1,player.getZ(),50,0.25,0.5,0.25,0.1);
		}
	}

}