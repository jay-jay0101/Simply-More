package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.VolcanicVentEntity;

public class VolcanicVentModel extends EntityModel<VolcanicVentEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("volcanic_vent"), "bone");

	private final ModelPart bb_main;
	public VolcanicVentModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -5.0F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F))
		.uv(28, 9).cuboid(-5.0F, -7.0F, -1.0F, 3.0F, 6.0F, 2.0F, new Dilation(0.0F))
		.uv(10, 28).cuboid(2.0F, -7.0F, -1.0F, 3.0F, 6.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 0).cuboid(-1.0F, -7.0F, -5.0F, 2.0F, 6.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 28).cuboid(-1.0F, -7.0F, 2.0F, 2.0F, 6.0F, 3.0F, new Dilation(0.0F))
		.uv(28, 17).cuboid(-5.0F, -4.0F, -4.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 28).cuboid(-3.0F, -4.0F, 4.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-5.0F, -3.0F, 1.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(14, 10).cuboid(-6.0F, -3.0F, -3.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 16).cuboid(-4.0F, -3.0F, -6.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(14, 16).cuboid(1.0F, -3.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 22).cuboid(0.0F, -3.0F, -2.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(14, 22).cuboid(1.0F, -3.0F, 1.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(VolcanicVentEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		bb_main.render(matrices, vertices, light, overlay, color);
	}
}