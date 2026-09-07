package net.rosemarythyme.simplymore.client.tooltip.motifs;

import net.minecraft.client.gui.DrawContext;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.sweenus.simplytooltips.api.BorderPalette;
import net.sweenus.simplytooltips.api.TooltipTheme;
import net.sweenus.simplytooltips.client.render.BorderRenderer;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class DeathMotif implements FullMotif {
    @Override
    public void draw(DrawContext context, int x, int y, int w, int h, long timeMs) {
        drawDust(context, new RenderUtils.Bound(x, y, w, h), timeMs);

        float delta = (float) Math.sin((timeMs % 360000) / 200f);
        int topLuminance = Math.round(MathUtils.clampedLerp(delta, -1, 1, 0xAA, 0xE0));
        int bottomLuminance = Math.round(MathUtils.clampedLerp(delta, -1, 1, 0x10, 0x25));

        float rise = (float) Math.sin((timeMs % 360000) / 800f);
        int startHeight = Math.round(rise) + Math.max(0, Math.round(h / 2f) - 40);

        int halfWidth = Math.round(w / 2f);
        int centerX = x + halfWidth;

        for(int i = startHeight; i < h; i++) {
            int length = Math.round(Math.min(MathUtils.clampedLerp(i, startHeight, h, 1, halfWidth) + 6, halfWidth));
            int luminance = Math.round(MathUtils.clampedLerp(i, startHeight, h, topLuminance, bottomLuminance));
            int color = (0x75 << 24) | (luminance << 16) | (luminance << 8) | luminance;
            context.fill(centerX - length, y + i, centerX + length, y + i + 1, color);
        }

        RenderUtils.drawPlanet(context, new Vector2i(centerX, y + startHeight + 20), new Vector2f(-0.5f, -0.5f), 40,
                0xFF101010, 0xFF303030, 0xFF505050, 0xFF808080, 0x75000000, 0x75262626,
                new RenderUtils.Bound(x, y, w, h));
    }

    public void drawDust(DrawContext context, RenderUtils.Bound bound, long timeMs) {
        final int color = 0x1CB08C66;

        int minX = bound.x() + 6, maxX = bound.x() + bound.width() - 6, minY = bound.y() + 6, maxY = bound.y() + bound.height() - 6;
        int spawnRange = Math.max(12, (maxX - minX) - 6);
        for (int i = 0; i < 32; i++) {
            double fallSpeed = 0.01 + i * 0.001;
            int travel = Math.max(10, maxY - minY);

            double fallProgress = (timeMs * fallSpeed) + i * 23;
            int py = minY + (int) (fallProgress % travel);
            int lane = (i * 65 + i * i * 7) % spawnRange;

            double swayA = Math.sin((timeMs * 0.0015) + i * 1.6) * (bound.width() * 0.1);
            double swayB = Math.sin((timeMs * 0.0007) + i * 0.8) * 1.5;

            int baseX = minX + lane + (int) (swayA + swayB);
            int px = Math.max(minX, Math.min(maxX - 4, baseX));

            BorderRenderer.drawSmallDiamond(context, px, py, color);
        }
    }

    @Override
    public BorderPalette getDefaultPalette() {
        return BorderPalette.accents(0xFF000000);
    }

    @Override
    public void drawBorderPattern(DrawContext context, int x, int y, int w, int h, TooltipTheme theme, BorderPalette palette) {
        int color = palette.accentA() == null ? 0x0 : palette.accentA();

        int centerX = Math.round(x + w / 2f);
        int halfWidth = centerX - (x + 6);

        int py = y + h - 2;
        drawHalf(context, centerX - halfWidth, centerX - 9, py, color);
        drawHalf(context, centerX + halfWidth, centerX + 9, py, color);
        drawMausoleum(context, centerX, py, color);

    }

    public static void drawMausoleum(DrawContext context, int x, int y, int color) {
        context.fill(x - 6, y, x + 6, y - 1, color);
        context.fill(x - 6, y - 1, x - 5, y - 7, color);
        context.fill(x - 4, y - 1, x - 2, y - 7, color);
        context.fill(x + 2, y - 1, x + 4, y - 7, color);
        context.fill(x + 5, y - 1, x + 6, y - 7, color);

        context.fill(x - 2, y - 5, x + 2, y - 6, color);
        context.fill(x - 6, y - 7, x + 6, y - 8, color);
        context.fill(x - 7, y - 8, x + 7, y - 9, color);
        context.fill(x - 2, y - 9, x - 1, y - 12, color);
        context.fill(x + 1, y - 9, x + 2, y - 12, color);
        context.fill(x - 1, y - 12, x + 1, y - 14, color);

        context.fill(x - 6, y - 9, x - 5, y - 10, color);
        context.fill(x - 5, y - 9, x - 4, y - 11, color);
        context.fill(x - 4, y - 10, x - 3, y - 12, color);
        context.fill(x - 3, y - 11, x - 2, y - 13, color);
        context.fill(x - 2, y - 12, x - 1, y - 14, color);

        context.fill(x + 5, y - 9, x + 6, y - 10, color);
        context.fill(x + 4, y - 9, x + 5, y - 11, color);
        context.fill(x + 3, y - 10, x + 4, y - 12, color);
        context.fill(x + 2, y - 11, x + 3, y - 13, color);
        context.fill(x + 1, y - 12, x + 2, y - 14, color);
    }

    public static void drawHalf(DrawContext context, int x, int x2, int y, int color) {
        int direction = (int) Math.signum(x2 - x);
        if(direction == 0) return;

        boolean cross = false;
        for (int px = x; direction == 1 ? px < x2 : px > x2; px += 8 * direction, cross = !cross) {
            if(cross) {
                context.fill(px-3, y, px+3, y-2, color);
                context.fill(px-2, y-2, px+2, y-3, color);
                context.fill(px-1, y-3, px+1, y-8, color);
                context.fill(px-3, y-4, px+3, y-6, color);
            } else {
                context.fill(px-3, y, px+3, y-7, color);
                context.fill(px-2, y-7, px+2, y-8, color);
            }
        }
    }
}
