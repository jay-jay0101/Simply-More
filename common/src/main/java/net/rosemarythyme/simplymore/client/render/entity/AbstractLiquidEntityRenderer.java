package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.entity.AbstractLiquidEntity;
import org.joml.Vector3f;

public abstract class AbstractLiquidEntityRenderer extends EntityRenderer<AbstractLiquidEntity> {

    public AbstractLiquidEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(AbstractLiquidEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float height = entity.getLiquidHeight();
        Sprite sprite = MinecraftClient.getInstance().getBakedModelManager().getAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).getSprite(getTexture(entity));
        matrices.push();
        matrices.translate(-0.5f, 0, -0.5f);

        VertexConsumer vc = vertexConsumers.getBuffer(RenderLayer.getTranslucentMovingBlock());
        MatrixStack.Entry matrix = matrices.peek();

        float u1 = sprite.getMinU();
        float u2 = sprite.getMaxU();
        float v1 = sprite.getMinV();
        float v2 = sprite.getMaxV();

        int color = getColor();
        light = getLight(light);

        renderSide(vc, matrix, new Vector3f(0, height, 0), new Vector3f(1, height, 0), new Vector3f(1, height, 1), new Vector3f(0, height, 1), u1, v1, u2, v2, light, color);
        renderSide(vc, matrix, new Vector3f(0, 0, 1), new Vector3f(1, 0, 1), new Vector3f(1, 0, 0), new Vector3f(0, 0, 0), u1, v1, u2, v2, light, color);
        renderSide(vc, matrix, new Vector3f(1, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0, height, 0), new Vector3f(1, height, 0), u1, v1, u2, v2, light, color);
        renderSide(vc, matrix, new Vector3f(0, 0, 1), new Vector3f(1, 0, 1), new Vector3f(1, height, 1), new Vector3f(0, height, 1), u1, v1, u2, v2, light, color);
        renderSide(vc, matrix, new Vector3f(0, 0, 0), new Vector3f(0, 0, 1), new Vector3f(0, height, 1), new Vector3f(0, height, 0), u1, v1, u2, v2, light, color);
        renderSide(vc, matrix, new Vector3f(1, 0, 1), new Vector3f(1, 0, 0), new Vector3f(1, height, 0), new Vector3f(1, height, 1), u1, v1, u2, v2, light, color);

        matrices.pop();
    }

    public void renderSide(VertexConsumer vc, MatrixStack.Entry matrix, Vector3f pos1, Vector3f pos2, Vector3f pos3, Vector3f pos4, float u1, float v1, float u2, float v2, int light, int color) {
        Vector3f edge1 = new Vector3f(pos3).sub(pos1);
        Vector3f edge2 = new Vector3f(pos4).sub(pos1);
        Vector3f normal = edge1.cross(edge2).normalize();
        Vector3f invertedNormal = new Vector3f(normal).mul(-1);

        addVertex(vc, matrix, pos1, u1, v2, light, color, normal);
        addVertex(vc, matrix, pos2, u2, v2, light, color, normal);
        addVertex(vc, matrix, pos3, u2, v1, light, color, normal);
        addVertex(vc, matrix, pos4, u1, v1, light, color, normal);
        addVertex(vc, matrix, pos4, u1, v1, light, color, invertedNormal);
        addVertex(vc, matrix, pos3, u2, v1, light, color, invertedNormal);
        addVertex(vc, matrix, pos2, u2, v2, light, color, invertedNormal);
        addVertex(vc, matrix, pos1, u1, v2, light, color, invertedNormal);
    }

    public void addVertex(VertexConsumer vc, MatrixStack.Entry matrix, Vector3f pos, float u, float v, int light, int color, Vector3f normal) {
        vc.vertex(matrix, pos).color(color).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix, normal.x, normal.y, normal.z);
    }

    abstract int getLight(int light);
    abstract int getColor();
}