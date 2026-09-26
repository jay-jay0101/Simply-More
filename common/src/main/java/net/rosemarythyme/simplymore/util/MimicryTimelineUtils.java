package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.entity.MimicryVisualEntity;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;

import java.util.Optional;

public class MimicryTimelineUtils {
    public static TargetList sweepAttack(LivingEntity player, float range) {
        return sweepAttack(player, range, 0);
    }

    public static TargetList sweepAttack(LivingEntity player, float range, int angle) {
        Vec3d position = player.getEyePos().add(MathUtils.getNormalised2dVector(player.getYaw() + angle).multiply(range));

        AudioVisualUtils.playSound(player.getWorld(), position, new Sound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK).setPitch(0.5f));
        AudioVisualUtils.particleCube((ServerWorld) player.getWorld(), position, ParticleTypes.SWEEP_ATTACK, 1, 0, 0);

        return TargetUtils.cubeAttack(player, position, range, TargetUtils.TargetType.ENEMIES);
    }

    public static TargetList spinAttack(LivingEntity entity, float range) {
        ServerWorld world = (ServerWorld) entity.getWorld();
        float particleRange = range / 1.5f;

        for(int i = 0; i < 10; i++) {
            Vec3d normalisedVector = MathUtils.getNormalised2dVector(i * 36);

            AudioVisualUtils.particleCube(world, entity.getEyePos().add(normalisedVector.multiply(particleRange)), ParticleTypes.SWEEP_ATTACK, 1, 0f, 0f);
            AudioVisualUtils.playSound(world, entity.getPos(), new Sound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP).setPitch(entity.getRandom().nextBetween(9,14) / 10f));
        }

        return TargetUtils.cylinderAttack(entity, entity.getPos(), range, 1.5f, TargetUtils.TargetType.ENEMIES);
    }

    public static TargetList slamAttack(LivingEntity entity, float range) {
        ServerWorld world = (ServerWorld) entity.getWorld();
        int blockRange = Math.round(range);

        AudioVisualUtils.explosionBlocks(world, entity.getBlockPos(), 10, blockRange, 2, 3, 0.5f);
        MathUtils.getPositionsOnFloor(world, entity.getBlockPos(),blockRange, 2, 3, Math.round(range * range / 2f)).forEach(block ->
                AudioVisualUtils.dustPillar(world, block));

        AudioVisualUtils.applyScreenshake(world, entity.getPos(), entity, 10, 2, 10);
        AudioVisualUtils.playSound(world, entity.getPos(), new Sound(SoundEvents.ENTITY_GENERIC_EXPLODE.value()).setVolume(0.5f));

        return TargetUtils.cylinderAttack(entity, entity.getPos(), range, 3f, TargetUtils.TargetType.ENEMIES);
    }

    public static TargetList stabAttack(LivingEntity entity, float range, float width) {
        ServerWorld world = (ServerWorld) entity.getWorld();

        AudioVisualUtils.particleLine(world, entity.getEyePos(), entity.getYaw(), entity.getPitch(), range, ParticleTypes.CRIT, 0.3f, 1, 0f, 0f);
        AudioVisualUtils.playSound(world, entity.getPos(), new Sound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG).setVolume(0.5f));

        return TargetUtils.lineAttack(entity , entity.getEyePos(), entity.getYaw(), entity.getPitch(), range, width, TargetUtils.TargetType.ENEMIES);
    }

    public static void move(LivingEntity target, float horizontalStrength, float jumpStrength) {
        Vec3d facingVector = MathUtils.getNormalised2dVector(target.getYaw()).multiply(horizontalStrength);
        target.setVelocity(facingVector.x, jumpStrength, facingVector.z);
        target.velocityModified = true;
    }

    public static void startAnimation(LivingEntity player, int duration, MimicryVisualEntity.Animation animation) {
        Optional<MimicryVisualEntity> visual = SummonUtils.getOwnedEntities(player, MimicryVisualEntity.class).stream().findAny();
        if(visual.isPresent()) {
            visual.get().startAttack(duration, animation);
        }
    }

    public static MimicryVisualEntity.AnimationData sweepAnimation(LivingEntity owner, float progress, float angle) {
        float yaw = owner.getYaw();
        float pitch = angle >= 0 ? 90f : -90f;
        return new MimicryVisualEntity.AnimationData(MathUtils.clampedLerp(progress, 0f, 1f, -angle, angle) + yaw - 45f, pitch, 90, MathUtils.getDirectionalVector(yaw, 0).multiply(0.8f).offset(Direction.UP, 1f));
    }

    public static MimicryVisualEntity.AnimationData spinAnimation(float progress) {
        float yaw = MathUtils.clampedLerp(progress, 0f, 1f, 0, 360);
        return new MimicryVisualEntity.AnimationData(yaw - 45, 90, 90, MathUtils.getDirectionalVector(yaw, 0).multiply(0.8f).offset(Direction.UP, 1));
    }

    public static MimicryVisualEntity.AnimationData downSwingAnimation(LivingEntity owner, float progress, float angle) {
        float yaw = owner.getYaw();
        return new MimicryVisualEntity.AnimationData(yaw + 90, 0, MathUtils.clampedLerp(progress, 0f, 1f, angle, -angle) - 45, MathUtils.getDirectionalVector(yaw, 0).multiply(0.8f).offset(Direction.UP, 1f));
    }

    public static MimicryVisualEntity.AnimationData stabAnimation(LivingEntity owner, float progress) {
        float yaw = owner.getYaw();
        float range = MathUtils.clampedLerp(progress, 0f, 1f, 0.8f, 2.5f);
        return new MimicryVisualEntity.AnimationData(yaw - 45f, 90f, 90, MathUtils.getDirectionalVector(yaw, 0).multiply(range).offset(Direction.UP, 1f));
    }
}
