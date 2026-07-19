package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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

    public static void particleRing(ServerWorld world, Vec3d center, ParticleEffect particleType, double radius, int count) {
        HelperMethods.spawnOrbitParticles(world, center, particleType, radius, count);
    }

    public static void particleLine(ServerWorld world, Vec3d startPos, Vec3d endPos, ParticleEffect particleType, double spread, int count, double delta, double speed) {
        Vec3d directionalDelta = endPos.subtract(startPos);
        double burstCount = (int) Math.ceil(directionalDelta.length() / spread);
        Vec3d direction = directionalDelta.normalize();

        for (int i = 0; i <= burstCount; i++) {
            Vec3d pos = startPos.add(direction.multiply(i * spread));
            world.spawnParticles(particleType, pos.getX(), pos.getY(), pos.getZ(), count, delta, delta, delta, speed);
        }
    }

    public static void hitTarget(LivingEntity target) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10));
    }

    public static void particleLine(ServerWorld world, Vec3d startPos, float yaw, float pitch, double length, ParticleEffect particleType, double spread, int count, double delta, double speed) {
        Vec3d direction = MathUtils.getDirectionalVector(yaw, pitch);
        particleLine(world, startPos, startPos.add(direction.multiply(length)), particleType, spread, count, delta, speed);
    }

    public static void particleSquare(ServerWorld world, Vec3d center, ParticleEffect particleType, double horizontalRange, double verticalRange, int count, double speed) {
        world.spawnParticles(particleType, center.getX(), center.getY(), center.getZ(), count, horizontalRange, verticalRange, horizontalRange, speed);
    }

    public static void particleAroundEntity(LivingEntity entity, ParticleEffect particleType, int count, double delta, double speed) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;

        world.spawnParticles(particleType, entity.getX(), entity.getY() + 1, entity.getZ(), count, delta, delta, delta, speed);
    }

    public static void playSound(World world, Vec3d pos, Sound sound) {
        world.playSound(null, pos.x, pos.y, pos.z, sound.event(), SoundCategory.PLAYERS, sound.volume(), sound.pitch());
    }
}
