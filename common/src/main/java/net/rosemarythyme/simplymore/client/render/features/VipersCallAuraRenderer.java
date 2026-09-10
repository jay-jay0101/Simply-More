package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.item.uniques.VipersCallItem;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;

public class VipersCallAuraRenderer {
    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider) {
        stack.push();
        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.age % 360 * 3f));

        float scale = (float) VipersCallItem.SETTINGS.auraRange * 2 + 0.25f;
        scale *= ClientActiveAbilityManager.CLIENT.getInOutStrength(entity, ActiveAbilityManager.Type.VIPERS_CALL);
        RenderUtils.drawFloorPlane(vertexConsumerProvider, stack, SimplyMore.identifier("textures/entity/planes/vipers_call.png"), LightmapTextureManager.MAX_LIGHT_COORDINATE, 0xFFFFFFFF, scale);

        stack.pop();
    }
}
