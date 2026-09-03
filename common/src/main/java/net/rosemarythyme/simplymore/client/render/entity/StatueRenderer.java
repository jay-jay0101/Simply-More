package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.StatueEntity;

public class StatueRenderer extends EntityRenderer<StatueEntity> {
    private static boolean shouldRenderAsStatue;

    public static boolean shouldRenderAsStatue() {
        return shouldRenderAsStatue;
    }

    public StatueRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected boolean hasLabel(StatueEntity livingEntity) {
        return false;
    }

    @Override
    public void render(StatueEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(entity.ownerSnapshot == null) return;
        if(MinecraftClient.getInstance().player == null) return;

        LivingEntity owner = entity.ownerSnapshot.snapshot(entity.getWorld());
        if(owner == null) return;
        owner.readNbt(entity.ownerSnapshot.compound());

        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYawOverride()));

        if(!entity.ownerSnapshot.owner().getUuid().equals(MinecraftClient.getInstance().player.getUuid()) || MinecraftClient.getInstance().options.getPerspective() != Perspective.FIRST_PERSON) {
            shouldRenderAsStatue = true;
            dispatcher.getRenderer(owner).render(owner, 0, 0, matrixStack, vertexConsumerProvider, i);
            shouldRenderAsStatue = false;
        }

        owner.discard();
    }

    @Override
    public Identifier getTexture(StatueEntity entity) {
        return getTexture();
    }

    public static Identifier getTexture() {
        return SimplyMore.identifier("textures/entity/misc/statue.png");
    }
}