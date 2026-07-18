package net.rosemarythyme.simplymore.util.data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.rosemarythyme.simplymore.util.EntityUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public record TargetList(List<LivingEntity> targets) {
    public TargetList {
        targets = List.copyOf(targets);
    }

    public TargetList(LivingEntity target) {
        this(List.of(target));
    }

    public TargetList filter(Predicate<LivingEntity> predicate) {
        return new TargetList(targets.stream().filter(predicate).toList());
    }

    public static TargetList empty() {
        return new TargetList(List.of());
    }

    public TargetList include(LivingEntity entity) {
        return include(new TargetList(entity));
    }

    public TargetList include(TargetList list) {
        return new TargetList(Stream.concat(targets.stream(), list.targets.stream()).toList());
    }

    public TargetList applyEffect(RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        return this.onEach((entity) -> entity.addStatusEffect(
                new StatusEffectInstance(effect, duration, amplifier))
        );
    }

    public TargetList addVelocity(double x, double y, double z) {
        return this.onEach((entity) -> {
            entity.addVelocity(x, y, z);
            entity.velocityModified = true;
        });
    }

    public TargetList incrementEffect(RegistryEntry<StatusEffect> effect, int duration, int amplifier, int maxAmplifier) {
        return this.onEach((entity) -> EntityUtils.reapplyAndIncrementEffect(
                entity, effect, duration, amplifier, maxAmplifier)
        );
    }

    public TargetList applyDurationDependantEffect(RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        return this.onEach((entity) -> {
            if (!entity.hasStatusEffect(effect)) entity.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier));
        });
    }

    public TargetList setOnFireFor(float seconds) {
        return this.onEach((entity) -> entity.setOnFireFor(seconds));
    }

    public TargetList damage(float amount, DamageSource source) {
        return this.onEach((entity -> entity.damage(source, amount)));
    }

    public TargetList onEach(Consumer<LivingEntity> action) {
        targets.forEach(action);

        return this;
    }

    public TargetList removeStatusEffects(Predicate<StatusEffect> predicate) {
        return this.onEach((entity) -> List.copyOf(entity.getStatusEffects()).forEach((effect) -> {
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
