package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.client.render.features.SoulfractureAuraRenderer;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.EntityUtils;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;draw()V"))
    private void simplymore$auras(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci, @Local VertexConsumerProvider.Immediate vertexConsumers) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientWorld world = MinecraftClient.getInstance().world;
        if(world == null || player == null) return;

        MatrixStack stack = new MatrixStack();
        Vec3d cameraPos = camera.getPos();


        for (LivingEntity livingEntity : world.getEntitiesByClass(
                LivingEntity.class,
                player.getBoundingBox().expand(32),
                LivingEntity::isAlive
        )) {
            if(EntityUtils.isHolding(livingEntity, ItemRegistry.SOULFRACTURE.get())) {
                stack.push();
                Vec3d pos = livingEntity.getLerpedPos(tickCounter.getTickDelta(false));
                stack.translate(
                        pos.x - cameraPos.x,
                        pos.y - cameraPos.y,
                        pos.z - cameraPos.z
                );

                SoulfractureAuraRenderer.render(livingEntity, stack, vertexConsumers);
                stack.pop();
            }
        }
    }
}
