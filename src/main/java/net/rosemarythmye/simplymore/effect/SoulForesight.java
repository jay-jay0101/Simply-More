package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.UUID;

public class SoulForesight extends StatusEffect {

    protected SoulForesight(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.getAttributeModifiers().put(attribute, entityAttributeModifier);
        return this;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            if (effect.getEffectType().getCategory() != StatusEffectCategory.BENEFICIAL || effect.getDuration()<=5) continue;
            entity.setStatusEffect(new StatusEffectInstance(effect.getEffectType(),effect.getDuration()-4,effect.getAmplifier()),entity);
        }
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
