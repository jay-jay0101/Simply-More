package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.LightningPointEntity;
import net.rosemarythyme.simplymore.item.uniques.StasisItem;
import net.rosemarythyme.simplymore.util.MathUtils;
import org.joml.Vector3f;

public class LightningPointEntityRenderer extends EntityRenderer<LightningPointEntity> {
    public LightningPointEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(LightningPointEntity entity, float yaw, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light) {
        stack.push();

        float scale = MathUtils.clampedLerp(entity.getAge(), -entity.getIntroTicks(), 0, 0f, (float) StasisItem.SETTINGS.radius * 2 + 0.1f);
        stack.translate(0, 0.05f, 0);

        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) entity.getAge() % 360));

        stack.scale(scale, 1, scale);

        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getItemEntityTranslucentCull(
                getTexture(entity)));

        Vector3f pos1 = new Vector3f(-0.5f, 0, -0.5f);
        Vector3f pos2 = new Vector3f(0.5f, 0, -0.5f);
        Vector3f pos3 = new Vector3f(0.5f, 0, 0.5f);
        Vector3f pos4 = new Vector3f(-0.5f, 0, 0.5f);

        Vector3f edge1 = new Vector3f(pos3).sub(pos1);
        Vector3f edge2 = new Vector3f(pos4).sub(pos1);
        Vector3f normal = edge1.cross(edge2).normalize();
        Vector3f invertedNormal = new Vector3f(normal).mul(-1);

        MatrixStack.Entry matrix = stack.peek();

        addVertex(vc, matrix, pos1, 0, 1, normal);
        addVertex(vc, matrix, pos2, 1, 1, normal);
        addVertex(vc, matrix, pos3, 1, 0, normal);
        addVertex(vc, matrix, pos4, 0, 0, normal);
        addVertex(vc, matrix, pos4, 0, 0, invertedNormal);
        addVertex(vc, matrix, pos3, 1, 0, invertedNormal);
        addVertex(vc, matrix, pos2, 1, 1, invertedNormal);
        addVertex(vc, matrix, pos1, 0, 1, invertedNormal);

        stack.pop();
    }

    public static void addVertex(VertexConsumer vc, MatrixStack.Entry matrix, Vector3f pos, float u, float v, Vector3f normal) {
        vc.vertex(matrix, pos).color(0xFFFFFFFF).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, normal.x, normal.y, normal.z);
    }


    @Override
    public boolean shouldRender(LightningPointEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }

    @Override
    public Identifier getTexture(LightningPointEntity entity) {
        return SimplyMore.identifier("textures/entity/stasis_aura.png");
    }
}