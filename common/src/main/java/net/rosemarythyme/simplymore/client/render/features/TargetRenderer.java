package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;

public class TargetRenderer {
    public static void renderHorizontal(RenderUtils.TargetData data, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider) {
        stack.push();
        RenderUtils.applySlowRotation(stack, data.target().age);

        float scale = (data.target().getWidth() + 0.5f) * 2f;
        RenderUtils.drawFloorPlane(vertexConsumerProvider, stack, SimplyMore.identifier("textures/entity/planes/target.png"), LightmapTextureManager.MAX_LIGHT_COORDINATE, data.color(), scale);

        stack.pop();
    }

    public static void renderVertical(RenderUtils.TargetData data, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider) {
        stack.push();
        RenderUtils.renderTowardsCamera(MinecraftClient.getInstance().gameRenderer.getCamera(), stack);
        stack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(90));
        stack.translate(0, 0.1f, 0);

        float scale = (Math.max(data.target().getWidth(), data.target().getHeight()) + 0.5f) * 2f;
        RenderUtils.drawVerticalPlane(vertexConsumerProvider, stack, SimplyMore.identifier("textures/entity/planes/target.png"), LightmapTextureManager.MAX_LIGHT_COORDINATE, data.color(), scale);

        stack.pop();
    }
}