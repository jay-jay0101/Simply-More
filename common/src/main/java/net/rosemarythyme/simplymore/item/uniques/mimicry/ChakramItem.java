package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

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

        if(ticksUsed >= mimicryConfig.chakram.duration) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryConfig.chakram.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.chakram.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_CHAKRAM));
        }

        public boolean disabled = false;
        @ValidatedInt.Restrict(min = 0)
        public int duration = 60;
    }
}
