package net.rosemarythyme.simplymore.item.compat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class CompatNetherGobberSwordItem extends CompatSwordItem {

    public CompatNetherGobberSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, boolean grandsword, boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, grandsword, lance, repairIngredient);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(10);

        return super.postHit(stack, target, attacker);
    }
}
