package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.rosemarythmye.simplymore.item.classes.uniques.Mimicry;

import java.util.List;
import java.util.UUID;

public class Harvest extends StatusEffect {


    public Harvest(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.getAttributeModifiers().put(attribute, entityAttributeModifier);
        return this;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        for (LivingEntity target : entity.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(entity.getX()-20,entity.getY()-20,entity.getZ()-20,entity.getX()+20,entity.getY()+20,entity.getZ()+20))) {
            if (target == entity) continue;
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40, 0), entity);
        }
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
