package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

public class LanceEffect extends StatusEffect {

    public LanceEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!isLanceInMainHand(livingEntity) || !isRidingLivingEntity(livingEntity) || !isOffHandEmpty(livingEntity))
            livingEntity.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.LANCE));
        return super.applyUpdateEffect(livingEntity, amplifier);
    }

    private boolean isLanceInMainHand(LivingEntity livingEntity) {
        return livingEntity.getMainHandStack().getItem() instanceof Weapon weapon && weapon.swordType() == Weapon.SwordTypes.LANCE;
    }

    private boolean isRidingLivingEntity(LivingEntity entity) {
        return entity.getVehicle() instanceof LivingEntity;
    }

    private boolean isOffHandEmpty(LivingEntity livingEntity) {
        return livingEntity.getStackInHand(Hand.OFF_HAND).getItem() instanceof ToolItem;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
