package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.item.uniques.SoulfractureItem;

public class SoulfractureAuraRenderer {
    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider) {
        stack.push();
        RenderUtils.applySlowRotation(stack, entity.age);

        float scale = (float) SoulfractureItem.SETTINGS.fragmentRadius * 2 + 0.25f;
        RenderUtils.drawFloorPlane(vertexConsumerProvider, stack, SimplyMore.identifier("textures/entity/planes/soulfracture.png"), LightmapTextureManager.MAX_LIGHT_COORDINATE, 0xFFFFFFFF, scale);

        stack.pop();
    }
}
