package net.rosemarythmye.simplymore.item.uniques.idols;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.entity.BlackPearlFireball;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class RupturedIdol extends SwordItem {

    public RupturedIdol(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ruptured_idol.tooltip1").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    public Text getName(ItemStack stack) {
        Style UNIQUE = HelperMethods.getStyle("unique");
        return Text.translatable(this.getTranslationKey(stack)).setStyle(UNIQUE);
    }
}
