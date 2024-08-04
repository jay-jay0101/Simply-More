package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.rosemarythmye.simplymore.item.uniques.Mimicry;

import java.util.UUID;

public class Insanity extends StatusEffect {


    public Insanity(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.getAttributeModifiers().put(attribute, entityAttributeModifier);
        return this;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        float yaw = entity.getYaw();
        float pitch = entity.getPitch();

        yaw += (entity.getRandom().nextInt(21)-10);
        pitch += (entity.getRandom().nextInt(21)-10);

        entity.setYaw(yaw);
        entity.setPitch(pitch);
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 3 == 0;
    }

}
