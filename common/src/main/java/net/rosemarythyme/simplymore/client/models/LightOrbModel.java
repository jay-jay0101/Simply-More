package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.LightOrbEntity;

public class LightOrbModel extends EntityModel<LightOrbEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("light_orb"), "bone");

	private final ModelPart planeX;
	private final ModelPart planeZ;
	private final ModelPart planeY;
	private final ModelPart bb_main;
	public LightOrbModel(ModelPart root) {
		this.planeX = root.getChild("planeX");
		this.planeZ = root.getChild("planeZ");
		this.planeY = root.getChild("planeY");
		this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData planeX = modelPartData.addChild("planeX", ModelPartBuilder.create().uv(0, -6).cuboid(0.0F, -3.0F, -3.0F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

		ModelPartData planeZ = modelPartData.addChild("planeZ", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

		ModelPartData cube_r1 = planeZ.addChild("cube_r1", ModelPartBuilder.create().uv(0, -6).cuboid(0.0F, -3.0F, -3.0F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData planeY = modelPartData.addChild("planeY", ModelPartBuilder.create().uv(-6, 0).cuboid(-3.0F, 0.0F, -3.0F, 6.0F, 0.05F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -11.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(LightOrbEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float delta = entity.getWorld().getTime() / (20f);
		float sinDelta = (float) Math.sin(delta);
		float cosDelta = (float) Math.cos(delta);

		planeX.setAngles(sinDelta * 6f, 0f, 0f);
		planeY.setAngles(0f, cosDelta * 6f, 0f);
		planeZ.setAngles(0f, 0f, -sinDelta * 6f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		planeX.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, color);
		planeZ.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, color);
		planeY.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, color);
		bb_main.render(matrices, vertices, LightmapTextureManager.MAX_LIGHT_COORDINATE, overlay, color);

	}
}