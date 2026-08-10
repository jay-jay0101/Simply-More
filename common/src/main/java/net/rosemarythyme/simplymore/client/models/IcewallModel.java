package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.IcewallEntity;

public class IcewallModel extends EntityModel<IcewallEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("icewall"), "bone");

	private final ModelPart bb_main;
	public IcewallModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -31.0F, -6.0F, 10.0F, 31.0F, 12.0F, new Dilation(0.0F))
		.uv(42, 43).cuboid(-8.0F, -18.0F, -9.0F, 9.0F, 18.0F, 10.0F, new Dilation(0.0F))
		.uv(44, 0).cuboid(-4.0F, -11.0F, -10.0F, 11.0F, 11.0F, 11.0F, new Dilation(0.0F))
		.uv(0, 43).cuboid(-2.0F, -22.0F, -3.0F, 10.0F, 22.0F, 11.0F, new Dilation(0.0F))
		.uv(42, 71).cuboid(-9.0F, -15.0F, -3.0F, 10.0F, 15.0F, 10.0F, new Dilation(0.0F))
		.uv(80, 22).cuboid(-3.0F, -42.0F, -3.0F, 7.0F, 11.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 76).cuboid(3.0F, -9.0F, -8.0F, 6.0F, 9.0F, 13.0F, new Dilation(0.0F))
		.uv(80, 39).cuboid(-10.0F, -6.0F, -11.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F))
		.uv(44, 22).cuboid(-6.0F, -11.0F, 4.0F, 12.0F, 11.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(IcewallEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		bb_main.render(matrices, vertices, light, overlay, color);
	}
}