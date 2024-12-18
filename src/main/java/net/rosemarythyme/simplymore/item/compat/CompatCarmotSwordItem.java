package net.rosemarythyme.simplymore.item.compat;

import net.minecraft.item.ToolMaterial;
import nourl.mythicmetals.abilities.Abilities;
import nourl.mythicmetals.misc.UsefulSingletonForColorUtil;

public class CompatCarmotSwordItem extends CompatSwordItem {

    public CompatCarmotSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, boolean grandsword, boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, grandsword, lance, repairIngredient);
        Abilities.BONUS_LOOTING.addItem(this, UsefulSingletonForColorUtil.MetalColors.CARMOT_STYLE);
    }
}
