package net.rosemarythyme.simplymore.util.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record TargetList(Set<LivingEntity> targets) {
    public TargetList {
        targets = Set.copyOf(targets);
    }

    public TargetList(LivingEntity target) {
        this(Set.of(target));
    }

    public TargetList filter(Predicate<LivingEntity> predicate) {
        return new TargetList(targets.stream().filter(predicate).collect(Collectors.toSet()));
    }

    public TargetList filterByType(Class<? extends LivingEntity> clazz) {
        return this.filter(clazz::isInstance);
    }

    public TargetList filterByOwnedBy(LivingEntity owner) {
        return this.filter((entity) -> entity instanceof Ownable ownable && ownable.getOwner() == owner);
    }

    public TargetList kill() {
        return this.onEach(LivingEntity::kill);
    }

    public TargetList discard() {
        return this.onEach(LivingEntity::discard);
    }

    public boolean isEmpty() {
        return targets.isEmpty();
    }

    public int size() {
        return targets.size();
    }

    public boolean isPopulated() {
        return !isEmpty();
    }

    public TargetList filterByTargetType(LivingEntity attacker, AttackUtils.AttackTarget targetType) {
        return this.filter((entity) -> AttackUtils.canTarget(attacker, entity, targetType));
    }

    public static TargetList empty() {
        return new TargetList(Set.of());
    }

    public TargetList include(LivingEntity target) {
        return include(new TargetList(target));
    }

    public TargetList exclude(TargetList target) {
        return this.filter((entity) -> !target.targets.contains(entity));
    }

    public TargetList exclude(LivingEntity target) {
        return exclude(new TargetList(target));
    }

    public TargetList include(Entity target) {
        if(!(target instanceof LivingEntity livingTarget)) return this;
        return include(new TargetList(livingTarget));
    }

    public TargetList include(TargetList list) {
        return new TargetList(Stream.concat(targets.stream(), list.targets.stream()).collect(Collectors.toSet()));
    }

    public TargetList applyEffect(RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        return this.onEach((target) -> target.addStatusEffect(
                new StatusEffectInstance(effect, duration, amplifier))
        );
    }
    public TargetList targetIndicator() {
        return this.onEach(AudioVisualUtils::targetIndicator);
    }

    public TargetList addVelocity(double x, double y, double z) {
        return this.onEach((target) -> {
            target.addVelocity(x, y, z);
            target.velocityModified = true;
        });
    }

    public TargetList knockback(LivingEntity attacker, double strength) {
        return this.onEach((target) -> AttackUtils.knockback(attacker, target, strength));
    }

    public TargetList knockback(Vec3d pos, double strength) {
        return this.onEach((target) -> AttackUtils.knockback(pos, target, strength));
    }

    public TargetList pull(Vec3d pos, double strength) {
        return this.onEach((target) -> AttackUtils.knockback(pos, target, -strength));
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

    public TargetList forceDamage(float amount, DamageSource source) {
        return this.onEach((entity -> AttackUtils.applyExtraDamage(entity, amount, source)));
    }

    public TargetList onEach(Consumer<LivingEntity> action) {
        targets.forEach(action);
        return this;
    }

    public TargetList onFirst(Consumer<LivingEntity> action) {
        Optional<LivingEntity> entity = targets.stream().findFirst();
        if(entity.isEmpty()) return this;

        action.accept(entity.get());

        return this;
    }

    public TargetList onEachEnumerated(BiConsumer<Integer, LivingEntity> action) {
        List<LivingEntity> targetList = List.copyOf(targets);

        for (int i = 0; i < size(); i++) {
            action.accept(i, targetList.get(i));
        }

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

    public TargetList addDurationToStatusEffect(Predicate<StatusEffect> predicate, int duration) {
        return this.onEach((entity) -> List.copyOf(entity.getStatusEffects()).forEach((effect) -> {
                    if (predicate.test(effect.getEffectType().value())) {
                        entity.addStatusEffect(new StatusEffectInstance(effect.getEffectType(), effect.getDuration() + duration, effect.getAmplifier()));
                    }
                }
        ));
    }

    public TargetList removeStatusEffect(RegistryEntry<StatusEffect> effect) {
        return this.onEach((entity) -> entity.removeStatusEffect(effect));
    }

    public TargetList removeStatusEffects() {
        return this.removeStatusEffects((effect) -> true);
    }
}
