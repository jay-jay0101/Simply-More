package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.models.BrassturnCogModel;
import net.rosemarythyme.simplymore.item.components.RotationComponent;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;

public class BrassturnCogFeatureRenderer {
    public static final BrassturnCogModel MODEL = new BrassturnCogModel(BrassturnCogModel.getTexturedModelData().createModel());

    public static void render(LivingEntity entity, ClientWorld world, MatrixStack stack, float delta, VertexConsumerProvider vertexConsumerProvider, int light, ItemStack itemStack) {
        stack.push();
        float oxidation = MathUtils.getCounterComponentProgress(itemStack);
        RotationComponent rot = itemStack.getOrDefault(ItemComponentRegistry.ROTATION.get(), RotationComponent.DEFAULT);

        stack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(rot.getRotation(delta + world.getTime())));
        stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        stack.translate(0, -2f, 0);

        VertexConsumer consumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(SimplyMore.identifier("textures/entity/objects/cog.png")));
        int color = ColorHelper.Argb.lerp(oxidation, 0xFFC54920, 0xFF499282);
        MODEL.render(stack, consumer, light, OverlayTexture.DEFAULT_UV, color);

        stack.pop();
    }
}
