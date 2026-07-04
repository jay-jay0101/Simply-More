package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.ModComponentRegistry;
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
        if((stack.getItem() instanceof SimplyMoreUniqueSwordItem swordItem)) {
            if(stack.getComponents().contains(ModComponentRegistry.COUNTER.get())) {
                return stack.getComponents().get(ModComponentRegistry.COUNTER.get());
            } else {
                return setCounterComponent(stack, swordItem.getDefaultComponent());
            }
        }

        return null;
    }

    public static CounterComponent setCounterComponent(ItemStack stack, CounterComponent component) {
        stack.set(ModComponentRegistry.COUNTER.get(), component);
        return component;
    }
}
