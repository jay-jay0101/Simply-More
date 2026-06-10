package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

public interface Weapon {

    SwordTypes swordType();

    static void causeLanceEffect(Entity entity, boolean selected) {
        if(!(entity instanceof LivingEntity livingEntity)) return;

        if (entity.getVehicle() instanceof LivingEntity
                && selected
                && SimplyMoreHelperMethods.shouldGrantLanceEffect(livingEntity)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.LANCE), 9999999, 0));
        }
    }

    enum SwordTypes {
        SWORD,
        LANCE,
        GRANDSWORD
    }
}
