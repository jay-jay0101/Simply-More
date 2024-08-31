package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

public class RadiantEffect extends StatusEffect {

    public RadiantEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        return SimplyMoreHelperMethods.simplyMore$addAttributeModifier(this, attribute, uuid, amount, operation);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,7,4));
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
