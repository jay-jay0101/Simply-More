package net.rosemarythyme.simplymore.item.uniques.joke;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.normal.SimplyMoreSwordItem;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThePanItem extends SimplyMoreSwordItem {

    String[] repairIngredient;

    public ThePanItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.repairIngredient = new String[]{"minecraft:iron_ingot"};
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 30) {
                attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS,1,1);
                double xVelocity = target.getX()-attacker.getX();
                double zVelocity = target.getZ()-attacker.getZ();
                double ratioMax = Math.abs(xVelocity)+ Math.abs(zVelocity);
                float strength = 20f;

                xVelocity *= strength/ratioMax;
                zVelocity *= strength/ratioMax;

                target.setVelocity(xVelocity,0.2,zVelocity);
                target.velocityModified = true;
            }
        }
        return super.postHit(stack, target, attacker);
    }

    // TODO: This method should be redone in order to allow for it to make use of a lang file. This will allow for translations to be added.
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip2").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
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
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
