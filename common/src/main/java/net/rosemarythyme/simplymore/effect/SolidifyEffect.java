package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.sweenus.simplyswords.effect.FreezeEffect;

public class SolidifyEffect extends FreezeEffect {
    public SolidifyEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public boolean applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.getWorld().isClient() && livingEntity instanceof PlayerEntity player) {
            for (ItemStack itemstack : livingEntity.getHandItems()) {
                if (!player.getItemCooldownManager().isCoolingDown(itemstack.getItem())) {
                    player.getItemCooldownManager().set(itemstack.getItem(), 60);
                }
            }
        }

        return super.applyUpdateEffect(livingEntity, amplifier);
    }

}
