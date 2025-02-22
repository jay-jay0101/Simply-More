package net.rosemarythyme.simplymore.item.uniques.mimicry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

import java.util.List;

public class GrandswordItem extends MimicryItem {
    public GrandswordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed > 8 && 50 >= ticksUsed) {
            jump(player,0.65f,0f);
            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.BLINDNESS,
                            40,
                            0
                    )
            );

            if(ticksUsed % 10 != 0) return;

            List<LivingEntity> enemies = spinAttack(player, 4f);
            float damage = mimicryAttributes.getGrandswordDamage();

            enemies.forEach(
                    target -> {
                        breakShield(target);
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        player.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.STRENGTH,
                                        mimicryAttributes.getGrandswordEffectTime(),
                                        0
                                )
                        );
                        knockback(player, target, mimicryAttributes.getGrandswordKnockback());
                    }
            );
        }

        if(ticksUsed >= 60) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get());
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableGrandswordVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.grandsword");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.grandsword.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.grandsword.tooltip2").setStyle(textStyle));
    }
}
