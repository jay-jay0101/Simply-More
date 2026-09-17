package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class BrassturnCogModel extends EntityModel<Entity> {
	private final ModelPart side;
	private final ModelPart side2;
	private final ModelPart side3;
	private final ModelPart side4;
	private final ModelPart d2;
	public BrassturnCogModel(ModelPart root) {
		this.side = root.getChild("side");
		this.side2 = root.getChild("side2");
		this.side3 = root.getChild("side3");
		this.side4 = root.getChild("side4");
		this.d2 = root.getChild("d2");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData side = modelPartData.addChild("side", ModelPartBuilder.create().uv(36, 42).cuboid(-4.0F, -4.0F, -1.0F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F))
		.uv(0, 63).cuboid(3.0F, -4.1F, -2.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(10, 63).cuboid(5.0F, -4.1F, -3.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(68, 56).cuboid(8.0F, -4.1F, -7.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(60, 63).cuboid(7.0F, -4.1F, -5.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 56).cuboid(6.0F, -4.1F, -4.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 9.0F));

		ModelPartData side2 = modelPartData.addChild("side2", ModelPartBuilder.create().uv(36, 42).cuboid(-4.0F, -4.0F, -1.0F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F))
		.uv(20, 63).cuboid(3.0F, -4.1F, -2.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(30, 63).cuboid(5.0F, -4.1F, -3.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(60, 69).cuboid(8.0F, -4.1F, -7.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 70).cuboid(7.0F, -4.1F, -5.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 56).cuboid(6.0F, -4.1F, -4.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(10.0F, 24.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData cube_r1 = side2.addChild("cube_r1", ModelPartBuilder.create().uv(36, 42).cuboid(-4.0F, -2.0F, 9.0F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.2F, -10.0F, 0.0F, 2.3562F, 0.0F));

		ModelPartData cube_r2 = side2.addChild("cube_r2", ModelPartBuilder.create().uv(36, 42).cuboid(-4.0F, -2.0F, 9.0F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.2F, -10.0F, -3.1416F, -0.7854F, 3.1416F));

		ModelPartData cube_r3 = side2.addChild("cube_r3", ModelPartBuilder.create().uv(36, 42).cuboid(-4.0F, -2.0F, 9.0F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.2F, -10.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r4 = side2.addChild("cube_r4", ModelPartBuilder.create().uv(36, 42).cuboid(-4.0F, -2.0F, 9.0F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.2F, -10.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData side3 = modelPartData.addChild("side3", ModelPartBuilder.create().uv(36, 42).cuboid(-9.9167F, -2.0F, 0.75F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F))
		.uv(48, 56).cuboid(-2.9167F, -2.1F, -0.25F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(58, 56).cuboid(-0.9167F, -2.1F, -1.25F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(10, 70).cuboid(2.0833F, -2.1F, -5.25F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 70).cuboid(1.0833F, -2.1F, -3.25F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 56).cuboid(0.0833F, -2.1F, -2.25F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-5.9167F, 22.0F, -9.25F, 0.0F, 3.1416F, 0.0F));

		ModelPartData side4 = modelPartData.addChild("side4", ModelPartBuilder.create().uv(36, 42).cuboid(-4.0F, -4.0F, -1.0F, 8.0F, 4.0F, 10.0F, new Dilation(0.0F))
		.uv(40, 63).cuboid(3.0F, -4.1F, -2.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(50, 63).cuboid(5.0F, -4.1F, -3.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(30, 70).cuboid(8.0F, -4.1F, -7.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 70).cuboid(7.0F, -4.1F, -5.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(36, 56).cuboid(6.0F, -4.1F, -4.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, 24.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData d2 = modelPartData.addChild("d2", ModelPartBuilder.create(), ModelTransform.pivot(-0.0121F, 22.0F, -0.9116F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		side.render(matrices, vertices, light, overlay, color);
		side2.render(matrices, vertices, light, overlay, color);
		side3.render(matrices, vertices, light, overlay, color);
		side4.render(matrices, vertices, light, overlay, color);
		d2.render(matrices, vertices, light, overlay, color);
	}
}