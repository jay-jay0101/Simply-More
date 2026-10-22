package net.rosemarythyme.simplymore.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.projectiles.AntimatterParticleEntity;

public class AntimatterParticleModel extends EntityModel<AntimatterParticleEntity> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(SimplyMore.identifier("antimatter"), "root");
	private final ModelPart root;
	public final ModelPart aura;
	public AntimatterParticleModel(ModelPart root) {
		this.root = root.getChild("root");
		this.aura = this.root.getChild("aura");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 28).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData aura = root.addChild("aura", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -8.0F, -7.0F, 14.0F, 14.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(AntimatterParticleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		root.render(matrices, vertexConsumer, light, overlay, color);
	}

	public void scaleAura(float scale) {
		aura.xScale = scale;
		aura.yScale = scale;
		aura.zScale = scale;
	}
}