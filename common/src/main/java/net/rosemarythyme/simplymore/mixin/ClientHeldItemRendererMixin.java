package net.rosemarythyme.simplymore.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.rosemarythyme.simplymore.item.uniques.mimicry.MimicryItem;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class ClientHeldItemRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void simplymore$item(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if(ClientActiveAbilityManager.CLIENT.isInAbility(entity, ActiveAbilityManager.Type.MIMICRY)
                && stack.getItem() instanceof MimicryItem) {
            ci.cancel();
        }
    }
}
