package net.rosemarythyme.simplymore.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.item.RunicSwordItem;

import java.util.List;

public class SimplyMoreRunicSwordItem extends RunicSwordItem implements Weapon {
    String[] repairIngredient;
    final SwordTypes swordType;

    public SimplyMoreRunicSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordTypes swordType, Settings settings, String... repairIngredient) {
        super(toolMaterial, settings.attributeModifiers(
                SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));

        this.repairIngredient = repairIngredient;
        this.swordType = swordType;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        Weapon.causeLanceEffect(entity, selected);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public SwordTypes swordType() {
        return swordType;
    }

    protected void generateDynamicTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        SimplySwordsClientAPI.generateDynamicTooltip(itemStack, tooltipContext, tooltip, type, "simplymore", "oracle_index:books/simplymore/weapon_types", "oracle_index:books/simplymore/unique_weapons", "", (Identifier)null);
    }
}
