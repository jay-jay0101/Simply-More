package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.VolcanicVentModel;
import net.rosemarythyme.simplymore.entity.VolcanicVentEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

public class VolcanicVentEntityRenderer extends LivingEntityRenderer<VolcanicVentEntity, VolcanicVentModel> {

    public VolcanicVentEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new VolcanicVentModel(context.getPart(VolcanicVentModel.LAYER)), 0f);
    }

    @Override
    protected boolean hasLabel(VolcanicVentEntity livingEntity) {
        return false;
    }

    @Override
    public void render(VolcanicVentEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0, MathUtils.getRiseFall(entity), 0);
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(VolcanicVentEntity entity) {
        return SimplyMore.identifier("textures/entity/volcanic_vent.png");
    }
}