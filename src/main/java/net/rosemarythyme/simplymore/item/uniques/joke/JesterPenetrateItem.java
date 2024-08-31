package net.rosemarythyme.simplymore.item.uniques.joke;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.normal.LanceItem;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JesterPenetrateItem extends LanceItem {

    String[] repairIngredient;

    public JesterPenetrateItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
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
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        List<Item> potentialIngredients = new ArrayList<>(List.of());
        Arrays.stream(this.repairIngredient).toList().forEach(
                (repIngredient) -> potentialIngredients.add(
                        Registries.ITEM.get(new Identifier(repIngredient))));
        return potentialIngredients.contains(ingredient.getItem());
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
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
