package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.animations.CrowEntityAnimations;
import net.rosemarythyme.simplymore.entity.legacy.CrowEntity;

public class CrowEntityModel extends SinglePartEntityModel<CrowEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("crow"), "bone");

	private final ModelPart main;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	public CrowEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.head = this.main.getChild("head");
		this.tail = this.main.getChild("tail");
		this.rightWing = this.main.getChild("right_wing");
		this.leftWing = this.main.getChild("left_wing");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -3.9F, -1.8F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
		.uv(14, 2).cuboid(0.5F, 1.1F, -0.8F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 10).cuboid(0.5F, -0.9F, 0.2F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(14, 4).cuboid(-1.5F, -0.9F, 0.2F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(14, 3).cuboid(-1.5F, 1.1F, -0.8F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.9F, 0.8F));

		ModelPartData head = main.addChild("head", ModelPartBuilder.create().uv(8, 12).cuboid(-1.0F, -2.25F, -1.25F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(14, 0).cuboid(-0.5F, -1.25F, -2.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.65F, -1.05F));

		ModelPartData tail = main.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.15F, 1.95F));

		ModelPartData tail1 = tail.addChild("tail1", ModelPartBuilder.create().uv(0, 7).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 2.25F, 1.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData rightWing = main.addChild("right_wing", ModelPartBuilder.create().uv(0, 10).cuboid(-1.0F, -0.5F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -2.65F, 0.2F));

		ModelPartData leftWing = main.addChild("left_wing", ModelPartBuilder.create().uv(10, 7).cuboid(0.0F, -0.5F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, -2.65F, 0.2F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		main.render(matrices, vertexConsumer, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return main;
	}

	@Override
	public void setAngles(CrowEntity entity, float limbAngle, float limbDistance, float delta, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.head.pitch = headPitch * (float) (Math.PI / 180.0);
		this.head.yaw = headYaw * (float) (Math.PI / 180.0);

		this.updateAnimation(entity.flapAnimationState, CrowEntityAnimations.FLAP, delta,1f);
	}
}