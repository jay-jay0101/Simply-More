package net.rosemarythyme.simplymore.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.rosemarythyme.simplymore.SimplyMore;

public class ModConfigs {
    public static void registerConfigs() {
        SimplyMore.LOGGER.info("Registering Config Files for " + SimplyMore.ID);
        AutoConfig.register(WrapperConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
    }
}
