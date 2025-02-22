package net.rosemarythyme.simplymore.item.uniques.mimicry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

import java.util.List;

public class RapierItem extends MimicryItem {
    public RapierItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableRapierVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.rapier");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.rapier.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.rapier.tooltip2").setStyle(textStyle));
    }

    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        float damage = mimicryAttributes.getRapierDamage();

        if(ticksUsed == 4 || ticksUsed == 16) {
            List<LivingEntity> enemies = stabAttack(player, 3, 0.25f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        ModEffectsRegistry.BLEED.get(),
                                        mimicryAttributes.getRapierEffectTime(),
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed == 7 || ticksUsed == 10 || ticksUsed == 13) {
            List<LivingEntity> enemies = stabAttack(player, 3, 0.25f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                    }
            );
        }

        if(ticksUsed >= 22) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get());
        }

    }
}
