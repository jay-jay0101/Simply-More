package net.rosemarythyme.simplymore.item.uniques.mimicry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

import java.util.List;

public class GlaiveItem extends MimicryItem {
    public GlaiveItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 2) {
            List<LivingEntity> enemies = sweepAttack(player, 3f);
            float damage = mimicryAttributes.getGlaiveDamage();
            enemies.forEach(
                    target -> {
                        target.dismountVehicle();
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                    }
            );
        }

        if(ticksUsed >= 8) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get());
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableGlaiveVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.glaive");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.glaive.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.glaive.tooltip2").setStyle(textStyle));
    }
}
