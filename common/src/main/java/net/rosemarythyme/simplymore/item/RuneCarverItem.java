package net.rosemarythyme.simplymore.item;

import me.fzzyhmstrs.fzzy_config.util.ValidationResult;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.rosemarythyme.simplymore.SimplyMore;
import net.sweenus.simplyswords.power.GemPowerComponent;
import net.sweenus.simplyswords.power.GemPowerFiller;
import net.sweenus.simplyswords.registry.GemPowerRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class RuneCarverItem extends Item implements GemPowerFiller {

    Types type;

    public RuneCarverItem(Settings settings, Types type) {
        super(settings);
        this.type = type;
    }

    @Override
    public Text getName(ItemStack stack) {
        Style RUNIC = type == Types.RUNEFUSED ? Styles.RUNIC : Styles.LEGENDARY;
        return Text.translatable(this.getTranslationKey(stack)).setStyle(RUNIC);
    }


    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType tooltipType) {
        tooltip.add(Text.literal(""));
        if (type == Types.RUNEFUSED) {
            tooltip.add(Text.translatable("item.simplymore.runefused_carver.tooltip1").formatted(Formatting.GRAY, Formatting.ITALIC));
            tooltip.add(Text.translatable("item.simplymore.runefused_carver.tooltip2").formatted(Formatting.GRAY, Formatting.ITALIC));
        } else {
            tooltip.add(Text.translatable("item.simplymore.netherfused_carver.tooltip1").formatted(Formatting.GRAY, Formatting.ITALIC));
            tooltip.add(Text.translatable("item.simplymore.netherfused_carver.tooltip2").formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    @Override
    public ValidationResult<GemPowerComponent> fill(ItemStack stack, GemPowerComponent component) {
        SimplyMore.LOGGER.info(component.toString());
        if (type == Types.RUNEFUSED) {
            return !(component.hasRunicPower() || component.runicPower() == GemPowerRegistry.EMPTY) ?
                    ValidationResult.Companion.success(new GemPowerComponent(true, component.hasNetherPower(), component.runicPower(), component.netherPower())) :
                    ValidationResult.Companion.error(component, "Can't add runic socket to the provided component");
        } else {
            return !(component.hasNetherPower() || component.netherPower() == GemPowerRegistry.EMPTY) ?
                    ValidationResult.Companion.success(new GemPowerComponent(component.hasRunicPower(), true, component.runicPower(), component.netherPower())) :
                    ValidationResult.Companion.error(component, "Can't add nether socket to the provided component");
        }
    }

    public enum Types {
        RUNEFUSED,
        NETHERFUSED
    }
}