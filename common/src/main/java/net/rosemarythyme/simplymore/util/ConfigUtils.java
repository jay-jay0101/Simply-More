package net.rosemarythyme.simplymore.util;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.config.ConfigWrapper;

public class ConfigUtils {
    public static ValidatedSet<Identifier> createEffectList(Identifier... defaults) {
        return ValidatedIdentifier.ofSuppliedList(Identifier.ofVanilla("absorption"), () -> Registries.STATUS_EFFECT.getEntrySet().stream().map(
                (entry) -> Registries.STATUS_EFFECT.getId(entry.getValue())
        ).toList()).toSet(defaults);
    }

    public static boolean isEffectBlacklisted(StatusEffect effect, ValidatedSet<Identifier> set, boolean shouldIncludeGlobal) {
        Identifier effectId = Registries.STATUS_EFFECT.getId(effect);

        if (shouldIncludeGlobal) {
            if(ConfigWrapper.unique.globalBlacklist.contains(effectId)) return true;
        }

        return set.contains(effectId);
    }
}
