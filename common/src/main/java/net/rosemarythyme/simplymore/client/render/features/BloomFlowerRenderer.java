package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.InventoryUtils;
import net.rosemarythyme.simplymore.util.ItemStackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;

public class BloomFlowerRenderer {
    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, ClientWorld world, float delta, int light) {
        stack.push();

        ItemStack item = InventoryUtils.getHeldAwakenedStack(entity, ItemRegistry.PERFORISCUS.get());
        if(!item.isEmpty()) {
            float scale = ItemStackUtils.getCounterComponentProgress(item) * MathUtils.wave(entity.age + delta, 20, 0.85f, 1.15f) * 1.5f;

            float height = entity.getHeight() - 1f + 0.3f;
            stack.translate(-0.5f, height, -0.5f);

            stack.translate(0.5f, 1f, 0.5f);
            stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
            RenderUtils.applySlowRotation(stack, world.getTime());
            stack.scale(scale, scale, scale);
            stack.translate(-0.5f, -1f, -0.5f);

            BlockState state = Blocks.SPORE_BLOSSOM.getDefaultState();
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, stack, vertexConsumerProvider, light, OverlayTexture.DEFAULT_UV);

        }

        stack.pop();
    }
}
