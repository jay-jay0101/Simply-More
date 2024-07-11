package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.rosemarythmye.simplymore.item.itemclasses.normal.Lance;
import net.rosemarythmye.simplymore.item.itemclasses.runics.RunicLance;
import net.rosemarythmye.simplymore.item.itemclasses.uniques.Glimmerstep;

public class LanceEffect extends StatusEffect {
    public LanceEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        if(!(entity.getMainHandStack().getItem() instanceof Lance || entity.getMainHandStack().getItem() instanceof RunicLance || entity.getMainHandStack().getItem() instanceof Glimmerstep) || !(entity.getVehicle() instanceof LivingEntity)) entity.removeStatusEffect(this);
        if(!((PlayerEntity) entity).getStackInHand(Hand.OFF_HAND).getItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_DAMAGE).isEmpty()) entity.removeStatusEffect(this);
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
