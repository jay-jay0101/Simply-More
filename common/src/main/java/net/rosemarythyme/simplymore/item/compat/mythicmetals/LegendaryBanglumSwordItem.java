package net.rosemarythyme.simplymore.item.compat.mythicmetals;

import com.mythicmetals.ability.Abilities;
import com.mythicmetals.misc.UsefulSingletonForColorUtil;
import net.minecraft.item.ToolMaterial;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;

public class LegendaryBanglumSwordItem extends SimplyMoreSwordItem {
    public LegendaryBanglumSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        Abilities.KNOCKBACK.addItem(this, UsefulSingletonForColorUtil.MetalColors.GOLD_STYLE);
    }
}
