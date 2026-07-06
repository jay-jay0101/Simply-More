package net.rosemarythyme.simplymore.item.compat.mythicmetals;

import com.mythicmetals.ability.Abilities;
import com.mythicmetals.misc.UsefulSingletonForColorUtil;
import net.minecraft.item.ToolMaterial;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;

public class LegendaryBanglumSwordItem extends SimplyMoreSwordItem {
    public LegendaryBanglumSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordType swordType, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, swordType, settings, repairIngredient);
        Abilities.KNOCKBACK.addItem(this, UsefulSingletonForColorUtil.MetalColors.GOLD_STYLE);
    }
}
