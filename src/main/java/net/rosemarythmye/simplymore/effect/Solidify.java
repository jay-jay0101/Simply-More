package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.sweenus.simplyswords.effect.FreezeEffect;

public class Solidify extends FreezeEffect {
    public Solidify(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.getWorld().isClient() && pLivingEntity instanceof PlayerEntity player) {
            for (ItemStack itemstack : pLivingEntity.getHandItems()) {
                if (!player.getItemCooldownManager().isCoolingDown(itemstack.getItem())) {
                    player.getItemCooldownManager().set(itemstack.getItem(),60);
                }
            }
        }

        super.applyUpdateEffect(pLivingEntity, pAmplifier);
    }

}
