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
import net.rosemarythyme.simplymore.entity.AuraOfPurityAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;

import java.util.List;


public class AscendedIdolItem extends SwordItem {

    public AscendedIdolItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        SimplyMoreHelperMethods.simplyMore$IdolHitEffects(
                attacker,
                ParticleTypes.FALLING_WATER,
                300,
                1,
                1,
                1,
                0,
                new AuraOfPurityAreaEffectCloudEntity(
                        attacker.getWorld(),
                        attacker.getX(),
                        attacker.getY(),
                        attacker.getZ(),
                        attacker
                )
        );

        return super.postHit(stack, target, attacker);
    }

    @Override
    public Text getName(ItemStack stack) {
        Style UNIQUE = net.sweenus.simplyswords.util.HelperMethods.getStyle("unique");
        return Text.translatable(this.getTranslationKey(stack)).setStyle(UNIQUE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style textStyle = net.sweenus.simplyswords.util.HelperMethods.getStyle("text");
        Style abilityStyle = net.sweenus.simplyswords.util.HelperMethods.getStyle("ability");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ascended_idol.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
