package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.EntityUtils;

public interface Weapon {

    SwordType getSwordType();

    static void tryGrantLanceEffect(Entity entity) {
        if(!(entity instanceof LivingEntity livingEntity)) return;

        if (EntityUtils.shouldGrantLanceEffect(livingEntity)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.LANCE), StatusEffectInstance.INFINITE, 0));
        }
    }

    enum SwordType {
        SWORD,
        LANCE,
        GRANDSWORD
    }
}
