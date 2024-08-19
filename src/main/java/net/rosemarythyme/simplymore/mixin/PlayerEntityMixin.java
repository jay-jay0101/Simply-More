package net.rosemarythyme.simplymore.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Inject(at = @At("RETURN"), method = "attack")
	private void attack(Entity target, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if (target instanceof PlayerEntity targetPlayer && targetPlayer.hasStatusEffect(ModEffectsRegistry.SOLIDIFIED)) {
			player.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED,90),targetPlayer);

		}
	}

	@Inject(at = @At("HEAD"), method = "applyDamage", cancellable = true)
	private void applyDamage(DamageSource source, float amount, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		if(!player.isInvulnerableTo(source) && player.hasStatusEffect(ModEffectsRegistry.BLESSING)) {

			player.removeStatusEffect(ModEffectsRegistry.BLESSING);

			player.heal(4);


			player.getWorld().playSound(null,player.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
			((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.WAX_ON,player.getX(),player.getY()+1,player.getZ(),50,0.25,0.5,0.25,0.1);
			info.cancel();
			return;
		}

		if(!player.isInvulnerableTo(source) && player.hasStatusEffect(ModEffectsRegistry.CURSE)) {

			player.removeStatusEffect(ModEffectsRegistry.CURSE);

			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,100,3));
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,100,0));


			player.getWorld().playSound(null,player.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
			((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,player.getX(),player.getY()+1,player.getZ(),50,0.25,0.5,0.25,0.1);
		}
	}

}