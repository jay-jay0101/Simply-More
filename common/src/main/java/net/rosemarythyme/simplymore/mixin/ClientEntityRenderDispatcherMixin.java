package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderDispatcher.class)
public class ClientEntityRenderDispatcherMixin<T extends Entity> {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/entity/Entity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void simplymore$modifyYaw(EntityRenderer<T> instance, Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        matrices.push();
        if(entity instanceof LivingEntity livingEntity) {
            float rot = RenderUtils.getModelRotationOverride(livingEntity, tickDelta, yaw);
            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(rot - yaw));
        }

        original.call(instance, entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
