package net.rosemarythyme.simplymore.client.tooltip.motifs;

import net.minecraft.client.gui.DrawContext;
import net.sweenus.simplytooltips.client.render.TooltipPainter;
import net.sweenus.simplytooltips.client.render.motif.BackgroundMotif;

public class BloodMotif implements BackgroundMotif {
    public static final int ID = 9312_003;

    @Override
    public void draw(DrawContext context, int x, int y, int w, int h, long timeMs) {
        if (w > 0 && h > 0) {
            int shift = (int)(timeMs / 22L % w);
            int left = 0xF0060000;
            int right = 0xF0130000;

            for(int row = 0; row < h; ++row) {
                int u = (row + shift) % w;
                float phase = u / (float) w;
                float t = 0.5F + 0.5F * (float) Math.sin(phase * Math.PI * 8.0F);

                int color = TooltipPainter.lerpColor(left, right, t);
                context.fill(x, y + row, x + w, y + row + 1, color);
            }

        }
    }

    public void drawBorderPattern(DrawContext context, int x, int y, int w, int h) {
        int color = 0xC0220000;
        for (int px = x + 8, i = 0; px < x + w - 8; px += 3, i = ++i % 3) {
            switch(i) {
                case 0 -> {
                    context.fill(px, y + 2, px + 1, y + 4, color);
                    context.fill(px + 1, y + 3, px + 2, y + 5, color);

                    context.fill(px, y - 2 + h, px + 1, y - 4 + h, color);
                    context.fill(px + 1, y - 3 + h, px + 2, y - 5 + h, color);
                }
                case 1 -> {
                    context.fill(px, y + 2, px + 1, y + 5, color);

                    context.fill(px, y - 2 + h, px + 1, y - 5 + h, color);
                }
                case 2 -> {
                    context.fill(px, y + 2, px + 1, y + 4, color);
                    context.fill(px - 1, y + 3, px, y + 5, color);

                    context.fill(px, y - 2 + h, px + 1, y - 4 + h, color);
                    context.fill(px - 1, y - 3 + h, px, y - 5 + h, color);
                }
            }
        }
    }
}
