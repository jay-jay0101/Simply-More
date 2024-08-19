package net.rosemarythyme.simplymore.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class RuneCarverItem extends Item {

    String type;

    public RuneCarverItem(Settings settings, String type) {
        super(settings);
        this.type = type;
    }

    @Override
    public Text getName(ItemStack stack) {
        Style RUNIC = type.equals("runic")? HelperMethods.getStyle("runic") : HelperMethods.getStyle("legendary");
        return Text.translatable(this.getTranslationKey(stack)).setStyle(RUNIC);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        switch (type) {
            case "runic":
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.runefused_carver.tooltip1").formatted(Formatting.GRAY, Formatting.ITALIC));
                tooltip.add(Text.translatable("item.simplymore.runefused_carver.tooltip2").formatted(Formatting.GRAY, Formatting.ITALIC));
                break;
            case "nether":
                tooltip.add(Text.literal(""));
                tooltip.add(Text.translatable("item.simplymore.netherfused_carver.tooltip1").formatted(Formatting.GRAY, Formatting.ITALIC));
                tooltip.add(Text.translatable("item.simplymore.netherfused_carver.tooltip2").formatted(Formatting.GRAY, Formatting.ITALIC));
                break;
        }
    }

}