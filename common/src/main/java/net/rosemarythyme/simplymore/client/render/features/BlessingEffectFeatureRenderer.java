package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.BlessingModel;

public class BlessingEffectFeatureRenderer {
    public static final BlessingModel MODEL = new BlessingModel(BlessingModel.getTexturedModelData().createModel());

    public static void render(LivingEntity entity, MatrixStack stack, float delta, VertexConsumerProvider vertexConsumerProvider) {
        stack.push();

        float ageDelta = entity.age + delta;
        float baseAngle = (ageDelta * 120f / 20f) % 360f;
        stack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(baseAngle));

        stack.translate(0, entity.getHeight(), 0);
        stack.scale(0.5f, 0.5f, 0.5f);
        stack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(180));

        VertexConsumer consumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(SimplyMore.identifier("textures/entity/icons/blessing.png")));
        renderOne(0, stack, consumer);
        renderOne(360f * (1/3f), stack, consumer);
        renderOne(360f * (2/3f), stack, consumer);

        stack.pop();
    }

    public static void renderOne(float angle, MatrixStack stack, VertexConsumer consumer) {
        stack.push();

        stack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(angle));
        stack.translate(0, 0, 1);

        MODEL.render(stack, consumer, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);

        stack.pop();
    }
}
