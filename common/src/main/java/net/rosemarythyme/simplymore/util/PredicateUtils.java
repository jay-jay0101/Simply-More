package net.rosemarythyme.simplymore.util;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class PredicateUtils {
    public static final Predicate<StatusEffect> BENEFICIAL_EFFECT = StatusEffect::isBeneficial;
    public static final Predicate<StatusEffect> HARMFUL_EFFECT = (effect -> effect.getCategory() == StatusEffectCategory.HARMFUL);
    public static final Predicate<StatusEffect> IS_INSTANT = (StatusEffect::isInstant);

    public static Predicate<StatusEffect> createForEffectBlacklist(ValidatedSet<Identifier> set, boolean shouldIncludeGlobal) {
        return (effect) -> !ConfigUtils.isEffectBlacklisted(effect, set, shouldIncludeGlobal);
    }

    public static Predicate<Entity> createForTargetType(LivingEntity attacker, AttackUtils.AttackTarget target) {
        return (entity -> entity instanceof LivingEntity livingEntity && AttackUtils.canTarget(attacker, livingEntity, target));
    }
}
