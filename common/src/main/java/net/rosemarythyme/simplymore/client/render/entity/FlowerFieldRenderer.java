package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
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
        stack.push();
        World world = entity.getWorld();

        float scale = 1 - Math.abs((float) MathUtils.getRiseFall(entity));
        scale *= MathUtils.wave(entity.getAge() + tickDelta, 20, 5f, 5.5f);
        stack.translate(-0.5f, 0.3f, -0.5);

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