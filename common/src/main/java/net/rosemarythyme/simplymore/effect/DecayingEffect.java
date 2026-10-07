package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.rosemarythyme.simplymore.registry.DamageTypeRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.data.TargetList;

public class DecayingEffect extends StatusEffect {
    public DecayingEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        int missingHealth = (int) Math.floor(entity.getMaxHealth() - entity.getHealth());
        clampHealth(entity, amplifier, missingHealth);
        clampAmplifier(entity, amplifier, missingHealth);
        return true;
    }

    public void clampHealth(LivingEntity entity, int amplifier, int missingHealth) {
        int damage = (amplifier + 1) - missingHealth;
        if(damage > 0) {
            entity.damage(DamageTypeRegistry.damageSourceOf(entity.getWorld(), DamageTypeRegistry.DECAY), damage);
        }
    }

    public void clampAmplifier(LivingEntity entity, int amplifier, int missingHealth) {
        int newAmplifier = Math.min(Math.max(amplifier, missingHealth - 1), 0xFF);

        RegistryEntry<StatusEffect> decaying = StatusEffectRegistry.getReference(StatusEffectRegistry.DECAYING);
        StatusEffectInstance instance = entity.getStatusEffect(decaying);

        if(instance == null) return;
        new TargetList(entity).applyEffect(decaying, instance.getDuration(),newAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
