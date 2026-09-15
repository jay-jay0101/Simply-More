package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class DripstoneSpikeEntityRenderer<T extends ProjectileEntity> extends EntityRenderer<T> {
    private final BlockRenderManager blockRenderManager;

    public DripstoneSpikeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0f;
        this.blockRenderManager = context.getBlockRenderManager();
    }

    @Override
    public void render(T block, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();

        World world = block.getWorld();

        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(block.getYaw()));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(block.getPitch() + 90));
        matrixStack.translate(-0.375f, 0f, -0.5f);

        final BlockState state = Blocks.POINTED_DRIPSTONE.getDefaultState();
        this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(state), state, block.getBlockPos(), matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(state)), false, Random.create(), 0, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(T entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
