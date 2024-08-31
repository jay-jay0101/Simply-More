package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.normal.LanceItem;
import net.rosemarythyme.simplymore.item.runics.RunicLanceItem;
import net.rosemarythyme.simplymore.item.uniques.GlimmerstepItem;

public class LanceEffect extends StatusEffect {

    public LanceEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!isLanceInMainHand(livingEntity) || !isRidingLivingEntity(livingEntity) || !isOffHandEmpty(livingEntity))
            livingEntity.removeStatusEffect(this);

        super.applyUpdateEffect(livingEntity, amplifier);
    }

    private boolean isLanceInMainHand(LivingEntity livingEntity) {
        return livingEntity.getMainHandStack().getItem() instanceof LanceItem
                || livingEntity.getMainHandStack().getItem() instanceof RunicLanceItem
                || livingEntity.getMainHandStack().getItem() instanceof GlimmerstepItem;
    }

    private boolean isRidingLivingEntity(LivingEntity entity) {
        return entity.getVehicle() instanceof LivingEntity;
    }

    private boolean isOffHandEmpty(LivingEntity livingEntity) {
        return livingEntity.getStackInHand(Hand.OFF_HAND).getItem()
                .getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(EntityAttributes.GENERIC_ATTACK_DAMAGE)
                .isEmpty();
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
