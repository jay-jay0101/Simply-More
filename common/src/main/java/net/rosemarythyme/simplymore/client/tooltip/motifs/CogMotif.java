package net.rosemarythyme.simplymore.client.tooltip.motifs;

import net.minecraft.client.gui.DrawContext;
import net.sweenus.simplytooltips.client.render.motif.BackgroundMotif;

public class CogMotif implements BackgroundMotif {
    public static final int ID = 9312_001;

    @Override
    public void draw(DrawContext context, int x, int y, int width, int height, long timeMs) {
        float rot = (timeMs % 3600) / 10f;

        drawCog(context, x + width - 60, y + (int) Math.floor(height*0.2f), 20, 7, 7, rot + 45);
        drawCog(context, x + 40, y + (int) Math.floor(height*0.4f), 15, 5, 5, rot);
        drawCog(context, x + width - 30, y + (int) Math.floor(height*0.5f), 10, 3, 4, rot + 80);
        drawCog(context, x + (width/2), y + (int) Math.floor(height*0.6f), 15, 5, 5, rot + 20);
        drawCog(context, x + 50, y + (int) Math.floor(height*0.8f), 10, 3, 4, rot + 80);
    }

    public void drawBorderPattern(DrawContext context, int x, int y, int width, int height) {
        int cogColor = 0xC0C15A36, armColor = 0xA04A1300;
        for (int px = x + 12, i = 0; px < x + width - 10; px += 14, i++) {
            context.fill(px - 1, y + 1, px, y + 3, cogColor);
            context.fill(px, y + 2, px + 1, y + 3, cogColor);
            context.fill(px + 1, y + 1, px + 2, y + 3, cogColor);

            context.fill(px - 2, y + 1, px - 1, y + 2, armColor);
            context.fill(px - 2, y + 3, px - 1, y + 4, armColor);
            context.fill(px, y + 3, px + 1, y + 4, armColor);
            context.fill(px + 2, y + 1, px + 3, y + 2, armColor);
            context.fill(px + 2, y + 3, px + 3, y + 4, armColor);

            context.fill(px + 1, y - 1 + height, px, y - 3 + height, cogColor);
            context.fill(px, y - 2 + height, px - 1, y - 3 + height, cogColor);
            context.fill(px - 1, y - 1 + height, px - 2, y - 3 + height, cogColor);

            context.fill(px + 2, y - 1 + height, px + 1, y - 2 + height, armColor);
            context.fill(px + 2, y - 3 + height, px + 1, y - 4 + height, armColor);
            context.fill(px, y - 3 + height, px - 1, y - 4 + height, armColor);
            context.fill(px - 2, y - 1 + height, px - 3, y - 2 + height, armColor);
            context.fill(px - 2, y - 3 + height, px - 3, y - 4 + height, armColor);
        }
    }


    public void drawCog(DrawContext context, int cx, int cy, int radius, int innerRadius, int armSize, float rot) {
        final int ARMS = 6;
        final int COLOR = 0xC0C15A36;
        final int BORDER_COLOR = 0xA04A1300;

        for(int i = 0; i < ARMS; i++) {
            float gRot = rot + (360 * ((float) i / ARMS));

            double a = Math.toRadians(gRot);
            double s = Math.sin(a);
            double c = Math.cos(a);

            int rx = (int) Math.ceil(radius * c);
            int ry = (int) Math.ceil(radius * s);

            drawRotatedSquare(context, cx + rx, cy + ry, armSize, -gRot, BORDER_COLOR);
        }

        for(int dx = -(radius - 1); dx <= radius - 1; dx++) {
            int dy = (int) Math.ceil(Math.sqrt((radius * radius) - (dx * dx)));
            int dy2 = (int) Math.ceil(Math.sqrt((innerRadius * innerRadius) - (dx * dx)));

            if(dy2 > 0) {
                context.fill(cx + dx, cy + dy, cx + dx + 1, cy + dy2, COLOR);
                context.fill(cx + dx, cy - dy + 1, cx + dx + 1, cy - dy2 + 1, COLOR);
            } else {
                context.fill(cx + dx, cy + dy, cx + dx + 1, (cy - dy) + 1, COLOR);
            }
        }
    }

    public void drawRotatedSquare(DrawContext context, int cx, int cy, int radius, float rot, int color) {
        double a = Math.toRadians(rot);
        double s = Math.sin(a);
        double c = Math.cos(a);
        int w = (int) Math.ceil(radius * (Math.abs(s) + Math.abs(c)));

        for(int dx = -w; dx <= w; dx++) {
            int dy1 = 0, dy2 = 0;

            for(int py = w; py >= 0; py--) {
                if(dy1 != 0 && dy2 != 0) break;

                if(dy1 == 0) {
                    double rx = (dx * c) + (py * s);
                    double ry = (dx * s) - (py * c);

                    if(Math.abs(rx) <= radius
                            && Math.abs(ry) <= radius) dy1 = py;
                }

                if(dy2 == 0) {
                    double rx = (dx * c) - (py * s);
                    double ry = (dx * s) + (py * c);

                    if(Math.abs(rx) <= radius
                            && Math.abs(ry) <= radius) dy2 = py;
                }
            }

            context.fill(cx + dx, cy - dy1, cx + dx + 1, cy + dy2, color);
        }
    }


}
