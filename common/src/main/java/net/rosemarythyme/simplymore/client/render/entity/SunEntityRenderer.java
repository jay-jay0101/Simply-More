package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.SunEntityModel;
import net.rosemarythyme.simplymore.entity.AbstractPlanetaryEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

public class SunEntityRenderer extends LivingEntityRenderer<AbstractPlanetaryEntity, SunEntityModel> {

    public SunEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SunEntityModel(context.getPart(SunEntityModel.LAYER)), 0f);
    }

    @Override
    protected boolean hasLabel(AbstractPlanetaryEntity livingEntity) {
        return false;
    }

    @Override
    public void render(AbstractPlanetaryEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();

        float scale = 1f - (float) Math.abs(MathUtils.getRiseFall(entity));
        matrixStack.scale(scale, scale, scale);

        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(AbstractPlanetaryEntity entity) {
        return SimplyMore.identifier("textures/entity/objects/sun.png");
    }

    @Override
    protected void setupTransforms(AbstractPlanetaryEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
    }
}