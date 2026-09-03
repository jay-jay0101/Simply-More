package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.LightOrbModel;
import net.rosemarythyme.simplymore.entity.LightOrbEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

public class LightOrbEntityRenderer extends LivingEntityRenderer<LightOrbEntity, LightOrbModel> {

    public LightOrbEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LightOrbModel(context.getPart(LightOrbModel.LAYER)), 0f);
    }

    @Override
    protected boolean hasLabel(LightOrbEntity livingEntity) {
        return false;
    }

    @Override
    public void render(LightOrbEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();

        float scale = 1 - (float) MathUtils.getRiseFall(entity);
        matrixStack.scale(scale, scale, scale);

        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(LightOrbEntity entity) {
        return SimplyMore.identifier("textures/entity/objects/light_orb.png");
    }
}