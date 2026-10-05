package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.animations.CrowAnimations;
import net.rosemarythyme.simplymore.entity.projectiles.CrowProjectileEntity;
import org.joml.Vector3f;

public class CrowProjectileEntityModel extends SinglePartEntityModel<CrowProjectileEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("crow_projectile"), "all");

	private final ModelPart root;
	private final ModelPart all;
	private final ModelPart wing0;
	private final ModelPart wing1;
	private final ModelPart leg1;
	private final ModelPart leg0;
	private final ModelPart head;
	private final ModelPart body;
	public CrowProjectileEntityModel(ModelPart root) {
		this.root = root.getChild("root");
		this.all = this.root.getChild("all");
		this.wing0 = this.all.getChild("wing0");
		this.wing1 = this.all.getChild("wing1");
		this.leg1 = this.all.getChild("leg1");
		this.leg0 = this.all.getChild("leg0");
		this.head = this.all.getChild("head");
		this.body = this.all.getChild("body");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0f, 0f, 0f));
		ModelPartData all = root.addChild("all", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 18.8327F, -1.8047F));

		ModelPartData wing0 = all.addChild("wing0", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, -1.9327F, -0.7954F));

		ModelPartData wing0_r1 = wing0.addChild("wing0_r1", ModelPartBuilder.create().uv(24, 13).cuboid(-1.0F, -1.2535F, -0.1823F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData wing1 = all.addChild("wing1", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, -1.9327F, -0.7954F));

		ModelPartData wing1_r1 = wing1.addChild("wing1_r1", ModelPartBuilder.create().uv(24, 13).cuboid(0.0F, -1.2535F, -0.1823F, 1.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData leg1 = all.addChild("leg1", ModelPartBuilder.create().uv(26, 0).cuboid(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 2.1673F, 2.8047F));

		ModelPartData leg0 = all.addChild("leg0", ModelPartBuilder.create().uv(26, 0).cuboid(-1.5F, 0.0F, -3.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 2.1673F, 2.8047F));

		ModelPartData head = all.addChild("head", ModelPartBuilder.create().uv(52, 19).cuboid(-1.5F, -3.0F, -2.8333F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(58, 28).cuboid(-0.5F, -3.0F, -4.8333F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(59, 29).cuboid(-0.5F, -3.0F, -5.8333F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.8327F, -0.362F));

		ModelPartData body = all.addChild("body", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.3634F, 2.8434F, 1.5708F, 0.0F, 0.0F));

		ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(54, 11).cuboid(-2.0F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -0.0388F, 0.1961F, -0.3491F, 0.0F, 0.0F));

		ModelPartData body_r2 = body.addChild("body_r2", ModelPartBuilder.create().uv(50, 0).cuboid(-2.0F, -4.0F, -0.5F, 3.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -2.0388F, -1.3039F, -0.6981F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(CrowProjectileEntity entity, float limbAngle, float limbDistance, float delta, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		AnimationHelper.animate(this, CrowAnimations.SWOOP, 0L, 1.0F, new Vector3f());
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		root.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return root;
	}
}