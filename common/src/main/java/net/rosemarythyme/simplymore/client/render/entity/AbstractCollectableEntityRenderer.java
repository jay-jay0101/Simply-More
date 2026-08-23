package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.entity.AbstractCollectableEntity;

public abstract class AbstractCollectableEntityRenderer extends EntityRenderer<AbstractCollectableEntity> {
    protected AbstractCollectableEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(AbstractCollectableEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(dispatcher.getRotation());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));

        matrices.scale(0.5f, 0.5f, 0.5f);

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)));
        MatrixStack.Entry matrix = matrices.peek();

        int alpha = getOpacity(entity);

        consumer.vertex(matrix.getPositionMatrix(), -0.5f, -0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, alpha).texture(0.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);
        consumer.vertex(matrix.getPositionMatrix(), -0.5f,  0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, alpha).texture(0.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);
        consumer.vertex(matrix.getPositionMatrix(),  0.5f,  0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, alpha).texture(1.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);
        consumer.vertex(matrix.getPositionMatrix(),  0.5f, -0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, alpha).texture(1.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);

        matrices.pop();
    }

    abstract int getOpacity(AbstractCollectableEntity entity);
}
