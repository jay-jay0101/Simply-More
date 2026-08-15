package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.LightbeamEntity;

public class LightbeamModel extends EntityModel<LightbeamEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("lightbeam"), "bone");

    private final ModelPart beam;
    private final ModelPart bb_main;
    public LightbeamModel(ModelPart root) {
        this.beam = root.getChild("beam");
        this.bb_main = root.getChild("bb_main");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData beam = modelPartData.addChild("beam", ModelPartBuilder.create().uv(0, 24).cuboid(-7.0F, -34.0F, -7.0F, 14.0F, 34.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-12.0F, -0.4F, -12.0F, 24.0F, 0.0F, 24.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(LightbeamEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        beam.yScale = 100;
        float yaw = (float) Math.toRadians((entity.getWorld().getTime() + animationProgress) / (20 / 40f));
        beam.yaw = yaw;
        bb_main.yaw = yaw;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        beam.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, color);
        bb_main.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, color);
    }
}