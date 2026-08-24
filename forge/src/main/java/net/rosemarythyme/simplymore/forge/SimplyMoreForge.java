package net.rosemarythyme.simplymore.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.rosemarythyme.simplymore.SimplyMore;

@Mod(SimplyMore.ID)
public final class SimplyMoreForge {
    public SimplyMoreForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(SimplyMore.ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        SimplyMore.init();

        // Brassturn Fix
        MinecraftForge.EVENT_BUS.addListener(EventHandler::onItemAttributeModifier);
    }
}
