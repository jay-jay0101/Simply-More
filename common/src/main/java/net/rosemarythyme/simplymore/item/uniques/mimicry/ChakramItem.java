package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;

import java.util.List;

public class ChakramItem extends MimicryItem {
    public ChakramItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        player.addStatusEffect(
                new StatusEffectInstance(
                        StatusEffects.HASTE,
                        10,
                        6
                )
        );

        player.addStatusEffect(
                new StatusEffectInstance(
                        StatusEffects.SPEED,
                        10,
                        0
                )
        );

        if(ticksUsed >= mimicry.chakram.duration) {
            player.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicry.chakram.disabled;
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.chakram");
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.chakram.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.chakram.tooltip2").setStyle(textStyle));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MIMICRY_CHAKRAM));
        }

        public boolean disabled = false;
        @ValidatedInt.Restrict(min = 0)
        public int duration = 60;
    }
}
