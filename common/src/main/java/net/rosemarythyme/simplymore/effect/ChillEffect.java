package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ChillEffect extends StatusEffect {

    public ChillEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        int entityFrozenTicks = entity.getFrozenTicks();
        entity.inPowderSnow = true;
        entity.setFrozenTicks(Math.min(entity.getMinFreezeDamageTicks(), entityFrozenTicks + 1));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
