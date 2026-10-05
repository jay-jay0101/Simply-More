package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.CrowProjectileEntityModel;
import net.rosemarythyme.simplymore.entity.projectiles.CrowProjectileEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

public class CrowProjectileEntityRenderer extends EntityRenderer<CrowProjectileEntity> {
    private final CrowProjectileEntityModel model;

    @Override
    public void render(CrowProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        yaw = MathUtils.getYawAndPitch(entity.getVelocity()).getLeft();
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(yaw));

        matrices.translate(0, 1f, 0);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));

        model.setAngles(null, 0, 0, tickDelta, yaw, 0);
        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 0xFFFFFFFF);
        matrices.pop();
    }

    public CrowProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new CrowProjectileEntityModel(context.getPart(CrowProjectileEntityModel.LAYER));
    }

    @Override
    public Identifier getTexture(CrowProjectileEntity entity) {
        return SimplyMore.identifier("textures/entity/crow.png");
    }
}