package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.DugBlockEntity;

public class DugBlockEntityRenderer extends EntityRenderer<DugBlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public DugBlockEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.blockRenderManager = context.getBlockRenderManager();
    }

    @Override
    public void render(DugBlockEntity block, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        BlockState blockState = block.getBlockState();

        if (blockState.getRenderType() == BlockRenderType.MODEL) {
            World world = block.getWorld();
            if (blockState != world.getBlockState(block.getBlockPos()) && blockState.getRenderType() != BlockRenderType.INVISIBLE) {
                matrixStack.push();
                BlockPos blockPos = BlockPos.ofFloored(block.getX(), block.getBoundingBox().maxY, block.getZ());
                matrixStack.translate(-0.5, 0, -0.5);
                this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState), blockState, blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false, Random.create(), blockState.getRenderingSeed(block.getBlockPos()), OverlayTexture.DEFAULT_UV);
                matrixStack.pop();
                super.render(block, f, g, matrixStack, vertexConsumerProvider, i);
            }
        }
    }

    @Override
    public Identifier getTexture(DugBlockEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
