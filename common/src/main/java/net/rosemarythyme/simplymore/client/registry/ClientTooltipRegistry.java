package net.rosemarythyme.simplymore.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.rosemarythyme.simplymore.client.tooltip.SimplyMoreTooltipProvider;
import net.rosemarythyme.simplymore.client.tooltip.motifs.CogMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.DeathMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.FullMotif;
import net.sweenus.simplytooltips.api.TooltipProviderRegistry;
import net.sweenus.simplytooltips.client.render.BorderRegistry;
import net.sweenus.simplytooltips.client.render.MotifRegistry;

public class ClientTooltipRegistry {
    public static final FullMotif COG = registerMotif(new CogMotif(), "cog");
    public static final FullMotif DEATH = registerMotif(new DeathMotif(), "death");

    @Environment(EnvType.CLIENT)
    public static void register() {
        MotifRegistry.register("cog", COG);
        MotifRegistry.register("death", DEATH);

        TooltipProviderRegistry.register(
                new SimplyMoreTooltipProvider(), 101);
    }

    public static FullMotif registerMotif(FullMotif motif, String key) {
        MotifRegistry.register(key, motif);
        BorderRegistry.register(key, motif.getPattern());

        return motif;
    }
}
