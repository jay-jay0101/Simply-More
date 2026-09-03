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
import net.rosemarythyme.simplymore.entity.LightningPointEntity;
import net.rosemarythyme.simplymore.item.uniques.StasisItem;
import net.rosemarythyme.simplymore.util.MathUtils;

public class LightningPointEntityRenderer extends EntityRenderer<LightningPointEntity> {
    public LightningPointEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(LightningPointEntity entity, float yaw, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light) {
        stack.push();
        RenderUtils.applySlowRotation(stack, entity.getAge());

        float scale = MathUtils.clampedLerp(entity.getAge(), -entity.getIntroTicks(), 0, 0f, (float) StasisItem.SETTINGS.radius * 2 + 0.1f);
        RenderUtils.drawFloorPlane(vertexConsumers, stack, getTexture(entity), LightmapTextureManager.MAX_LIGHT_COORDINATE, 0xFFFFFFFF, scale);

        stack.pop();
    }

    @Override
    public boolean shouldRender(LightningPointEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }

    @Override
    public Identifier getTexture(LightningPointEntity entity) {
        return SimplyMore.identifier("textures/entity/planes/stasis.png");
    }
}