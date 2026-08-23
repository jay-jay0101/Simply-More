package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.CannonballEntity;

public class CannonballModel extends EntityModel<CannonballEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("cannonball"), "bone");

	private final ModelPart bone;
	public CannonballModel(ModelPart root) {
		this.bone = root.getChild("bone");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 34).cuboid(-4.0F, -5.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 18).cuboid(-5.0F, -4.0F, -4.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(CannonballEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		bone.render(matrices, vertices, light, overlay, color);
	}
}