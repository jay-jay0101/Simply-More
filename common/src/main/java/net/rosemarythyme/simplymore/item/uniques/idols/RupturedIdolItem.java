package net.rosemarythyme.simplymore.item.uniques.idols;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class RupturedIdolItem extends SimplyMoreUniqueSwordItem {

    public RupturedIdolItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ruptured_idol.tooltip1").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }
}
