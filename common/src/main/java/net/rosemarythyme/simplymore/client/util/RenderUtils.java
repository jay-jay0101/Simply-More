package net.rosemarythyme.simplymore.client.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class RenderUtils {
    public record Bound(int x, int y, int width, int height) {

        public Bound() {
            this(-0xFFFF, -0xFFFF, 0xFFFF * 2, 0xFFFF * 2);
        }

        public Bound clampWithin(Bound other) {
            int x1 = Math.max(Math.min(x, x + width),
                    Math.min(other.x, other.x + other.width));

            int x2 = Math.min(Math.max(x, x + width),
                    Math.max(other.x, other.x + other.width));

            int y1 = Math.max(Math.min(y, y + height),
                    Math.min(other.y, other.y + other.height));

            int y2 = Math.min(Math.max(y, y + height),
                    Math.max(other.y, other.y + other.height));

            return new Bound(
                    x1,
                    y1,
                    Math.max(0, x2 - x1),
                    Math.max(0, y2 - y1)
            );
        }
    }

    public static void drawPlanet(DrawContext context, Vector2i pos, Vector2f lightPos, int radius, int borderColor, int shadeColor, int normalColor, int highlightColor, Bound bound) {
        drawPlanet(context, pos, lightPos, radius, borderColor, shadeColor, normalColor, highlightColor, null, null, bound);
    }

    public static void drawPlanet(DrawContext context, Vector2i pos, Vector2f lightPos, int radius, int borderColor, int shadeColor, int normalColor, int highlightColor, Integer craterColor, Integer innerCraterColor, Bound bound) {
        int innerRadius = radius - 2;

        drawCircle(context, pos.x, pos.y, radius, borderColor, bound);
        drawCircle(context, pos.x, pos.y, innerRadius, shadeColor , bound);

        int lx = pos.x + Math.round(radius * lightPos.x);
        int ly = pos.y + Math.round(radius * lightPos.x);

        drawCircleWithinCircle(context, new Vector2i(lx, ly), Math.round(innerRadius * 2 * (2/3f)), pos, innerRadius, normalColor, bound);
        drawCircleWithinCircle(context, new Vector2i(lx, ly), Math.round(innerRadius * 2 * (1/3f)), pos, innerRadius, highlightColor, bound);

        if (craterColor != null) {
            drawCrater(context, pos, radius, new Vector2f( 0.42f, -0.58f), 7, craterColor, innerCraterColor, bound);
            drawCrater(context, pos, radius, new Vector2f(-0.52f, -0.32f), 6, craterColor, innerCraterColor, bound);
            drawCrater(context, pos, radius, new Vector2f(-0.08f, -0.70f), 5, craterColor, innerCraterColor, bound);
            drawCrater(context, pos, radius, new Vector2f( 0.66f, -0.08f), 6, craterColor, innerCraterColor, bound);
            drawCrater(context, pos, radius, new Vector2f(-0.64f,  0.34f), 8, craterColor, innerCraterColor, bound);
            drawCrater(context, pos, radius, new Vector2f( 0.20f,  0.54f), 5, craterColor, innerCraterColor, bound);
            drawCrater(context, pos, radius, new Vector2f( 0.56f,  0.34f), 7, craterColor, innerCraterColor, bound);
            drawCrater(context, pos, radius, new Vector2f(-0.22f,  0.08f), 5, craterColor, innerCraterColor, bound);
        }
    }

    private static void drawCrater(DrawContext context, Vector2i planetPos, int planetRadius, Vector2f position, int size, int color, int innerColor, Bound bound) {
        int cx = planetPos.x + Math.round(position.x * planetRadius);
        int cy = planetPos.y + Math.round(position.y * planetRadius);

        int width = Math.max(3, Math.round(size * 0.25f));

        drawHollowCircle(context, cx, cy, size + 1, width, color, bound);
        drawCircle(context, cx, cy, size - 2, innerColor, bound);
    }

    public static void drawHollowCircle(DrawContext context, int x, int y, int radius, int thickness, int color, Bound bound) {
        int innerRadius = Math.max(0, radius - thickness);
        int innerRadiusSquared = innerRadius * innerRadius;
        int radiusSquared = radius * radius;

        for(int dx = 1 - radius; dx <= radius - 1; dx++) {
            int dxSquared = dx * dx;
            int dy = (int) Math.ceil(Math.sqrt(radiusSquared - dxSquared));
            int dy2 = (int) Math.ceil(Math.sqrt(innerRadiusSquared - dxSquared));

            if(dy2 > 0) {
                Bound topDraw = new Bound(x + dx, y + dy, 1, dy2 - dy).clampWithin(bound);
                context.fill(topDraw.x, topDraw.y, topDraw.x + topDraw.width, topDraw.y + topDraw.height, color);

                Bound bottomDraw = new Bound(x + dx, y - dy + 1, 1, dy - dy2).clampWithin(bound);
                context.fill(bottomDraw.x, bottomDraw.y, bottomDraw.x + bottomDraw.width, bottomDraw.y + bottomDraw.height, color);
            } else {
                Bound draw = new Bound(x + dx, y - dy, 1, dy * 2 + 1).clampWithin(bound);
                context.fill(draw.x, draw.y, draw.x + draw.width, draw.y + draw.height, color);
            }
        }
    }

    public static void drawCircle(DrawContext context, int x, int y, int radius, int color, Bound bound) {
        drawHollowCircle(context, x, y, radius, radius, color, bound);
    }

    public static void drawCircleWithinCircle(DrawContext context, Vector2i pos, int radius, Vector2i boundPos, int boundRadius, int color, Bound outerBound) {
        int radiusSquared = radius * radius;
        int boundRadiusSquared = boundRadius * boundRadius;

        for(int dx = 1 - radius ; dx <= radius - 1; dx++) {
            int dxSquared = dx * dx;
            if(dx > radiusSquared) continue;

            int dy = (int) Math.ceil(Math.sqrt(radiusSquared - dxSquared));
            int minY = pos.y - dy;
            int maxY = pos.y + dy;

            int boundDx = pos.x + dx - boundPos.x;
            int boundDxSquared = boundDx * boundDx;
            if(boundDxSquared > boundRadiusSquared) continue;

            int boundDy = (int) Math.ceil(Math.sqrt(boundRadiusSquared - boundDxSquared));
            int boundMinY = boundPos.y - boundDy;
            int boundMaxY = boundPos.y + boundDy;

            int drawMinY = Math.max(minY, boundMinY);
            int drawMaxY = Math.min(maxY, boundMaxY);
            if(drawMinY > drawMaxY) continue;

            Bound draw = new Bound(pos.x + dx, drawMinY, 1, drawMaxY - drawMinY + 1)
                    .clampWithin(outerBound);

            context.fill(draw.x, draw.y, draw.x + draw.width, draw.y + draw.height, color);
        }
    }

    public static void addVertex(VertexConsumer vc, MatrixStack.Entry matrix, Vector3f pos, float u, float v, Vector3f normal, int light, int color) {
        vc.vertex(matrix, pos).color(color).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, normal.x, normal.y, normal.z);
    }

    public static void drawPlane(Vector3f pos1, Vector3f pos2, Vector3f pos3, Vector3f pos4, VertexConsumerProvider provider, MatrixStack stack, Identifier id, int light, int color) {
        VertexConsumer vc = provider.getBuffer(RenderLayer.getItemEntityTranslucentCull(id));

        Vector3f edge1 = new Vector3f(pos3).sub(pos1);
        Vector3f edge2 = new Vector3f(pos4).sub(pos1);
        Vector3f normal = edge1.cross(edge2).normalize();
        Vector3f invertedNormal = new Vector3f(normal).mul(-1);

        MatrixStack.Entry matrix = stack.peek();

        addVertex(vc, matrix, pos1, 0, 1, normal, light, color);
        addVertex(vc, matrix, pos2, 1, 1, normal, light, color);
        addVertex(vc, matrix, pos3, 1, 0, normal, light, color);
        addVertex(vc, matrix, pos4, 0, 0, normal, light, color);
        addVertex(vc, matrix, pos4, 0, 0, invertedNormal, light, color);
        addVertex(vc, matrix, pos3, 1, 0, invertedNormal, light, color);
        addVertex(vc, matrix, pos2, 1, 1, invertedNormal, light, color);
        addVertex(vc, matrix, pos1, 0, 1, invertedNormal, light, color);
    }

    public static void drawFloorPlane(VertexConsumerProvider provider, MatrixStack stack, Identifier id, int light, int color) {
        stack.push();
        stack.translate(0, 0.05f, 0);

        drawPlane(
                new Vector3f(-0.5f, 0, -0.5f), new Vector3f(0.5f, 0, -0.5f),
                new Vector3f(0.5f, 0, 0.5f), new Vector3f(-0.5f, 0, 0.5f),
                provider, stack, id, light, color
        );
        stack.pop();
    }

    public static void drawVerticalPlane(VertexConsumerProvider provider, MatrixStack stack, Identifier id, int light, int color) {
        drawPlane(
                new Vector3f(0, -0.5f, -0.5f), new Vector3f(0, -0.5f,  0.5f),
                new Vector3f(0,  0.5f,  0.5f), new Vector3f(0,  0.5f, -0.5f),
                provider, stack, id, light, color
        );
    }

    public static void drawVerticalPlane(VertexConsumerProvider provider, MatrixStack stack, Identifier id, int light, int color, float scale) {
        stack.push();
        stack.scale(scale, scale, scale);

        drawVerticalPlane(provider, stack, id, light, color);
        stack.pop();
    }

    public static int whiteWithOpacity(int opacity) {
        return (opacity << 24) | 0xFFFFFF;
    }

    public static void drawFloorPlane(VertexConsumerProvider provider, MatrixStack stack, Identifier id, int light, int color, float scale) {
        stack.push();
        stack.scale(scale, 1, scale);
        drawFloorPlane(provider, stack, id, light, color);
        stack.pop();
    }

    public static void applySlowRotation(MatrixStack stack, long delta) {
        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) delta % 360));
    }

    public static void renderTowardsCamera(Camera camera, MatrixStack stack) {
        stack.multiply(camera.getRotation());
        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
    }
}
