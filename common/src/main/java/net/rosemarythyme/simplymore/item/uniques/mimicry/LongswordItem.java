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

public class LongswordItem extends MimicryItem {
    public LongswordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 3) {
            List<LivingEntity> enemies = sweepAttack(player, 1.6f);
            float damage = mimicryAttributes.getLongswordBaseDamage() + (enemies.size() * mimicryAttributes.getLongswordExtraDamagePerTarget());
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        knockback(player, target, mimicryAttributes.getLongswordKnockback());
                    }
            );
        }

        if(ticksUsed >= 13) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get());
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableLongswordVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.longsword");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.longsword.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.longsword.tooltip2").setStyle(textStyle));
    }
}
