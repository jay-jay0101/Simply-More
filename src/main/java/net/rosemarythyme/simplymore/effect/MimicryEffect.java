package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;

public class MimicryEffect extends StatusEffect {

    public MimicryEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity.getMainHandStack().getItem() instanceof MimicryItem))
            livingEntity.removeStatusEffect(this);
        super.applyUpdateEffect(livingEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
