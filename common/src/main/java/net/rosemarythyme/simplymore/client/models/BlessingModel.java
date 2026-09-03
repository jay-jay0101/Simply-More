package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class BlessingModel extends EntityModel<Entity> {
	private final ModelPart bb_main;
	public BlessingModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -12.0F, -1.0F, 4.0F, 12.0F, 2.0F, new Dilation(0.0F))
				.uv(12, 0).cuboid(-5.0F, -10.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(12, 6).cuboid(2.0F, -10.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(12, 12).cuboid(-3.0F, -13.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(6, 14).cuboid(-4.0F, -13.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 14).cuboid(2.0F, -13.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(12, 16).cuboid(3.0F, -13.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		bb_main.render(matrices, vertices, light, overlay, color);
	}
}