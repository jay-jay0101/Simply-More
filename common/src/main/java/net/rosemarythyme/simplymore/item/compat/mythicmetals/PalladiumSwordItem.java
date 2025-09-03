package net.rosemarythyme.simplymore.item.compat.mythicmetals;

import com.mythicmetals.item.tools.PalladiumToolSet;
import com.mythicmetals.misc.UsefulSingletonForColorUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;

import java.util.List;

public class PalladiumSwordItem extends SimplyMoreSwordItem {
    public PalladiumSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordTypes swordType, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, swordType, settings, repairIngredient);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        PalladiumToolSet.applyHeatToTarget(target, attacker);
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("abilities.mythicmetals.hot").setStyle(UsefulSingletonForColorUtil.MetalColors.PALLADIUM_STYLE));
        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }
}
