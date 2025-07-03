package net.rosemarythyme.simplymore.forge;

import net.neoforged.fml.common.Mod;
import net.rosemarythyme.simplymore.SimplyMore;

@Mod(SimplyMore.ID)
public final class SimplyMoreForge {
    public SimplyMoreForge() {
        // Run our common setup.
        SimplyMore.init();
    }
}
