package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;

public class WraithEyesRenderer {
    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, float delta) {
        if(entity == MinecraftClient.getInstance().player && MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON) return;
        stack.push();

        stack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw(delta) + 90));
        stack.translate(0, entity.getEyeHeight(entity.getPose()), 0);
        RenderUtils.drawVerticalPlane(vertexConsumerProvider, stack, SimplyMore.identifier("textures/entity/planes/eyes.png"), LightmapTextureManager.MAX_LIGHT_COORDINATE, 0xAAFFFFFF);

        stack.pop();
    }
}
