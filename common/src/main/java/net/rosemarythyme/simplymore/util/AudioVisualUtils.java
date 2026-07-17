package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.util.HelperMethods;

public class AudioVisualUtils {
    public static void handleFootfalls(Entity entity, ItemStack stack, World world, FootfallParticles particles) {
        HelperMethods.createFootfalls(entity, stack, world, particles.walkingParticle(), particles.sprintingParticle(), particles.passiveParticle(), true);
    }

    public static void particleRing(ServerWorld world, Vec3d center, ParticleEffect particleType, double radius, int particleCount) {
        HelperMethods.spawnOrbitParticles(world, center, particleType, radius, particleCount);
    }

    public static void particleAroundEntity(LivingEntity entity, ParticleEffect particleType, int count, double delta, float speed) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;

        world.spawnParticles(particleType, entity.getX(), entity.getY() + 1, entity.getZ(), count, delta, delta, delta, speed);
    }

    public static void playSound(World world, Vec3d pos, Sound sound) {
        world.playSound(null, pos.x, pos.y, pos.z, sound.event(), SoundCategory.PLAYERS, sound.volume(), sound.pitch());
    }
}
