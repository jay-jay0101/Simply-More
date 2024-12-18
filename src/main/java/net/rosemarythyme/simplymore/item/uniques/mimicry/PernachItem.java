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

public class PernachItem extends MimicryItem {
    public PernachItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        float damage = mimicryAttributes.getPernachDamage();

        if(ticksUsed == 3 || ticksUsed == 12) {
            List<LivingEntity> enemies = sweepAttack(player, 1.4f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) breakShield(target);
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                    }
            );
        }

        if(ticksUsed == 21) {
            List<LivingEntity> enemies = sweepAttack(player, 1.6f);

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicryAttributes.getPernachEffectTime(),
                                        1
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WEAKNESS,
                                        mimicryAttributes.getPernachEffectTime(),
                                        0
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        ModEffectsRegistry.BLEED,
                                        mimicryAttributes.getPernachEffectTime(),
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 25) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING);
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisablePernachVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.pernach");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.pernach.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.pernach.tooltip2").setStyle(textStyle));
    }
}
