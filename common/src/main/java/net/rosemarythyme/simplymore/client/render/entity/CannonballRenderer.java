package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.CannonballModel;
import net.rosemarythyme.simplymore.entity.CannonballEntity;

public class CannonballRenderer extends EntityRenderer<CannonballEntity> {
    private final CannonballModel model;

    public CannonballRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new CannonballModel(context.getPart(CannonballModel.LAYER));
    }

    @Override
    public void render(CannonballEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
        matrices.translate(0f, -0.5f, 0f);
        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(CannonballEntity entity) {
        return SimplyMore.identifier("textures/entity/cannonball.png");
    }
}