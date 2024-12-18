package net.rosemarythyme.simplymore.item.compat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import nourl.mythicmetals.abilities.Abilities;
import nourl.mythicmetals.item.tools.RiptideTool;
import nourl.mythicmetals.misc.UsefulSingletonForColorUtil;

public class CompatTidesingerSwordItem extends CompatSwordItem implements RiptideTool {

    public CompatTidesingerSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, boolean grandsword, boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, grandsword, lance, repairIngredient);
        Abilities.AQUA_AFFINITY.addItem(this, UsefulSingletonForColorUtil.MetalColors.TIDESINGER_BLUE);
        Abilities.RIPTIDE.addItem(this, UsefulSingletonForColorUtil.MetalColors.TIDESINGER_BLUE);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return this.activateRiptide(user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        this.performRiptide(stack, world, user, remainingUseTicks);
    }
}
