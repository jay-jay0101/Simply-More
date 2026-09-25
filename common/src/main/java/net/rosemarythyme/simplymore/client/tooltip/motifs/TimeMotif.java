package net.rosemarythyme.simplymore.client.tooltip.motifs;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.sweenus.simplytooltips.client.render.motif.BackgroundMotif;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class TimeMotif implements BackgroundMotif {
    @Override
    public void draw(DrawContext context, int x, int y, int w, int h, long timeMs) {
        RenderUtils.Bound bound = new RenderUtils.Bound(x, y, w, h);

        int halfHeight = Math.round(h / 2f);
        int centerY = y + halfHeight;

        int halfWidth = Math.round(w / 2f);
        int centerX = x + halfWidth;

        drawSunAndMoon(context, centerX, centerY, w, bound, timeMs);
    }

    private static void drawSunAndMoon(DrawContext context, int centerX, int centerY, int width, RenderUtils.Bound bound, long timeMs) {
        int radius = Math.round(width / 4.5f);
        float rot = (timeMs % 36000) / 100f;

        Vector2i center = new Vector2i(centerX, centerY);
        Vec3d sunOffset = MathUtils.getNormalised2dVector(rot);

        float offset = width / 3f;
        Vector2i sunOffsetPos = new Vector2i(
                (int) Math.round(sunOffset.getX() * offset),
                (int) Math.round(sunOffset.getZ() * offset)
        );

        RenderUtils.drawPlanet(context, new Vector2i(center).add(sunOffsetPos), new Vector2f(0f, 0f), radius, 0xFFFC9601, 0xFFFC9601, 0xFFFFCC33, 0xFFFFE484, bound);

        Vector2f lightPos = new Vector2f(
                (float) sunOffset.getX() * 0.75f,
                (float) sunOffset.getZ() * 0.75f
        );
        RenderUtils.drawPlanet(context, new Vector2i(center).sub(sunOffsetPos), lightPos, radius, 0xFF101010, 0xFF303030, 0xFF505050, 0xFF808080, 0x75000000, 0x75262626, bound);
    }
}
