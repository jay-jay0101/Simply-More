package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Box;

import java.util.UUID;

public class RadiantEffect extends StatusEffect {

    protected RadiantEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.getAttributeModifiers().put(attribute, entityAttributeModifier);
        return this;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,7,4));
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
