package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.rosemarythyme.simplymore.item.uniques.MoundshifterItem;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class ClientLivingEntityRendererMixin<T extends LivingEntity> {

    @Inject(method = "setupTransforms", at = @At("TAIL"))
    private void simplymore$transform(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale, CallbackInfo ci) {
        double currentDuration = ClientActiveAbilityManager.CLIENT.getCurrentDuration(entity, ActiveAbilityManager.Type.DRILL);
        if(entity.getFirstPassenger() instanceof LivingEntity passenger) currentDuration = Math.max(currentDuration, ClientActiveAbilityManager.CLIENT.getCurrentDuration(passenger, ActiveAbilityManager.Type.DRILL));

        if(currentDuration == 0) return;

        double duration = MoundshifterItem.SETTINGS.maxDrillTime;
        double ticksUsed = duration - currentDuration;

        float height = entity.getHeight();
        if(entity.getVehicle() instanceof LivingEntity vehicle) height += vehicle.getHeight();

        if(ticksUsed <= 10) {
            height = MathUtils.clampedLerp((float) ticksUsed, 0, 10, 0, height);
        }

        matrices.translate(0, -height, 0);
    }

    @Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void simplymore$stopRender(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        double currentDuration = ClientActiveAbilityManager.CLIENT.getCurrentDuration(livingEntity, ActiveAbilityManager.Type.DRILL);
        if(livingEntity.getFirstPassenger() instanceof LivingEntity passenger) currentDuration = Math.max(currentDuration, ClientActiveAbilityManager.CLIENT.getCurrentDuration(passenger, ActiveAbilityManager.Type.DRILL));

        double duration = MoundshifterItem.SETTINGS.maxDrillTime;
        double ticksUsed = duration - currentDuration;

        if(currentDuration > 0 && ticksUsed > 10) ci.cancel();
    }

    @ModifyReturnValue(method = "getShadowRadius(Lnet/minecraft/entity/LivingEntity;)F", at = @At("TAIL"))
    private float simplymore$removeShadow(float original, LivingEntity entity) {
        double currentDuration = ClientActiveAbilityManager.CLIENT.getCurrentDuration(entity, ActiveAbilityManager.Type.DRILL);
        if(entity.getFirstPassenger() instanceof LivingEntity passenger) currentDuration = Math.max(currentDuration, ClientActiveAbilityManager.CLIENT.getCurrentDuration(passenger, ActiveAbilityManager.Type.DRILL));

        if(currentDuration > 0) {
            double duration = MoundshifterItem.SETTINGS.maxDrillTime;
            double ticksUsed = duration - currentDuration;
            return MathUtils.clampedLerp((float) ticksUsed, 0, 10, original, 0);
        }

        return original;
    }
}
