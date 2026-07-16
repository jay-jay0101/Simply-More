package net.rosemarythyme.simplymore.util.data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public record TargetList(List<LivingEntity> targets) {
    public TargetList {
        targets = List.copyOf(targets);
    }

    public TargetList filter(Predicate<LivingEntity> predicate) {
        return new TargetList(targets.stream().filter(predicate).toList());
    }

    public static TargetList empty() {
        return new TargetList(List.of());
    }

    public TargetList applyEffect(RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        return this.forEach((entity) -> entity.addStatusEffect(
                new StatusEffectInstance(effect, duration, amplifier))
        );
    }

    public TargetList applyDurationDependantEffect(RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        return this.forEach((entity) -> {
            if (!entity.hasStatusEffect(effect)) entity.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
        });
    }

    public TargetList forEach(Consumer<LivingEntity> action) {
        targets.forEach(action);

        return this;
    }

    public TargetList removeStatusEffects(Predicate<StatusEffect> predicate) {
        return this.forEach((entity) -> List.copyOf(entity.getStatusEffects()).forEach((effect) -> {
                    if (predicate.test(effect.getEffectType().value())) {
                        entity.removeStatusEffect(effect.getEffectType());
                    }
                }
        ));
    }

    public TargetList removeStatusEffects() {
        return this.removeStatusEffects((effect) -> true);
    }
}
