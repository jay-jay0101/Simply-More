package net.rosemarythyme.simplymore.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.item.SimplySwordsSwordItem;

import java.util.List;

public class SimplyMoreSwordItem extends SimplySwordsSwordItem {

    public SimplyMoreSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, settings.attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));
    }

    @Override
    protected void generateDynamicTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        SimplySwordsClientAPI.generateDynamicTooltip(itemStack, tooltipContext, tooltip, type, "simplymore", "oracle_index:books/simplymore/weapon_types", "oracle_index:books/simplymore/unique_weapons", "", (Identifier)null);
    }
}
