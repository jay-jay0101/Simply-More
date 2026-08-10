package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.IcewallModel;
import net.rosemarythyme.simplymore.entity.IcewallEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

public class IcewallEntityRenderer extends LivingEntityRenderer<IcewallEntity, IcewallModel> {

    public IcewallEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new IcewallModel(context.getPart(IcewallModel.LAYER)), 0f);
    }

    @Override
    protected boolean hasLabel(IcewallEntity livingEntity) {
        return false;
    }

    @Override
    public void render(IcewallEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0, MathUtils.getRiseFall(entity) * entity.getHeight(), 0);
        matrixStack.scale(2, 2, 2);
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(IcewallEntity entity) {
        return SimplyMore.identifier("textures/entity/icewall.png");
    }
}