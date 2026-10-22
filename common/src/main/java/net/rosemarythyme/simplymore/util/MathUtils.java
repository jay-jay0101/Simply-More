package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.AbstractAbilityPlacementEntity;
import org.joml.Vector3f;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class MathUtils {
    public static boolean chance(LivingEntity player, float chance) {
        return player.getRandom().nextFloat() <= chance;
    }

    public static Vec3d getNormalised2dVector(float yaw) {
        double yawAngle = Math.toRadians(yaw);
        double cosYaw = Math.cos(yawAngle);
        double sinYaw = Math.sin(yawAngle);

        return new Vec3d(-sinYaw, 0, cosYaw);
    }

    public static Vec3d getNormalised3dVector(Entity entity) {
        Vec3d vector = entity.getRotationVec(1.0F).normalize();
        return new Vec3d(
                vector.getX(),
                vector.getY(),
                vector.getZ()
        );
    }

    public static float clampedLerp(float input, float inputMinimum, float inputMaximum, float outputMinimum, float outputMaximum) {
        float inputRange = inputMaximum - inputMinimum;
        float outputRange = outputMaximum - outputMinimum;
        float normalisedInput = Math.clamp((input - inputMinimum) / inputRange, 0, 1);

        return (normalisedInput * outputRange) + outputMinimum;
    }

    public static float wave(float input, float interim, float outputMinimum, float outputMaximum) {
        float center = (outputMinimum + outputMaximum) / 2;
        float range = (outputMaximum - outputMinimum) / 2;

        return (float) Math.sin(input * Math.PI * 2 / interim) * range + center;
    }

    public static String translateTicks(int ticks) {
        float seconds = ticks / 20f;
        return new DecimalFormat("#.##").format(seconds);
    }

    public static Vector3f colorToVector(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        return new Vector3f(r / 255f, g / 255f, b / 255f);
    }

    public static String toPercentage(float decimal) {
        float output = decimal * 100;
        return String.valueOf((int) Math.floor(output))
                .concat("%");
    }

    public static Vec3d getHoverPosBehindOwner(LivingEntity owner, Entity entity, int ordinal, double offset, double behindRange) {
        Vec3d direct = EntityUtils.rangeAroundPoint(owner.getEyePos().offset(Direction.UP, 0.5f), entity, owner.getYaw() + 180, (float) behindRange);

        float offsetPos;
        if(ordinal % 2 == 0) {
            offsetPos = (float) ordinal / 2;
        } else {
            offsetPos = (float) (ordinal + 1) / 2;
            offsetPos = (float) -Math.floor(offsetPos);
        }

        Vec3d tangent = MathUtils.getDirectionalVector(owner.getYaw() + 90, 0);
        return direct.add(tangent.multiply(offsetPos * offset));
    }

    public static Box createCuboidBox(Vec3d centre, double xOffset, double yOffset, double zOffset) {
        return new Box(
                centre.getX() - xOffset,
                centre.getY() - yOffset,
                centre.getZ() - zOffset,
                centre.getX() + xOffset,
                centre.getY() + yOffset,
                centre.getZ() + zOffset
        );
    }

    public static Box createCuboidBox(Vec3d centre, double negXOffset, double negYOffset, double negZOffset, double posXOffset, double posYOffset, double posZOffset) {
        return new Box(
                centre.getX() + negXOffset,
                centre.getY() + negYOffset,
                centre.getZ() + negZOffset,
                centre.getX() + posXOffset,
                centre.getY() + posYOffset,
                centre.getZ() + posZOffset
        );
    }

    public static Box createCubeBox(Vec3d centre, double offset) {
        return createCuboidBox(centre, offset, offset, offset);
    }

    public static double getMaxAbsLength(Vec3d direction) {
        return Math.max(
                Math.abs(direction.getX()),
                Math.max(
                        Math.abs(direction.getY()),
                        Math.abs(direction.getZ())
                )
        );
    }

    public static double cubicDistance(Vec3d posA, Vec3d posB) {
        return Math.max(
                Math.abs(posA.getX() - posB.getX()),
                Math.max(
                        Math.abs(posA.getY() - posB.getY()),
                        Math.abs(posA.getZ() - posB.getZ())
                )
        );
    }

    public static Pair<Float, Float> getYawAndPitchBetween(Vec3d pointA, Vec3d pointB) {
        Vec3d direction = normalisedDirectionBetween(pointA, pointB, true);
        return getYawAndPitch(direction);
    }

    public static Vec3d normalisedDirectionBetween(Vec3d pointA, Vec3d pointB, boolean includeY) {
        return new Vec3d(
                pointB.getX() - pointA.getX(),
                includeY ? pointB.getY() - pointA.getY() : 0,
                pointB.getZ() - pointA.getZ()
        ).normalize();
    }

    public static Vec3d getDirectionalVector(float yaw, float pitch) {
        double radianYaw = Math.toRadians(yaw);
        double radianPitch = Math.toRadians(pitch);

        double cosPitch = Math.cos(radianPitch);

        return new Vec3d(
            -Math.sin(radianYaw) * cosPitch,
            -Math.sin(radianPitch),
            Math.cos(radianYaw) * cosPitch
        );
    }

    public static Pair<Float, Float> getYawAndPitch(Vec3d direction) {
        Vec3d dir = direction.normalize();

        float yaw = (float) Math.toDegrees(Math.atan2(-dir.x, dir.z));
        float pitch = (float) Math.toDegrees(Math.asin(-dir.y));

        return new Pair<>(yaw, pitch);
    }

    public static double getRiseFall(AbstractAbilityPlacementEntity entity) {
        if(entity.isActive()) return 0;
        int age = entity.getAge();

        if(age < 0) {
            return clampedLerp(age, -entity.getIntroTicks(), 0, -1f, 0f);
        }

        return -clampedLerp(age, entity.getLifespan(), entity.getLifespan() + entity.getOutroTicks(), 0f, 1f);
    }

    public static Set<BlockPos> getPositionsOnFloor(World world, BlockPos center, int horizontalRange, int aboveRange, int belowRange, int num) {
        int attempts = num * 5;
        Set<BlockPos> positions = new HashSet<>();

        Random random = world.getRandom();

        for(int i = 0; i < attempts; i++) {
            int x = random.nextBetween(-horizontalRange, horizontalRange);
            int z = random.nextBetween(-horizontalRange, horizontalRange);

            BlockPos pos = new BlockPos(x + center.getX(), aboveRange + center.getY(), z + center.getZ());
            for(int y = 0; y <= aboveRange + belowRange; y++) {
                if (world.getBlockState(pos).isFullCube(world, pos)) {
                    positions.add(pos);
                    break;
                }

                pos = pos.down();
            }

            if(positions.size() >= num) break;
        }

        return positions;
    }

    public static int PSEUDOINFINITE_DURATION = 9999999;
    public static int getUseTicksFromInfiniteDuration(int duration) {
        return PSEUDOINFINITE_DURATION - duration;
    }
}
