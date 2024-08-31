package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

public class GrotesqueEffect extends StatusEffect {

    public GrotesqueEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        return SimplyMoreHelperMethods.simplyMore$addAttributeModifier(this, attribute, uuid, amount, operation);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity.getMainHandStack().getItem() instanceof BladeOfTheGrotesqueItem))
            livingEntity.removeStatusEffect(this);
        super.applyUpdateEffect(livingEntity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
