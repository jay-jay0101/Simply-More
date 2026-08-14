package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.rosemarythyme.simplymore.client.render.features.BloodHarvesterSenseRenderer;
import net.rosemarythyme.simplymore.client.render.features.SoulfractureAuraRenderer;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(WorldRenderer.class)
public class ClientWorldRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;draw()V"))
    private void simplymore$auras(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci, @Local VertexConsumerProvider.Immediate vertexConsumers) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientWorld world = MinecraftClient.getInstance().world;
        if(world == null || player == null) return;

        MatrixStack stack = new MatrixStack();
        Vec3d cameraPos = camera.getPos();

        List<LivingEntity> closeEntities = world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(32), LivingEntity::isAlive);

        for (LivingEntity entity : closeEntities) {
            stack.push();
            Vec3d pos = entity.getLerpedPos(tickCounter.getTickDelta(false));
            stack.translate(
                    pos.x - cameraPos.x,
                    pos.y - cameraPos.y,
                    pos.z - cameraPos.z
            );

            if(EntityUtils.isHolding(entity, ItemRegistry.SOULFRACTURE.get())) {
                SoulfractureAuraRenderer.render(entity, stack, vertexConsumers);
            }

            if(ClientActiveAbilityManager.CLIENT.isInAbility(player, ActiveAbilityManager.Type.HARVEST)) {
                if(AttackUtils.canTarget(player, entity, AttackUtils.AttackTarget.ENEMIES)) {
                    BloodHarvesterSenseRenderer.render(entity, stack, vertexConsumers, camera);
                }
            }

            stack.pop();
        }
    }

    @ModifyArgs(method = "renderSky", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V", ordinal = 0))
    private void simplymore$tintFog(Args args) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if (strength > 0f) {
            args.set(0, MathHelper.lerp(strength, args.get(0), 1f));
            args.set(1, MathHelper.lerp(strength, args.get(1), 0f));
            args.set(2, MathHelper.lerp(strength, args.get(2), 0f));
        }
    }

    @ModifyArgs(method = "renderWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;color(FFFF)Lnet/minecraft/client/render/VertexConsumer;"))
    private void simplymore$tintRain(Args args) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if (strength > 0f) {
            args.set(0, 1f);
            args.set(1, 0f);
            args.set(2, 0f);
        }
    }

    @ModifyArgs(method = "renderWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;light(I)Lnet/minecraft/client/render/VertexConsumer;"))
    private void simplymore$brightenRain(Args args) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if (strength > 0f) {
            args.set(0, LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE);
        }
    }

    @Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
    private void simplymore$preventSplash(Camera camera, CallbackInfo ci) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if(strength > 0f) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "renderWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;hasPrecipitation()Z"))
    private boolean simplymore$forceRain(boolean original) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        return original || strength > 0;
    }

    @Inject(method = "renderClouds", at = @At(value = "HEAD"), cancellable = true)
    private void simplymore$removeClouds(MatrixStack matrices, Matrix4f matrix4f, Matrix4f matrix4f2, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if(strength > 0) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method = "renderWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;getPrecipitation(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome$Precipitation;"))
    private Biome.Precipitation simplymore$changePrecipitation(Biome.Precipitation original) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if(strength > 0) return Biome.Precipitation.RAIN;

        return original;
    }

    @ModifyExpressionValue(method = "renderWeather", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getRainGradient(F)F"))
    private float simplymore$gradualRain(float original) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if (strength > 0f) {
            return MathHelper.lerp(strength, original, 1f);
        }

        return original;
    }

    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    private void simplymore$preventEntityRender(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) return;

        if(player.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.DAZZLED))) {
            ci.cancel();
        }
    }
}
