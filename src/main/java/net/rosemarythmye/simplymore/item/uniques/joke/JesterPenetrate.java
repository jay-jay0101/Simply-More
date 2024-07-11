package net.rosemarythmye.simplymore.item.uniques.joke;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.item.normal.Lance;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class JesterPenetrate extends Lance {


    public JesterPenetrate(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip2").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }


    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity && selected) {
            ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, 0));
            ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20, 1));
            ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 0));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
