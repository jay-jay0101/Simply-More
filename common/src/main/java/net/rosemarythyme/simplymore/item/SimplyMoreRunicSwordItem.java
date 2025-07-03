package net.rosemarythyme.simplymore.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.sweenus.simplyswords.item.RunicSwordItem;

public class SimplyMoreRunicSwordItem extends RunicSwordItem implements Weapon {
    String[] repairIngredient;
    final SwordTypes swordType;

    public SimplyMoreRunicSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordTypes swordType, Settings settings, String... repairIngredient) {
        super(toolMaterial, settings.attributeModifiers(
                SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));

        this.repairIngredient = repairIngredient;
        this.swordType = swordType;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        Weapon.causeLanceEffect(entity, selected);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public SwordTypes swordType() {
        return swordType;
    }
}
