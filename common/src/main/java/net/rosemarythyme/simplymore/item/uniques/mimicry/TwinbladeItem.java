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

public class TwinbladeItem extends MimicryItem {
    public TwinbladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 8) {
            List<LivingEntity> enemies = spinAttack(player, 4.5f);
            float damage = mimicryAttributes.getTwinbladeFirstDamage();

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicryAttributes.getTwinbladeEffectTime(),
                                        1
                                )
                        );
                    }
            );
        }

        if(ticksUsed == 24) {
            List<LivingEntity> enemies = spinAttack(player, 4.5f);
            float damage = mimicryAttributes.getTwinbladeSecondDamage();

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        knockback(player, target, mimicryAttributes.getTwinbladeKnockback());
                    }
            );
        }

        if(ticksUsed >= 30) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get());
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableTwinbladeVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.twinblade");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.twinblade.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.twinblade.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.twinblade.tooltip3").setStyle(textStyle));
    }
}
