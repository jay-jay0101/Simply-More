package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.LightbeamModel;
import net.rosemarythyme.simplymore.entity.LightbeamEntity;
import net.rosemarythyme.simplymore.item.uniques.LustrousMoxieItem;
import net.rosemarythyme.simplymore.util.MathUtils;

public class LightbeamEntityRenderer extends LivingEntityRenderer<LightbeamEntity, LightbeamModel> {

    public LightbeamEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new LightbeamModel(context.getPart(LightbeamModel.LAYER)), 0f);
    }

    @Override
    protected boolean hasLabel(LightbeamEntity livingEntity) {
        return false;
    }

    @Override
    public void render(LightbeamEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();

        float scale = 1 - Math.abs((float) MathUtils.getRiseFall(entity));
        scale *= MathUtils.clampedLerp(entity.getCharge(), 0, LustrousMoxieItem.SETTINGS.maxChargeTime, LustrousMoxieItem.SETTINGS.minSize, LustrousMoxieItem.SETTINGS.maxSize);
        matrixStack.scale(scale, 1, scale);

        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(LightbeamEntity entity) {
        return SimplyMore.identifier("textures/entity/objects/lightbeam.png");
    }

    @Override
    public boolean shouldRender(LightbeamEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}