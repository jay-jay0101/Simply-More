package net.rosemarythyme.simplymore.client.tooltip.motifs;

import net.minecraft.client.gui.DrawContext;
import net.sweenus.simplytooltips.api.BorderPalette;
import net.sweenus.simplytooltips.api.TooltipTheme;
import net.sweenus.simplytooltips.client.render.BorderRenderer;

public class DeathMotif implements FullMotif {
    public static final int ID = 9312_002;

    @Override
    public void draw(DrawContext context, int x, int y, int w, int h, long timeMs) {
        if (w >= 40 && h >= 40) {
            int minX = x + 6;
            int maxX = x + w - 6;
            int minY = y + 2;
            int maxY = y + h - 2;
            int spawnRange = Math.max(10, maxX - minX - 1);

            for (int i = 0; i < 50; ++i) {
                int speedBand = (i * 13 + 5) % 6;
                double speed = 0.009 + (double) speedBand * 0.0011 + (double) i * 1.5E-4;

                int travel = Math.max(8, maxY - minY);

                double fallProgress = (double) timeMs * speed + (double) (i * 11);

                int py = minY + (int) (fallProgress % (double) travel);
                int lane = (i * 29 + i * i * 5) % spawnRange;
                double drift = Math.sin((double) timeMs * 0.0016 + (double) i * 1.1) * (double) 20.0F;

                int px = Math.max(minX, Math.min(maxX, minX + lane + (int) drift));
                int color = 0x1CB08C66;

                BorderRenderer.drawSmallDiamond(context, px, py, color);
            }
        }
    }

    @Override
    public BorderPalette getDefaultPalette() {
        return BorderPalette.accents(0xFF000000);
    }

    @Override
    public void drawBorderPattern(DrawContext context, int x, int y, int w, int h, TooltipTheme theme, BorderPalette palette) {
        int color = palette.accentA() == null ? 0x0 : palette.accentA();

        for (int px = x + 6, i = 0; px < x + (w/2); px += 14, i++) {
            context.fill(px - 2, y + 2, px + 1, y + 3, color);
            context.fill(px - 2, y + 3, px, y + 5, color);
            context.fill(px - 1, y + 5, px + 1, y + 6, color);
            context.fill(px, y + 6, px + 2, y + 7, color);
            context.fill(px + 2, y + 5, px + 3, y + 6, color);
            context.fill(px + 3, y + 4, px + 4, y + 5, color);

            int py = y + h;
            context.fill(px - 2, py - 2, px + 1, py - 3, color);
            context.fill(px - 2, py - 3, px, py - 5, color);
            context.fill(px - 1, py - 5, px + 1, py - 6, color);
            context.fill(px, py - 6, px + 2, py - 7, color);
            context.fill(px + 2, py - 5, px + 3, py - 6, color);
            context.fill(px + 3, py - 4, px + 4, py - 5, color);
        }

        for (int px = x + w - 6, i = 0; px > x + (w/2); px -= 14, i++) {
            context.fill(px + 2, y + 2, px - 1, y + 3, color);
            context.fill(px + 2, y + 3, px, y + 5, color);
            context.fill(px + 1, y + 5, px - 1, y + 6, color);
            context.fill(px, y + 6, px - 2, y + 7, color);
            context.fill(px - 2, y + 5, px - 3, y + 6, color);
            context.fill(px - 3, y + 4, px - 4, y + 5, color);

            int py = y + h;
            context.fill(px + 2, py - 2, px - 1, py - 3, color);
            context.fill(px + 2, py - 3, px, py - 5, color);
            context.fill(px + 1, py - 5, px - 1, py - 6, color);
            context.fill(px, py - 6, px - 2, py - 7, color);
            context.fill(px - 2, py - 5, px - 3, py - 6, color);
            context.fill(px - 3, py - 4, px - 4, py - 5, color);
        }
    }
}
