package net.rosemarythyme.simplymore.config;

import dev.architectury.platform.Platform;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedCondition;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.text.Text;

public class ConfigWrapper {
    public static final WeaponAttributesConfig ATTRIBUTES = ConfigApiJava.registerAndLoadConfig(WeaponAttributesConfig::new);
    public static final UniqueEffectConfig UNIQUE = ConfigApiJava.registerAndLoadConfig(UniqueEffectConfig::new);
    public static final ClientConfig CLIENT = ConfigApiJava.registerAndLoadConfig(ClientConfig::new);

    /// Edited from SimplySwords <code>WeaponAttributesConfig#createCondition</code>
    public static ValidatedCondition<Integer> modLoadedCondition(int defaultValue, String mod) {
        return new ValidatedInt(defaultValue)
                .toCondition(
                        () -> Platform.isModLoaded(mod),
                        Text.translatable("config.simplymore.condition.mod_not_loaded." + mod + ".description"),
                        () -> defaultValue
                ).withFailTitle(Text.translatable("config.simplymore.condition.mod_not_loaded." + mod + ".title"));
    }

    public static void register(){}
}
