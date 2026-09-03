package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.entity.AbstractSpiritualEntity;

public abstract class AbstractSpiritualRenderer<E extends AbstractSpiritualEntity, M extends EntityModel<E>> extends LivingEntityRenderer<E, M> {
    public AbstractSpiritualRenderer(EntityRendererFactory.Context context, M model) {
        super(context, model, 1.2f);
    }

    @Override
    public void render(E livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        //todo: add scaling

        matrixStack.push();
        matrixStack.translate(0, (Math.sin(livingEntity.getAge() / 10f) + 1) / 6f, 0);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();

        matrixStack.push();
        RenderUtils.applySlowRotation(matrixStack, livingEntity.getAge());
        RenderUtils.drawFloorPlane(vertexConsumerProvider, matrixStack, getAuraTexture(), LightmapTextureManager.MAX_LIGHT_COORDINATE, 0xFFFFFFFF, (float) livingEntity.getAuraRange() * 2);
        matrixStack.pop();
    }

    public abstract Identifier getAuraTexture();

    @Override
    protected boolean hasLabel(E livingEntity) {
        return false;
    }

    @Override
    public boolean shouldRender(E entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }
}