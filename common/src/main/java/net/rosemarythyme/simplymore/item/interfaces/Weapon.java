package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

public interface Weapon {

    SwordTypes swordType();

    static void causeLanceEffect(Entity entity, boolean selected) {
        if(!(entity instanceof LivingEntity livingEntity)) return;

        if (entity.getVehicle() instanceof LivingEntity
                && selected
                && !(livingEntity.getStackInHand(Hand.OFF_HAND).getItem() instanceof ToolItem)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.LANCE), 9999999, 0));
        }
    }

    enum SwordTypes {
        SWORD,
        LANCE,
        GRANDSWORD
    }
}
