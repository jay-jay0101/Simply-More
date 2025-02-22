package net.rosemarythyme.simplymore.item.runics;

import net.minecraft.item.ToolMaterial;
import net.sweenus.simplyswords.item.RunicSwordItem;

public class RunicGrandSwordItem extends RunicSwordItem {

    String[] repairIngredient;
    public RunicGrandSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.repairIngredient = repairIngredient;
    }
}
