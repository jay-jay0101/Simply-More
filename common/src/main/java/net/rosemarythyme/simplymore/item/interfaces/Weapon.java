package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.EntityUtils;

public interface Weapon {

    SwordTypes swordType();

    static void causeLanceEffect(Entity entity, boolean selected) {
        if(!(entity instanceof LivingEntity livingEntity)) return;

        if (entity.getVehicle() instanceof LivingEntity
                && selected
                && EntityUtils.shouldGrantLanceEffect(livingEntity)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.LANCE), 9999999, 0));
        }
    }

    enum SwordTypes {
        SWORD,
        LANCE,
        GRANDSWORD
    }
}
