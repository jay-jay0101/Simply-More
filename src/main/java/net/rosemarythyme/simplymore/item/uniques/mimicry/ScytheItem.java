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

public class ScytheItem extends MimicryItem {
    public ScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 2 || ticksUsed == 17 || ticksUsed == 32) {
            jump(player, 3f,0.18f);
        }

        if(ticksUsed == 15 || ticksUsed == 30 || ticksUsed == 45) {
            List<LivingEntity> enemies = sweepAttack(player, 2.8f);
            float damage = mimicryAttributes.getScytheDamage();
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        mimicryAttributes.getScytheEffectTime()
                                )
                        );
                    }
            );

            List<LivingEntity> enemies2 = sweepAttack(player, 2.8f,25);
            enemies2.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        mimicryAttributes.getScytheEffectTime()
                                )
                        );
                    }
            );

            List<LivingEntity> enemies3 = sweepAttack(player, 2.8f,-25);
            enemies2.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        mimicryAttributes.getScytheEffectTime()
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 50) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING);
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableScytheVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.scythe");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.scythe.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.scythe.tooltip2").setStyle(textStyle));
    }
}
