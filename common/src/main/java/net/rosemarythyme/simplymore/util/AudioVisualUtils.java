package net.rosemarythyme.simplymore.util;

import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.networking.s2c.S2CParticleCylinderPacket;
import net.rosemarythyme.simplymore.networking.s2c.S2CScreenShakePacket;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class AudioVisualUtils {
    public static void applyScreenshake(ServerWorld world, Vec3d pos, LivingEntity attacker, double range, float intensity, int duration) {;
        List<ServerPlayerEntity> players = world.getPlayers().stream().filter((player) -> player.squaredDistanceTo(pos) < range * range && player != attacker).toList();
        NetworkManager.sendToPlayers(players, new S2CScreenShakePacket(intensity, duration, false));

        if(attacker instanceof ServerPlayerEntity player) {
            NetworkManager.sendToPlayer(player, new S2CScreenShakePacket(intensity, duration, true));
        }
    }

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

    public static void targetIndicator(LivingEntity target) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 10));
    }

    public static void particleLine(ServerWorld world, Vec3d startPos, float yaw, float pitch, double length, ParticleEffect particleType, double spread, int count, double delta, double speed) {
        Vec3d direction = MathUtils.getDirectionalVector(yaw, pitch);
        particleLine(world, startPos, startPos.add(direction.multiply(length)), particleType, spread, count, delta, speed);
    }

    public static void particleCuboid(ServerWorld world, Vec3d center, ParticleEffect particleType, double horizontalRange, double verticalRange, int count, double speed) {
        world.spawnParticles(particleType, center.getX(), center.getY(), center.getZ(), count, horizontalRange, verticalRange, horizontalRange, speed);
    }

    public static void particleAroundEntity(LivingEntity entity, ParticleEffect particleType, int count, double delta, double speed) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;

        world.spawnParticles(particleType, entity.getX(), entity.getEyeY(), entity.getZ(), count, delta, delta, delta, speed);
    }

    public static void particleCube(ServerWorld world, Vec3d center, ParticleEffect particleType, int count, double delta, double speed) {
        particleCuboid(world, center, particleType, delta, delta, count, speed);
    }

    public static void particleCylinder(ServerWorld world, Vec3d center, ParticleEffect particleType, int count, float horizontalDistance, float verticalDistance, float speed) {
        S2CParticleCylinderPacket packet = new S2CParticleCylinderPacket(particleType, false, center.getX(), center.getY(), center.getZ(), horizontalDistance, verticalDistance, speed, count);

        for(ServerPlayerEntity player : world.getPlayers()) {
            if(!player.getBlockPos().isWithinDistance(center, 32f)) continue;
            NetworkManager.sendToPlayer(player, packet);
        }
    }

    public static void rainParticlesAboveEntity(LivingEntity entity, ParticleEffect particleType, int count, float delta, double height, float speed) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return;

        particleCylinder(world, entity.getPos().offset(Direction.UP, height), particleType, count, delta, 0, speed);
    }

    public static void playSound(World world, Vec3d pos, Sound sound) {
        world.playSound(null, pos.x, pos.y, pos.z, sound.event(), SoundCategory.PLAYERS, sound.volume(), sound.pitch());
    }
}
