package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.util.HelperMethods;

public class VisualEffectsUtils {
    public static void handleFootfalls(Entity entity, ItemStack stack, World world, FootfallParticles particles) {
        HelperMethods.createFootfalls(entity, stack, world, particles.walkingParticle(), particles.sprintingParticle(), particles.passiveParticle(), true);
    }

    public static void particleRing(ServerWorld world, Vec3d center, ParticleEffect particleType, double radius, int particleCount) {
        HelperMethods.spawnOrbitParticles(world, center, particleType, radius, particleCount);
    }
}
