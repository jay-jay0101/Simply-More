package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.animations.SpiritualTormentorAnimations;
import net.rosemarythyme.simplymore.entity.SpiritualTormentorEntity;

public class SpiritualTormentorModel extends SinglePartEntityModel<SpiritualTormentorEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("spiritual_tormentor"), "BODY");

	private final ModelPart BODY;
	private final ModelPart HEAD;
	private final ModelPart RIGHT_ARM;
	private final ModelPart WEAPON;
	private final ModelPart LEFT_ARM;
	private final ModelPart WEAPON2;
	private final ModelPart TAIL;

	public SpiritualTormentorModel(ModelPart root) {
		this.BODY = root.getChild("BODY");
		this.HEAD = this.BODY.getChild("HEAD");
		this.RIGHT_ARM = this.BODY.getChild("RIGHT_ARM");
		this.WEAPON = this.RIGHT_ARM.getChild("WEAPON");
		this.LEFT_ARM = this.BODY.getChild("LEFT_ARM");
		this.WEAPON2 = this.LEFT_ARM.getChild("WEAPON2");
		this.TAIL = this.BODY.getChild("TAIL");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		ModelPartData BODY = modelPartData.addChild("BODY", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -23.0F, -10.0F, 12.0F, 10.0F, 20.0F, new Dilation(0.0F))
		.uv(0, 30).cuboid(-6.0F, -13.0F, -7.0F, 12.0F, 10.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData HEAD = BODY.addChild("HEAD", ModelPartBuilder.create().uv(52, 34).cuboid(-6.0616F, -9.414F, -5.0789F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0616F, -23.586F, 0.0789F));

		ModelPartData cube_r1 = HEAD.addChild("cube_r1", ModelPartBuilder.create().uv(30, 68).cuboid(-2.0F, -7.0F, -1.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F))
		.uv(20, 68).cuboid(-2.0F, -7.0F, -14.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.9384F, -6.414F, 5.9211F, 0.0F, 0.0F, -0.6109F));

		ModelPartData RIGHT_ARM = BODY.addChild("RIGHT_ARM", ModelPartBuilder.create().uv(32, 54).cuboid(-5.0F, -3.3333F, -6.3333F, 10.0F, 8.0F, 6.0F, new Dilation(0.0F))
		.uv(64, 54).cuboid(-4.0F, 4.6667F, -5.3333F, 8.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.6667F, -9.6667F));

		ModelPartData WEAPON = RIGHT_ARM.addChild("WEAPON", ModelPartBuilder.create().uv(76, 22).cuboid(-7.0F, -1.4F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(64, 22).cuboid(5.0F, -2.4F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
		.uv(52, 30).cuboid(8.0F, -1.4F, -0.5F, 29.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(60, 68).cuboid(37.0F, -1.4F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(64, 20).cuboid(-7.0F, -0.4F, -0.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0667F, -3.5333F));

		ModelPartData LEFT_ARM = BODY.addChild("LEFT_ARM", ModelPartBuilder.create().uv(0, 54).cuboid(-5.0F, -3.3333F, -0.1667F, 10.0F, 8.0F, 6.0F, new Dilation(0.0F))
		.uv(64, 0).cuboid(-4.0F, 4.6667F, 0.8333F, 8.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.6667F, 10.1667F));

		ModelPartData WEAPON2 = LEFT_ARM.addChild("WEAPON2", ModelPartBuilder.create().uv(76, 22).cuboid(-7.0F, -1.4F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(64, 22).cuboid(5.0F, -2.4F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
		.uv(52, 30).cuboid(8.0F, -1.4F, -0.5F, 29.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(60, 68).cuboid(37.0F, -1.4F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(64, 20).cuboid(-7.0F, -0.4F, -0.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0667F, 3.1333F));

		ModelPartData TAIL = BODY.addChild("TAIL", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

		ModelPartData cube_r2 = TAIL.addChild("cube_r2", ModelPartBuilder.create().uv(0, 68).cuboid(-5.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.136F, 3.5F, 0.0355F, 0.0F, 0.7854F, 0.0F));

		ModelPartData cube_r3 = TAIL.addChild("cube_r3", ModelPartBuilder.create().uv(0, 68).cuboid(-5.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.136F, 3.5F, 0.0355F, 0.0F, -0.7854F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(SpiritualTormentorEntity entity, float limbSwing, float limbSwingAmount, float delta, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		delta -= 20;

		HEAD.setAngles(0, (float) Math.toRadians(netHeadYaw), 0);
		BODY.setAngles(0f, (float) Math.toRadians(90f), 0f);

		this.updateAnimation(entity.idleArmsAnim, SpiritualTormentorAnimations.HAND_SWAY, delta,1f);
		this.updateAnimation(entity.chargeAnim, SpiritualTormentorAnimations.CHARGE, delta,1f);
		this.updateAnimation(entity.startChargeAnim, SpiritualTormentorAnimations.START_CHARGE, delta,1f);
		this.updateAnimation(entity.endChargeAnim, SpiritualTormentorAnimations.SWING, delta,1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		BODY.render(matrices, vertexConsumer, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return BODY;
	}
}