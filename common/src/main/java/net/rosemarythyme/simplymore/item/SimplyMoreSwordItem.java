package net.rosemarythyme.simplymore.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.item.SimplySwordsSwordItem;

import java.util.List;

public class SimplyMoreSwordItem extends SimplySwordsSwordItem implements Weapon {
    private final SwordType swordType;

    public SimplyMoreSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordType swordType, Settings settings, String... repairIngredient) {
        super(toolMaterial, settings.attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));

        this.swordType = swordType;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(swordType == SwordType.LANCE) {
            Weapon.tryGrantLanceEffect(attacker, target);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public SwordType getSwordType() {
        return swordType;
    }

    @Override
    protected void generateDynamicTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        SimplySwordsClientAPI.generateDynamicTooltip(itemStack, tooltipContext, tooltip, type, "simplymore", "oracle_index:books/simplymore/weapon_types", "oracle_index:books/simplymore/unique_weapons", "", (Identifier)null);
    }
}
