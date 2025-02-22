package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.rosemarythyme.simplymore.SimplyMore;

public class ModConfigs {
    public static void registerConfigs() {
        SimplyMore.LOGGER.info("Registering Config Files for " + SimplyMore.ID);
        safeGetConfig();
    }

    public static WrapperConfig safeGetConfig() {
        try {
            AutoConfig.register(WrapperConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        } catch (RuntimeException ignored) {}

        return AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    }
}
