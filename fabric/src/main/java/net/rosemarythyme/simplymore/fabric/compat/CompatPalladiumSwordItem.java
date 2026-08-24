package net.rosemarythyme.simplymore.fabric.compat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.rosemarythyme.simplymore.item.compat.CompatSwordItem;
import nourl.mythicmetals.abilities.Abilities;
import nourl.mythicmetals.item.tools.PalladiumToolSet;
import nourl.mythicmetals.misc.UsefulSingletonForColorUtil;

public class CompatPalladiumSwordItem extends CompatSwordItem {

    public CompatPalladiumSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, boolean grandsword, boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, grandsword, lance, repairIngredient);
        Abilities.HOT.addItem(this, UsefulSingletonForColorUtil.MetalColors.PALLADIUM_STYLE);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        PalladiumToolSet.applyHeatToTarget(target, attacker);
        return super.postHit(stack, target, attacker);
    }
}
