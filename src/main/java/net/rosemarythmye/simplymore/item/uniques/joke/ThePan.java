package net.rosemarythmye.simplymore.item.uniques.joke;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.rosemarythmye.simplymore.item.normal.Lance;
import net.rosemarythmye.simplymore.item.normal.Sword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class ThePan extends Sword {

    public ThePan(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

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
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip2").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }


    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
