package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;

public class MimicryEffect extends StatusEffect {

    public MimicryEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public boolean applyUpdateEffect(LivingEntity livingEntity, int amplifier) {

        livingEntity.addStatusEffect(
                new StatusEffectInstance(
                        StatusEffects.MINING_FATIGUE,
                        5,
                        255
                )
        );

        livingEntity.addStatusEffect(
                new StatusEffectInstance(
                        StatusEffects.SLOWNESS,
                        5,
                        2
                )
        );

        if(livingEntity instanceof PlayerEntity player && !player.getWorld().isClient()) {
            int duration = livingEntity.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING)).getDuration();
            int ticksUsed = AttackUtils.getUseTicksFromInfiniteDuration(duration);

//            MimicryItem item = (MimicryItem) ItemRegistry.MIMICRY_AMPLIFIERS.get(amplifier).get();
//
//            item.usageTimeline(player, ticksUsed);
        }

        return super.applyUpdateEffect(livingEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
