package net.rosemarythyme.simplymore.client.util;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;

public class RenderUtils {
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
