package net.rosemarythyme.simplymore.client.tooltip.motifs;

import net.minecraft.client.gui.DrawContext;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.sweenus.simplytooltips.client.render.motif.BackgroundMotif;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class MimicryMotif implements BackgroundMotif {

    @Override
    public void draw(DrawContext context, int x, int y, int w, int h, long timeMs) {
        RenderUtils.Bound bound = new RenderUtils.Bound(x, y, w, h);

        drawVine(context, new Vector2f(0f, 0f), new Vector2f(1f, 1f), 50, 20, bound, timeMs);
        drawVine(context, new Vector2f(0f, 1f), new Vector2f(1f, 0f), 100, -65, bound, timeMs);
        drawVine(context, new Vector2f(0.25f, 0f), new Vector2f(0.8f, 1f), 10, 49, bound, timeMs);
        drawVine(context, new Vector2f(0.6f, 0f), new Vector2f(0.41f, 1f), -62, 24, bound, timeMs);
        drawVine(context, new Vector2f(0f, 0.4f), new Vector2f(1f, 0.10f), 43, -40, bound, timeMs);
    }

    private static void drawVine(DrawContext context, Vector2f localStart, Vector2f localEnd, float amp1, float amp2, RenderUtils.Bound bound, long timeMs) {
        Vector2i start = new Vector2i(Math.round(localStart.x * bound.width() + bound.x()), Math.round(localStart.y * bound.height() + bound.y()));
        Vector2i end = new Vector2i(Math.round(localEnd.x * bound.width() + bound.x()), Math.round(localEnd.y * bound.height() + bound.y()));

        final int innerColor = 0xAAC3323C;
        final int outerColor = 0xAA6E1219;
        final int width = 2;

        float delta = (timeMs % 10800000) / 50000f;
        float ampMult1 = 1f + ((float) Math.sin(delta * amp2) * 0.2f);
        float interimXOffset = ((float) Math.sin(delta * amp2 + 40) * 0.2f) * 50;
        float ampMult2 = 1f + ((float) Math.cos(delta * amp1 + 150) * 0.2f);
        float interimYOffset = ((float) Math.sin(delta * amp1 + 120) * 0.2f) * 50;

        Vector2i interimPos = new Vector2i(Math.round((start.x + end.x + interimXOffset) / 2f), Math.round((start.y + end.y + interimYOffset) / 2f));

        drawHalfSine(context, start, interimPos, width, amp1 * ampMult1, outerColor, bound);
        drawHalfSine(context, start, interimPos, width - 1, amp1 * ampMult1, innerColor, bound);
        drawHalfSine(context, interimPos, end, width, -amp1 * ampMult2, outerColor, bound);
        drawHalfSine(context, interimPos, end, width - 1, -amp1 * ampMult2, innerColor, bound);
    }

    private static void drawHalfSine(DrawContext context, Vector2i start, Vector2i end, int width, float amplifier, int color, RenderUtils.Bound bound) {
        Vector2i currentPos = start;

        for(float d = 0; d <= 1f; d += 0.005f) {
            Vector2i newPos = getPositionOnHalfSine(start, end, amplifier, d);

            if(!newPos.equals(currentPos)) {
                currentPos = newPos;

                RenderUtils.Bound draw = new RenderUtils.Bound(newPos.x - width, newPos.y - width, width * 2, width * 2).clampWithin(bound);

                if(draw.height() > 0 && draw.width() > 0) {
                    context.fill(draw.x(), draw.y(), draw.x() + draw.width(), draw.y() + draw.height(), color);
                }
            }
        }
    }

    private static Vector2i getPositionOnHalfSine(Vector2i start, Vector2i end, float amplifier, float progress) {
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        double length = Math.sqrt((dx * dx) + (dy * dy));

        long x = Math.round(start.x + (progress * dx) - (amplifier * (dy/length) * Math.sin(Math.PI * progress)));
        long y = Math.round(start.y + (progress * dy) + (amplifier * (dx/length) * Math.sin(Math.PI * progress)));

        return new Vector2i((int)x, (int)y);
    }
}
