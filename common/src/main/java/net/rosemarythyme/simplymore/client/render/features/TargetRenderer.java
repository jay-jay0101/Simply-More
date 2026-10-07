package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;

public class TargetRenderer {
    public static void render(RenderUtils.TargetData data, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider) {
        stack.push();
        RenderUtils.applySlowRotation(stack, data.target().age);

        float scale = (data.target().getWidth() + 0.5f) * 2f;
        RenderUtils.drawFloorPlane(vertexConsumerProvider, stack, SimplyMore.identifier("textures/entity/planes/target.png"), LightmapTextureManager.MAX_LIGHT_COORDINATE, data.color(), scale);

        stack.pop();
    }
}