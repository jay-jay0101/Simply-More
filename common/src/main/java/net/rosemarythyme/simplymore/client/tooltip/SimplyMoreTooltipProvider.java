package net.rosemarythyme.simplymore.client.tooltip;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.registry.TagRegistry;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.client.tooltip.SimplySwordsTooltipProvider;
import net.sweenus.simplyswords.item.component.AwakeningComponent;
import net.sweenus.simplytooltips.api.*;

import java.util.List;

public class SimplyMoreTooltipProvider implements TooltipProvider {
    private final SimplySwordsTooltipProvider BASE = new SimplySwordsTooltipProvider();

    @Override
    public boolean supports(ItemStack itemStack) {
        return itemStack.isIn(TagRegistry.UNIQUE);
    }

    @Override
    public ModernTooltipModel build(ItemStack stack, List<Text> tooltip, boolean isAltPressed) {
        ModernTooltipModel base = BASE.build(stack, tooltip, isAltPressed);
        ItemFrameProgress progress = null;
        if(AwakeningApi.usesAwakeningProgression(stack)) {
            int level = AwakeningApi.getLevel(stack);
            progress = new ItemFrameProgress(
                level,
                AwakeningComponent.MAX_LEVEL,
                0xFF74E7FF,
                0xFFFFFF,
                Text.literal(String.valueOf(level))
            );
        }

        return new ModernTooltipModel(
                base.title(),
                base.badges(),
                base.borderStyle(),
                base.abilityLines(),
                base.bodyLines(),
                base.extraLines(),
                base.theme(),
                base.upgradeSection(),
                base.animKeyExtra(),
                base.themeKey(),
                base.hint(),
                base.affixLines(),
                progress
        );
    }
}
