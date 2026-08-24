package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class VenomPoisonEffect extends StatusEffect {

    public VenomPoisonEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 2.0F) {
            entity.damage(entity.getDamageSources().magic(), 2.0F);
        }
        else if (entity.getHealth() > 1.0F) {
            entity.damage(entity.getDamageSources().magic(), 1.0F);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 25 >> amplifier;
        return i == 0 || duration % i == 0;
    }


}
