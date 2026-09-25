package net.rosemarythyme.simplymore.fabric;

import net.fabricmc.api.ModInitializer;
import net.rosemarythyme.simplymore.SimplyMore;

public final class SimplyMoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SimplyMore.init();
    }
}
