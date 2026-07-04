package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.world.World;
import net.sweenus.simplyswords.util.HelperMethods;

public class VisualEffectsUtils {
    public static void handleFootfalls(Entity entity, ItemStack stack, World world, SimpleParticleType particleEffect) {
        handleFootfalls(entity, stack, world, particleEffect, particleEffect, particleEffect);
    }

    public static void handleFootfalls(Entity entity, ItemStack stack, World world, SimpleParticleType particleEffect, SimpleParticleType sprintParticleEffect, SimpleParticleType passiveParticleEffect) {
        HelperMethods.createFootfalls(entity, stack, world, particleEffect, sprintParticleEffect, passiveParticleEffect, true);
    }
}
