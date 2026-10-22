package net.rosemarythyme.simplymore.client.render.entity;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.AntimatterParticleModel;
import net.rosemarythyme.simplymore.entity.projectiles.AntimatterParticleEntity;
import net.rosemarythyme.simplymore.item.uniques.MatterbaneItem;
import net.rosemarythyme.simplymore.util.MathUtils;

public class AntimatterParticleRenderer extends EntityRenderer<AntimatterParticleEntity> {
    private final AntimatterParticleModel model;

    public AntimatterParticleRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new AntimatterParticleModel(context.getPart(AntimatterParticleModel.LAYER));
    }

    @Override
    public void render(AntimatterParticleEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0, -1.3, 0);

        int lifespan = entity.getLifespan();
        int opacity = (int) MathUtils.clampedLerp(entity.age, lifespan - 10, lifespan, 0xFF, 0x0);

        scaleAura(entity, tickDelta);
        if(entity.getState() == AntimatterParticleEntity.ParticleState.EMPOWERED) {
            shake(entity, tickDelta, matrices);
        }

        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity))), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, opacity << 24 | entity.getColor());
        matrices.pop();
    }

    private void scaleAura(AntimatterParticleEntity entity, float tickDelta) {
        float delta = entity.age + tickDelta;

        float scale = switch (entity.getState()) {
            case STABLE -> MathUtils.clampedLerp(delta, MatterbaneItem.SETTINGS.particleStableTime * 0.9f, MatterbaneItem.SETTINGS.particleStableTime, 0f, 1f);
            case UNSTABLE -> MathUtils.wave(delta, 20, 0.95f, 1.05f);
            case EMPOWERED -> {
                float idealScale = MatterbaneItem.SETTINGS.particleEmpowermentRadius * 2 * (16/14f);
                int startAge = entity.getEmpowerStartAge();
                if(delta <= startAge + 10) {
                    yield MathUtils.clampedLerp(delta, startAge, startAge + 10, 1f, idealScale);
                }

                yield MathUtils.wave(delta, 20, idealScale - 0.2f, idealScale + 0.2f);
            }
        };

        model.scaleAura(scale);
    }

    private void shake(AntimatterParticleEntity entity, float tickDelta, MatrixStack stack) {
        float delta = entity.age + tickDelta;

        stack.translate(
                Math.sin(delta * 2.7f) * 0.03f,
                Math.sin(delta * 3.4f + 1.2f) * 0.03f,
                Math.cos(delta * 2.9f + 0.7f) * 0.03f
        );
    }

    @Override
    public Identifier getTexture(AntimatterParticleEntity entity) {
        return SimplyMore.identifier("textures/entity/projectiles/antimatter.png");
    }

    @Override
    public boolean shouldRender(AntimatterParticleEntity entity, Frustum frustum, double x, double y, double z) {
        return entity.shouldRender(x, y, z);
    }
}