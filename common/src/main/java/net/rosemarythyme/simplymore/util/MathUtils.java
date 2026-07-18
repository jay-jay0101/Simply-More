package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.ItemComponentRegistry;
import org.joml.Vector3d;

import java.text.DecimalFormat;

public class MathUtils {
    public static boolean chance(LivingEntity player, float chance) {
        return player.getRandom().nextFloat() <= chance;
    }

    public static Vector3d getNormalised2dVector(float yaw) {
        double yawAngle = Math.toRadians(yaw);
        double cosYaw = Math.cos(yawAngle);
        double sinYaw = Math.sin(yawAngle);

        return new Vector3d(-sinYaw, 0, cosYaw);
    }

    public static Vector3d getNormalised3dVector(Entity entity) {
        Vec3d vector = entity.getRotationVec(1.0F).normalize();
        return new Vector3d(
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

    public static String translateTicks(int ticks) {
        float seconds = ticks / 20f;
        return new DecimalFormat("#.##").format(seconds);
    }

    public static String toPercentage(float decimal) {
        float output =  decimal * 100;
        return String.valueOf((int) Math.floor(output))
                .concat("%");
    }

    public static CounterComponent getCounterComponent(ItemStack stack) {
        if(!(stack.getItem() instanceof SimplyMoreUniqueSwordItem swordItem))
            throw new IllegalArgumentException("MathUtils#getCounterComponent should not be called on a non-unique.");

        CounterComponent component = stack.getComponents().get(ItemComponentRegistry.COUNTER.get());

        return component == null
                ? setCounterComponent(stack, swordItem.getDefaultCounterComponent())
                : component;
    }

    public static CounterComponent setCounterComponent(ItemStack stack, CounterComponent component) {
        stack.set(ItemComponentRegistry.COUNTER.get(), component);
        return component;
    }

    public static CounterComponent addToCounterComponent(ItemStack stack, int value) {
        CounterComponent component = getCounterComponent(stack);
        return setCounterComponent(stack, component.add(value));
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

    public static Vec3d normalisedDirectionBetween(Vec3d pointA, Vec3d pointB, boolean includeY) {
        return new Vec3d(
                pointB.getX() - pointA.getX(),
                includeY ? pointB.getY() - pointA.getY() : 0,
                pointB.getZ() - pointA.getZ()
        ).normalize();
    }
}
