package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;

public class BloodHarvesterSenseRenderer {
    public static RenderLayer layer = RenderLayer.of(
            "blood_sense",
            VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
            VertexFormat.DrawMode.QUADS,
            256,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.POSITION_COLOR_TEXTURE_LIGHTMAP_PROGRAM)
                    .texture(new RenderPhase.Texture(
                            SimplyMore.identifier("textures/entity/blood_sense.png"),
                            false,
                            false
                    ))
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .depthTest(RenderPhase.ALWAYS_DEPTH_TEST)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                    .build(false)
    );

    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, Camera camera) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.world == null) return;

        stack.push();

        stack.multiply(camera.getRotation());
        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));

        stack.translate(0, entity.getHeight() / 2f, 0);

        float scale = (float) (Math.sin(client.world.getTime() * 0.1f) * 0.15f) + 1f;
        stack.scale(scale, scale, scale);

        VertexConsumer vc = vertexConsumerProvider.getBuffer(layer);
        MatrixStack.Entry matrix = stack.peek();

        int opacity = (int) (0xFF * ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST));

        vc.vertex(matrix.getPositionMatrix(), -0.5f, -0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, opacity).texture(0.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);
        vc.vertex(matrix.getPositionMatrix(), -0.5f,  0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, opacity).texture(0.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);
        vc.vertex(matrix.getPositionMatrix(),  0.5f,  0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, opacity).texture(1.0f, 0.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);
        vc.vertex(matrix.getPositionMatrix(),  0.5f, -0.5f, 0.0f).color(0xFF, 0xFF, 0xFF, opacity).texture(1.0f, 1.0f).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrix, 0.0f, 1.0f, 0.0f);

        renderName(entity, client.textRenderer, stack, vertexConsumerProvider, opacity);

        stack.pop();
    }

    public static void renderName(LivingEntity entity, TextRenderer renderer, MatrixStack stack, VertexConsumerProvider vcs, int opacity) {
        Text name = entity.getName();
        float width = renderer.getWidth(name);

        stack.translate(0, 0.6f, 0);
        stack.scale(-0.04f, -0.04f, -0.04f);
        renderer.draw(
                name, -width / 2f, 0,
                ColorHelper.Argb.withAlpha(opacity, 0x660000), false,
                stack.peek().getPositionMatrix(), vcs,
                TextRenderer.TextLayerType.SEE_THROUGH,
                0, LightmapTextureManager.MAX_LIGHT_COORDINATE
        );
    }
}
