package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;

public class BladeOfTheGrotesqueAuraRenderer {
    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumerProvider, int light) {
        stack.push();
        RenderUtils.applySlowRotation(stack, entity.age);

        float scale = (float) BladeOfTheGrotesqueItem.SETTINGS.auraRange * 2 + 0.25f;
        RenderUtils.drawFloorPlane(vertexConsumerProvider, stack, SimplyMore.identifier("textures/entity/planes/blade_of_the_grotesque.png"), light, 0xFFFFFFFF, scale);

        stack.pop();
    }
}
