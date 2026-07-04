package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;

public class EntityUtils {
    public static boolean shouldGrantLanceEffect(LivingEntity entity) {
        return isLanceInMainHand(entity) && isRidingLivingEntity(entity) && isOffHandEmpty(entity);
    }

    private static boolean isLanceInMainHand(LivingEntity livingEntity) {

        return livingEntity.getMainHandStack().getItem() instanceof Weapon weapon && weapon.swordType() == Weapon.SwordTypes.LANCE;
    }

    private static boolean isRidingLivingEntity(LivingEntity entity) {
        return entity.getVehicle() instanceof LivingEntity;
    }

    private static boolean isOffHandEmpty(LivingEntity livingEntity) {
        return !(livingEntity.getStackInHand(Hand.OFF_HAND).getItem() instanceof ToolItem);
    }
}
