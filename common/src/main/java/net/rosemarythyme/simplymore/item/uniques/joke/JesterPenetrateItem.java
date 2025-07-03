package net.rosemarythyme.simplymore.item.uniques.joke;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class JesterPenetrateItem extends SimplyMoreSwordItem {

    public JesterPenetrateItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.LANCE, settings);
        this.repairIngredient = new String[]{
                "minecraft:white_wool",
                "minecraft:orange_wool",
                "minecraft:magenta_wool",
                "minecraft:light_blue_wool",
                "minecraft:yellow_wool",
                "minecraft:lime_wool",
                "minecraft:pink_wool",
                "minecraft:gray_wool",
                "minecraft:light_gray_wool",
                "minecraft:cyan_wool",
                "minecraft:purple_wool",
                "minecraft:blue_wool",
                "minecraft:brown_wool",
                "minecraft:green_wool",
                "minecraft:red_wool",
                "minecraft:black_wool"
        };
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.getTime() % 20 == 0
                && entity instanceof PlayerEntity player && player.getStackInHand(Hand.MAIN_HAND).equals(stack)) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 0));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }
}
