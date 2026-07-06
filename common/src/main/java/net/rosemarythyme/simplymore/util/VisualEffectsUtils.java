package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.util.HelperMethods;

public class VisualEffectsUtils {
    public static void handleFootfalls(Entity entity, ItemStack stack, World world, FootfallParticles particles) {
        HelperMethods.createFootfalls(entity, stack, world, particles.walkingParticle(), particles.sprintingParticle(), particles.passiveParticle(), true);
    }
}
