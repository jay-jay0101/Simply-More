package net.rosemarythyme.simplymore.registry;

import net.rosemarythyme.simplymore.client.tooltip.motifs.BloodMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.DeathMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.CogMotif;
import net.sweenus.simplytooltips.client.render.MotifRegistry;

public class TooltipMotifRegistry {
    public static final CogMotif COG = new CogMotif();
    public static final DeathMotif DEATH = new DeathMotif();
    public static final BloodMotif BLOOD = new BloodMotif();

    public static void register() {
        MotifRegistry.register("cog", COG);
        MotifRegistry.register("death", DEATH);
        MotifRegistry.register("blood", BLOOD);
    }
}
