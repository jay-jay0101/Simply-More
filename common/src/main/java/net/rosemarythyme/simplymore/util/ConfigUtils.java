package net.rosemarythyme.simplymore.util;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.config.ConfigWrapper;

import java.util.Optional;

public class ConfigUtils {
    public static ValidatedSet<Identifier> createEffectList(Identifier... defaults) {
        return ValidatedIdentifier.ofSuppliedList(Identifier.ofVanilla("absorption"), () -> Registries.STATUS_EFFECT.getEntrySet().stream().map(
                (entry) -> Registries.STATUS_EFFECT.getId(entry.getValue())
        ).toList()).toSet(defaults);
    }

    public static boolean isEffectBlacklisted(RegistryEntry<StatusEffect> effect, ValidatedSet<Identifier> set, boolean shouldIncludeGlobal) {
        Optional<Identifier> effectId = effect.getKey().map(RegistryKey::getValue);
        if(effectId.isEmpty()) return false;

        if (shouldIncludeGlobal) {
            if(ConfigWrapper.unique.globalBlacklist.contains(effectId.get())) return true;
        }

        return set.contains(effectId.get());
    }
}
