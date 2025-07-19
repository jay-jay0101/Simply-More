package net.rosemarythyme.simplymore.item.uniques.idols;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.entity.AuraOfCorruptionAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class TarnishedIdolItem extends SimplyMoreUniqueSwordItem {

    public TarnishedIdolItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect.darksent");
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
                ),
                effect.darksent.chance
        );

        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip2").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.tarnished_idol.tooltip6").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }
}
