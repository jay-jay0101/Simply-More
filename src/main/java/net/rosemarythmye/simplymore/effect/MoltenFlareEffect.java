package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.rosemarythmye.simplymore.item.uniques.MoltenFlare;

import java.util.UUID;

public class MoltenFlareEffect extends StatusEffect {


    public MoltenFlareEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.getAttributeModifiers().put(attribute, entityAttributeModifier);
        return this;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        if(!(entity.getMainHandStack().getItem() instanceof MoltenFlare)) entity.removeStatusEffect(this);
       if (!(entity.getWorld().isClient())) ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY()+2, entity.getZ(), 3, 0.5, 0.5, 0.5, 0);
       super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
