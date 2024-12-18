package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;

public class MimicryEffect extends StatusEffect {

    public MimicryEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {

        if(amplifier != 12) {
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
        }

        if(livingEntity instanceof PlayerEntity player && !player.getWorld().isClient) {
            int duration = livingEntity.getStatusEffect(this).getDuration();
            int ticksUsed = MimicryItem.usageEffectTime - duration;

            MimicryItem item = ModItemsRegistry.MIMICRY_AMPLIFIERS.get(amplifier);

            item.usageTimeline(player, ticksUsed);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
