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
import net.minecraft.util.math.Vec3d;
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
        // Check if the game is running on the client side.
        // If so, we don't need to handle the knockback effect or sound playback.
        if (attacker.getWorld().isClient()) {
            // Call the superclass's postHit method to handle any default behavior.
            return super.postHit(stack, target, attacker);
        }

        // Check if the attacker's random number generator returns a value greater than 30.
        // If so, we don't apply the knockback effect.
        // This is, functionally, the same as checking if the attacker's random number generator returns a value less than or equal to 30.
        if (attacker.getRandom().nextBetween(1, 100) > 30) {
            // Call the superclass's postHit method to handle any default behavior.
            return super.postHit(stack, target, attacker);
        }

        // Get the positions of the target and attacker entities.
        Vec3d targetPosition = target.getPos();
        Vec3d attackerPosition = attacker.getPos();

        // Calculate the difference in x and z coordinates between the target and attacker positions.
        double deltaX = targetPosition.getX() - attackerPosition.getX();
        double deltaZ = targetPosition.getZ() - attackerPosition.getZ();

        // Calculate the distance between the target and attacker positions using the Pythagorean theorem.
        double distance = Math.hypot(deltaX, deltaZ);

        // Check if the distance is zero.
        // If so, we don't apply the knockback effect.
        if (distance == 0) {
            // Call the superclass's postHit method to handle any default behavior.
            return super.postHit(stack, target, attacker);
        }

        // Define the knockback strength.
        float knockbackStrength = 20f;

        // Normalize the delta x and z values to get the direction of the knockback.
        double normalizedDeltaX = deltaX / distance;
        double normalizedDeltaZ = deltaZ / distance;

        // Apply the knockback effect to the target entity.
        target.setVelocity(normalizedDeltaX * knockbackStrength, 0.2, normalizedDeltaZ * knockbackStrength);
        target.velocityModified = true;

        // Play the sound effect at the attacker's position.
        attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1, 1);

        // Return true to indicate that the method handled the hit.
        return true;
    }

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
