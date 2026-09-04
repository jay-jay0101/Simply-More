package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.animations.SpiritualGuardianAnimations;
import net.rosemarythyme.simplymore.entity.SpiritualGuardianEntity;

public class SpiritualGuardianModel extends SinglePartEntityModel<SpiritualGuardianEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("spiritual_guardian"), "BODY");

	private final ModelPart BODY;
	private final ModelPart HEAD;
	private final ModelPart RIGHT_ARM;
	private final ModelPart WEAPON;
	private final ModelPart LEFT_ARM;
	private final ModelPart SHIELD;
	private final ModelPart TAIL;
	public SpiritualGuardianModel(ModelPart root) {
		this.BODY = root.getChild("BODY");
		this.HEAD = this.BODY.getChild("HEAD");
		this.RIGHT_ARM = this.BODY.getChild("RIGHT_ARM");
		this.WEAPON = this.RIGHT_ARM.getChild("WEAPON");
		this.LEFT_ARM = this.BODY.getChild("LEFT_ARM");
		this.SHIELD = this.LEFT_ARM.getChild("SHIELD");
		this.TAIL = this.BODY.getChild("TAIL");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData BODY = modelPartData.addChild("BODY", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -23.0F, -10.0F, 12.0F, 10.0F, 20.0F, new Dilation(0.0F))
		.uv(0, 30).cuboid(-6.0F, -13.0F, -7.0F, 12.0F, 10.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData cube_r1 = BODY.addChild("cube_r1", ModelPartBuilder.create().uv(48, 78).cuboid(-5.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.136F, 2.5F, 0.0355F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r2 = BODY.addChild("cube_r2", ModelPartBuilder.create().uv(48, 78).cuboid(-5.0F, -5.5F, 0.0F, 10.0F, 11.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.136F, 2.5F, 0.0355F, 0.0F, 0.7854F, 0.0F));

		ModelPartData HEAD = BODY.addChild("HEAD", ModelPartBuilder.create().uv(0, 54).cuboid(-6.0616F, -9.414F, -5.0789F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0616F, -23.586F, 0.0789F));

		ModelPartData cube_r3 = HEAD.addChild("cube_r3", ModelPartBuilder.create().uv(48, 89).cuboid(-2.0F, -7.0F, -1.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.9384F, -4.414F, -5.0789F, 0.6807F, 0.0F, -1.5708F));

		ModelPartData cube_r4 = HEAD.addChild("cube_r4", ModelPartBuilder.create().uv(68, 89).cuboid(-1.0F, -3.5F, -1.5F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.5217F, -6.6714F, -6.3619F, 0.6677F, -0.2217F, -1.2989F));

		ModelPartData cube_r5 = HEAD.addChild("cube_r5", ModelPartBuilder.create().uv(58, 89).cuboid(-1.0F, -3.5F, -1.5F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.5217F, -6.6714F, 6.6381F, -0.6677F, 0.2217F, -1.2989F));

		ModelPartData cube_r6 = HEAD.addChild("cube_r6", ModelPartBuilder.create().uv(88, 88).cuboid(-2.0F, -7.0F, -1.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.9384F, -4.414F, 4.9211F, -0.4363F, 0.0F, -1.5708F));

		ModelPartData cube_r7 = HEAD.addChild("cube_r7", ModelPartBuilder.create().uv(88, 78).cuboid(-2.0F, -7.0F, -1.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F))
		.uv(40, 54).cuboid(-2.0F, -7.0F, -10.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.9384F, -6.414F, 3.9211F, 0.0F, 0.0F, -0.6109F));

		ModelPartData RIGHT_ARM = BODY.addChild("RIGHT_ARM", ModelPartBuilder.create().uv(72, 64).cuboid(-5.0F, -3.3333F, -6.3333F, 10.0F, 8.0F, 6.0F, new Dilation(0.0F))
		.uv(24, 78).cuboid(-4.0F, 4.6667F, -5.3333F, 8.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.6667F, -9.6667F));

		ModelPartData WEAPON = RIGHT_ARM.addChild("WEAPON", ModelPartBuilder.create().uv(64, 24).cuboid(-7.0F, -1.4F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(90, 0).cuboid(5.0F, -2.4F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F))
		.uv(52, 30).cuboid(8.0F, -1.4F, -0.5F, 29.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(24, 74).cuboid(37.0F, -1.4F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(65, 22).cuboid(-7.0F, -0.4F, -0.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0667F, -3.5333F));

		ModelPartData LEFT_ARM = BODY.addChild("LEFT_ARM", ModelPartBuilder.create().uv(40, 64).cuboid(-5.0F, -3.3333F, -0.1667F, 10.0F, 8.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 74).cuboid(-4.0F, 4.6667F, 0.8333F, 8.0F, 16.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.6667F, 10.1667F));

		ModelPartData SHIELD = LEFT_ARM.addChild("SHIELD", ModelPartBuilder.create().uv(52, 34).cuboid(-0.6F, -7.6F, -7.48F, 2.0F, 15.0F, 15.0F, new Dilation(0.0F))
		.uv(86, 34).cuboid(-0.6F, -13.6F, -10.48F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F))
		.uv(86, 48).cuboid(-0.6F, -13.6F, 7.52F, 2.0F, 11.0F, 3.0F, new Dilation(0.0F))
		.uv(78, 89).cuboid(-2.6F, 0.4F, -1.08F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F))
		.uv(65, 1).cuboid(-0.6F, 7.4F, -4.98F, 2.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(3.4F, 18.2667F, 4.8133F, 0.0F, -1.2654F, 1.5708F));

		ModelPartData TAIL = BODY.addChild("TAIL", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(SpiritualGuardianEntity entity, float limbSwing, float limbSwingAmount, float delta, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		HEAD.setAngles(0, (float) Math.toRadians(netHeadYaw), 0);
		BODY.setAngles(0f, (float) Math.toRadians(90f), 0f);

		this.updateAnimation(entity.idleArmsAnim, SpiritualGuardianAnimations.ARMS_SWAY, delta,1f);
		this.updateAnimation(entity.attackAnim, SpiritualGuardianAnimations.SWING, delta,1f);
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