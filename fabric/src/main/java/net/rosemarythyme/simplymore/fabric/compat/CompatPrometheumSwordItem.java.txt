package net.rosemarythyme.simplymore.fabric.compat;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.compat.CompatSwordItem;
import nourl.mythicmetals.abilities.Abilities;
import nourl.mythicmetals.item.tools.AutoRepairable;
import nourl.mythicmetals.item.tools.PalladiumToolSet;
import nourl.mythicmetals.item.tools.PrometheumToolSet;
import nourl.mythicmetals.misc.UsefulSingletonForColorUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CompatPrometheumSwordItem extends CompatSwordItem implements AutoRepairable {

    public CompatPrometheumSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, boolean grandsword, boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, grandsword, lance, repairIngredient);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        PrometheumToolSet.tickAutoRepair(stack, world);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(PrometheumToolSet.isOvergrown(stack)) {
            tooltip.add(Text.translatable("tooltip.prometheum.overgrown").setStyle(Style.EMPTY.withColor(3828310)));
        }
        tooltip.add(Text.translatable("tooltip.prometheum.mending").setStyle(Style.EMPTY.withColor(3828310)));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
