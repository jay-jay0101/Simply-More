package net.rosemarythyme.simplymore.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.rosemarythyme.simplymore.client.SimplyMoreClientInit;

@Environment(EnvType.CLIENT)
public class SimplyMoreFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This is handled separately on fabric and forge
        // to avoid a crash on forge
        SimplyMoreClientInit.registerModelPredicates();
    }
}