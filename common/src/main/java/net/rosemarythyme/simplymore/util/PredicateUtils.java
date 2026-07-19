package net.rosemarythyme.simplymore.util;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class PredicateUtils {
    public static final Predicate<StatusEffect> BENEFICIAL_EFFECT = StatusEffect::isBeneficial;
    public static final Predicate<StatusEffect> HARMFUL_EFFECT = (effect -> effect.getCategory() == StatusEffectCategory.HARMFUL);

    public static Predicate<StatusEffect> createForEffectBlacklist(ValidatedSet<Identifier> set, boolean shouldIncludeGlobal) {
        return (effect) -> !ConfigUtils.isEffectBlacklisted(effect, set, shouldIncludeGlobal);
    }
}
