package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.rosemarythyme.simplymore.item.uniques.MyrmedgeItem;

public class MyrmedgeEffect extends SolidifyEffect {


    public MyrmedgeEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity.getMainHandStack().getItem() instanceof MyrmedgeItem)
        && !(livingEntity.getOffHandStack().getItem() instanceof MyrmedgeItem)) {
            livingEntity.removeStatusEffect(this);
            return;
        }

        if(livingEntity.age < 20) {
            livingEntity.removeStatusEffect(this);
        }

        super.applyUpdateEffect(livingEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
