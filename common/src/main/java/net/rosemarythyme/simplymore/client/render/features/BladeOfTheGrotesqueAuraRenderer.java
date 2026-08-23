package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import org.joml.Vector3f;

public class BladeOfTheGrotesqueAuraRenderer {
    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, int light) {
        stack.push();

        float scale = (float) BladeOfTheGrotesqueItem.SETTINGS.auraRange * 2 + 0.25f;
        stack.translate(0, 0.05f, 0);

        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) entity.age % 360));

        stack.scale(scale, 1, scale);

        VertexConsumer vc = vertexConsumerProvider.getBuffer(RenderLayer.getItemEntityTranslucentCull(
                SimplyMore.identifier("textures/entity/blade_of_the_grotesque_aura.png")));

        Vector3f pos1 = new Vector3f(-0.5f, 0, -0.5f);
        Vector3f pos2 = new Vector3f(0.5f, 0, -0.5f);
        Vector3f pos3 = new Vector3f(0.5f, 0, 0.5f);
        Vector3f pos4 = new Vector3f(-0.5f, 0, 0.5f);

        Vector3f edge1 = new Vector3f(pos3).sub(pos1);
        Vector3f edge2 = new Vector3f(pos4).sub(pos1);
        Vector3f normal = edge1.cross(edge2).normalize();
        Vector3f invertedNormal = new Vector3f(normal).mul(-1);

        MatrixStack.Entry matrix = stack.peek();

        addVertex(vc, matrix, pos1, 0, 1, normal, light);
        addVertex(vc, matrix, pos2, 1, 1, normal, light);
        addVertex(vc, matrix, pos3, 1, 0, normal, light);
        addVertex(vc, matrix, pos4, 0, 0, normal, light);
        addVertex(vc, matrix, pos4, 0, 0, invertedNormal, light);
        addVertex(vc, matrix, pos3, 1, 0, invertedNormal, light);
        addVertex(vc, matrix, pos2, 1, 1, invertedNormal, light);
        addVertex(vc, matrix, pos1, 0, 1, invertedNormal, light);

        stack.pop();
    }

    public static void addVertex(VertexConsumer vc, MatrixStack.Entry matrix, Vector3f pos, float u, float v, Vector3f normal, int light) {
        vc.vertex(matrix, pos).color(0xFFFFFFFF).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, normal.x, normal.y, normal.z);
    }

}
