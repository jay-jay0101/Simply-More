package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.EarthquakeVisualEntity;
import net.rosemarythyme.simplymore.util.MathUtils;
import org.joml.Vector3f;

public class EarthquakeEntityRenderer extends EntityRenderer<EarthquakeVisualEntity> {
    public EarthquakeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EarthquakeVisualEntity entity, float yaw, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light) {
        stack.push();

        stack.translate(0, 0.05f, 0);

        float scale = MathUtils.clampedLerp(entity.getAge(), -entity.getLifespan(), 0, 0f, 16f);
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

        int opacity = getOpacity(entity);

        addVertex(vc, matrix, pos1, 0, 1, normal, opacity);
        addVertex(vc, matrix, pos2, 1, 1, normal, opacity);
        addVertex(vc, matrix, pos3, 1, 0, normal, opacity);
        addVertex(vc, matrix, pos4, 0, 0, normal, opacity);
        addVertex(vc, matrix, pos4, 0, 0, invertedNormal, opacity);
        addVertex(vc, matrix, pos3, 1, 0, invertedNormal, opacity);
        addVertex(vc, matrix, pos2, 1, 1, invertedNormal, opacity);
        addVertex(vc, matrix, pos1, 0, 1, invertedNormal, opacity);

        stack.pop();
    }

    public static void addVertex(VertexConsumer vc, MatrixStack.Entry matrix, Vector3f pos, float u, float v, Vector3f normal, int opacity) {
        vc.vertex(matrix, pos).color(0x80, 0x80, 0x80, opacity).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE).normal(matrix, normal.x, normal.y, normal.z);
    }


    @Override
    public boolean shouldRender(EarthquakeVisualEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }

    @Override
    public Identifier getTexture(EarthquakeVisualEntity entity) {
        int num = (int) Math.floor(MathUtils.clampedLerp(entity.getAge(), -entity.getLifespan(), 0, 0, 9));

        return SimplyMore.identifier("textures/entity/earthquake_stage_" + num + ".png");
    }

    public int getOpacity(EarthquakeVisualEntity entity) {
        return (int) Math.floor(MathUtils.clampedLerp(entity.getAge(), entity.getLifespan(), entity.getLifespan() + entity.getOutroTicks(), 0xFF, 0x00));
    }
}