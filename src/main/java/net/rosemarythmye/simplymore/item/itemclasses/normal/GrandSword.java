package net.rosemarythmye.simplymore.item.itemclasses.normal;

import net.minecraft.item.ToolMaterial;

public class GrandSword extends Sword {

    public GrandSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.repairIngredient = repairIngredient;
    }
}
