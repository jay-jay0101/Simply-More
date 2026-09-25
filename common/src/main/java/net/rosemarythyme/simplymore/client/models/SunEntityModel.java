package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.AbstractPlanetaryEntity;

public class SunEntityModel extends EntityModel<AbstractPlanetaryEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("sun"), "bb_main");
	private final ModelPart bb_main;

	public SunEntityModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -14.0F, -7.0F, 14.0F, 14.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(AbstractPlanetaryEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.bb_main.setAngles(0, (float) Math.toRadians(entity.getYaw()), 0);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		bb_main.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, color);
	}
}