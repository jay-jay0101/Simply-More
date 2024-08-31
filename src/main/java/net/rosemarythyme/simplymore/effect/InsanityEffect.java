package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

public class InsanityEffect extends StatusEffect {

    public InsanityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        return SimplyMoreHelperMethods.simplyMore$addAttributeModifier(this, attribute, uuid, amount, operation);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        float newYaw = entity.getYaw() + (entity.getRandom().nextInt(21) - 10);
        float newPitch = entity.getPitch() + (entity.getRandom().nextInt(21) - 10);

        entity.setYaw(newYaw);
        entity.setPitch(newPitch);

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 3 == 0;
    }

}
