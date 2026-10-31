package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.entity.FlowerFieldEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

public class FlowerFieldRenderer extends EntityRenderer<FlowerFieldEntity> {
    public FlowerFieldRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FlowerFieldEntity entity, float yaw, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light) {
        renderBlossom(entity, stack, vertexConsumers, tickDelta, light, new Vec3d(0, 0, 0), 2f);

        float yawOffset = ((entity.getAge() + tickDelta) % 360) * 360 / 100;
        for (int i = 0; i < 360; i += 45) {
            renderBlossom(entity, stack, vertexConsumers, tickDelta, light, MathUtils.getDirectionalVector(i - yawOffset + 22.5f,0).multiply(2.2f), 1f);
            renderBlossom(entity, stack, vertexConsumers, tickDelta, light, MathUtils.getDirectionalVector(i + yawOffset,0).multiply(3f), 0.75f);
            renderBlossom(entity, stack, vertexConsumers, tickDelta, light, MathUtils.getDirectionalVector(i - yawOffset - 22.5f,0).multiply(3.4f), 0.5f);
        }
    }

    private void renderBlossom(FlowerFieldEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumers, float delta, int light, Vec3d offset, float scale) {
        stack.push();
        World world = entity.getWorld();

        scale *= 1 - Math.abs((float) MathUtils.getRiseFall(entity));
        scale *= MathUtils.wave(entity.getAge() + delta, 20, 0.9f, 1.1f);
        stack.translate(-0.5f + offset.getX(), offset.getY() - 0.2f, -0.5 + offset.getZ());

        stack.translate(0.5f, 0.8f, 0.5);

        stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
        RenderUtils.applySlowRotation(stack, world.getTime());

        stack.scale(scale, scale, scale);

        stack.translate(-0.5f, -0.8f, -0.5f);

        BlockState state = Blocks.SPORE_BLOSSOM.getDefaultState();
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, stack, vertexConsumers, light, OverlayTexture.DEFAULT_UV);

        stack.pop();
    }

    @Override
    public boolean shouldRender(FlowerFieldEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }

    @Override
    public Identifier getTexture(FlowerFieldEntity entity) {
        return Identifier.ofVanilla("air");
    }
}