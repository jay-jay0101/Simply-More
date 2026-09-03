package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.entity.EarthquakeVisualEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

public class EarthquakeEntityRenderer extends EntityRenderer<EarthquakeVisualEntity> {
    public EarthquakeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(EarthquakeVisualEntity entity, float yaw, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light) {
        float scale = MathUtils.clampedLerp(entity.getAge(), -entity.getLifespan(), 0, 0f, 16f);
        RenderUtils.drawFloorPlane(vertexConsumers, stack, getTexture(entity), LightmapTextureManager.MAX_LIGHT_COORDINATE, RenderUtils.whiteWithOpacity(getOpacity(entity)), scale);
    }

    @Override
    public boolean shouldRender(EarthquakeVisualEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }

    @Override
    public Identifier getTexture(EarthquakeVisualEntity entity) {
        int num = (int) Math.floor(MathUtils.clampedLerp(entity.getAge(), -entity.getLifespan(), 0, 0, 9));

        return SimplyMore.identifier("textures/entity/planes/earthquake/earthquake_stage_" + num + ".png");
    }

    public int getOpacity(EarthquakeVisualEntity entity) {
        return (int) Math.floor(MathUtils.clampedLerp(entity.getAge(), entity.getLifespan(), entity.getLifespan() + entity.getOutroTicks(), 0xFF, 0x00));
    }
}