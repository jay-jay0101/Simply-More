package net.rosemarythyme.simplymore.client.tooltip.motifs;

import net.minecraft.client.gui.DrawContext;
import net.sweenus.simplytooltips.api.BorderPalette;
import net.sweenus.simplytooltips.api.TooltipTheme;
import net.sweenus.simplytooltips.client.render.border.BorderPattern;
import net.sweenus.simplytooltips.client.render.motif.BackgroundMotif;

public interface FullMotif extends BackgroundMotif {
    void drawBorderPattern(DrawContext context, int x, int y, int w, int h, TooltipTheme theme, BorderPalette palette);
    BorderPalette getDefaultPalette();

    default BorderPattern getPattern() {
        return new BorderPattern() {
            @Override
            public void draw(DrawContext drawContext, int x, int y, int w, int h, TooltipTheme tooltipTheme, BorderPalette borderPalette) {
                drawBorderPattern(drawContext, x, y, w, h, tooltipTheme, borderPalette);
            }

            @Override
            public BorderPalette defaultPalette(TooltipTheme tooltipTheme) {
                return getDefaultPalette();
            }
        };
    }
}
