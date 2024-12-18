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

public class SpearItem extends MimicryItem {
    public SpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableSpearVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.spear");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.spear.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.spear.tooltip2").setStyle(textStyle));
    }

    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 6 || ticksUsed == 10) {
            List<LivingEntity> enemies = stabAttack(player, 5, 0.4f);

            float damage = mimicryAttributes.getSpearDamage();
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicryAttributes.getSpearEffectTime(),
                                        0
                                )
                        );
                    }
            );
        }
        if(ticksUsed == 20) {
            List<LivingEntity> enemies = stabAttack(player, 5, 0.4f);

            float damage = mimicryAttributes.getSpearFinalStabDamage();
            enemies.forEach(
                    target -> {
                        breakShield(target);
                        target.timeUntilRegen = 0;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicryAttributes.getSpearEffectTime(),
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 28) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING);
        }

    }
}
