package net.rosemarythyme.simplymore.item.normal;

import net.minecraft.item.ToolMaterial;

public class GrandSwordItem extends SimplyMoreSwordItem {

    public GrandSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.repairIngredient = repairIngredient;
    }
}
