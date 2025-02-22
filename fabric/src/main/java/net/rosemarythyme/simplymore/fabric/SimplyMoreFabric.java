package net.rosemarythyme.simplymore.fabric;

import dev.architectury.platform.Platform;
import net.fabricmc.api.ModInitializer;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.fabric.compat.MythicMetalsCompat;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.registry.compat.MythicMetalsCompatProxy;

public final class SimplyMoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        if (Platform.isModLoaded("mythicmetals")) {
            // We run this first because otherwise init would register the items registrar too early
            MythicMetalsCompat.registerCompatItems();
        }

        SimplyMore.init();

        // This is handled separately on fabric and forge
        // to avoid a crash on forge
        ModItemsRegistry.registerItemGroup();
    }
}
