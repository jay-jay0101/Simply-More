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

public class GreatSpearItem extends MimicryItem {
    public GreatSpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 6) {
            List<LivingEntity> enemies = slamAttack(player, 4f);
            float damage = mimicryAttributes.getGreatSpearSlamDamage();
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.MINING_FATIGUE,
                                        mimicryAttributes.getGreatSpearEffectTime(),
                                        25
                                )
                        );
                        jump(target, 0, 0.525f);
                    }
            );
        }

        if(ticksUsed == 18) {
            List<LivingEntity> enemies = stabAttack(player, 6,0.8f);
            float damage = mimicryAttributes.getGreatSpearStabDamage();
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        knockback(player,target, mimicryAttributes.getGreatSpearStabKnockback());
                    }
            );
        }

        if(ticksUsed >= 28) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get());
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableGreatSpearVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.great_spear");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_spear.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_spear.tooltip2").setStyle(textStyle));
    }
}
