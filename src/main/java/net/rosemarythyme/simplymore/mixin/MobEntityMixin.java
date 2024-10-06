package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

	private static final WrapperConfig config = ModConfigs.safeGetConfig();
	private static final UniqueEffectConfig effect = config.uniqueEffects;

	private final int stunTime = effect.getSolidifyAttackerStunTime();

	@ModifyReturnValue(at = @At("RETURN"), method = "tryAttack")
	private boolean simplyMore$tryAttack(boolean originalReturnValue, Entity target) {
		MobEntity mobEntity = (MobEntity) (Object) this;
		if (mobEntity.hasStatusEffect(ModEffectsRegistry.STUNNED))
			return false;
		if (originalReturnValue && target instanceof PlayerEntity player && player.hasStatusEffect(ModEffectsRegistry.SOLIDIFIED)) {
			mobEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.STUNNED,stunTime),player);
		}
        return originalReturnValue;
    }
}