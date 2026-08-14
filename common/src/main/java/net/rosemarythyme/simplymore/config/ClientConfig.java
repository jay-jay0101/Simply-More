package net.rosemarythyme.simplymore.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import net.rosemarythyme.simplymore.SimplyMore;
import org.jetbrains.annotations.NotNull;

public class ClientConfig extends Config {
    public ClientConfig() {
        super(SimplyMore.identifier("client"));
    }

    public ValidatedEnum<Screenshake> screenshake = new ValidatedEnum<>(
            Screenshake.ALL,
            ValidatedEnum.WidgetType.CYCLING
    );

    public ValidatedEnum<Flashbang> flashbang = new ValidatedEnum<>(
            Flashbang.LIGHT,
            ValidatedEnum.WidgetType.CYCLING
    );

    public enum Screenshake implements EnumTranslatable {
        ALL,
        SELF,
        OTHERS,
        NONE;

        @Override
        public @NotNull String prefix() {
            return "simplymore.client.screenshake";
        }
    }

    public enum Flashbang implements EnumTranslatable {
        LIGHT,
        DARK;

        @Override
        public @NotNull String prefix() {
            return "simplymore.client.flashbang";
        }
    }
}