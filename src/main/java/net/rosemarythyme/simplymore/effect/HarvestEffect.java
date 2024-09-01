package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Box;

public class HarvestEffect extends StatusEffect {

    public HarvestEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        double range = 20;
        Box searchArea = new Box(
                livingEntity.getX() - range,
                livingEntity.getY() - range,
                livingEntity.getZ() - range,
                livingEntity.getX() + range,
                livingEntity.getY() + range,
                livingEntity.getZ() + range
        );

        for (LivingEntity target : livingEntity.getWorld().getNonSpectatingEntities(LivingEntity.class, searchArea)) {
            if (target == livingEntity)
                continue;
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 40, 0), livingEntity);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
