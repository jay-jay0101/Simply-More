package net.rosemarythyme.simplymore.item.uniques.idols;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.AuraOfCorruptionAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class TarnishedIdolItem extends SwordItem {

    public TarnishedIdolItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        SimplyMoreHelperMethods.simplyMore$IdolHitEffects(
                attacker,
                ParticleTypes.FALLING_OBSIDIAN_TEAR,
                300,
                1.0D,
                1.0D,
                1.0D,
                0.0D,
                new AuraOfCorruptionAreaEffectCloudEntity(
                        attacker.getWorld(),
                        attacker.getX(),
                        attacker.getY(),
                        attacker.getZ(),
                        attacker
                )
        );

        return super.postHit(stack, target, attacker);
    }

    public Text getName(ItemStack stack) {
        Style UNIQUE = HelperMethods.getStyle("unique");
        return Text.translatable(this.getTranslationKey(stack)).setStyle(UNIQUE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style textStyle = HelperMethods.getStyle("text");
        Style abilityStyle = HelperMethods.getStyle("ability");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
