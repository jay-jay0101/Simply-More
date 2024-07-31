package net.rosemarythmye.simplymore.effect;

import net.minecraft.client.render.entity.HuskEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;

import java.util.UUID;

public class TidebreakerEffect extends StatusEffect {


    public TidebreakerEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.getAttributeModifiers().put(attribute, entityAttributeModifier);
        return this;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        if (!entity.getWorld().isClient) {
            ServerWorld world = ((ServerWorld) entity.getWorld());
            world.spawnParticles(ParticleTypes.CLOUD, entity.getX(),entity.getY()+5,entity.getZ(),100,3,0,3,0.05);
            world.spawnParticles(ParticleTypes.FALLING_WATER, entity.getX(),entity.getY()+5,entity.getZ(),100,3,0,3,0);
            if(world.getTime() % 5 == 0) world.playSound(null,entity.getX(),entity.getY(),entity.getZ(), SoundEvents.WEATHER_RAIN, SoundCategory.PLAYERS,1,1);
            for (LivingEntity target : entity.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(entity.getX()-3,entity.getY()-2,entity.getZ()-3,entity.getX()+3,entity.getY()+8,entity.getZ()+3))) {
                if (target == entity || target.isTeammate(entity)) continue;
                target.addStatusEffect(new StatusEffectInstance(ModEffects.INSANITY, 160, 0), entity);
            }
        }


        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
