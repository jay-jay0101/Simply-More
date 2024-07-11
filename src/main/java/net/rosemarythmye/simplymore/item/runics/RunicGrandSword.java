package net.rosemarythmye.simplymore.item.runics;

import net.minecraft.item.ToolMaterial;
import net.sweenus.simplyswords.item.RunicSwordItem;

public class RunicGrandSword extends RunicSwordItem {

    String[] repairIngredient;
    public RunicGrandSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.repairIngredient = repairIngredient;
    }
}
