package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.client.world.PlayerLunacyManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;
import java.util.Set;

@Mixin(EntityRenderDispatcher.class)
public class ClientEntityRenderDispatcherMixin<T extends Entity> {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/entity/Entity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void simplymore$modifyRender(EntityRenderer<T> instance, Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        matrices.push();
        if(entity instanceof LivingEntity livingEntity) {
            float rot = RenderUtils.getModelRotationOverride(livingEntity, tickDelta, yaw);
            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(rot - yaw));

            simplymore$applyLunacy(instance, livingEntity, yaw, tickDelta, matrices, vertexConsumers, light, PlayerLunacyManager.get(livingEntity), original);
        }

        original.call(instance, entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }

    @Unique
    private void simplymore$applyLunacy(EntityRenderer<T> instance, LivingEntity entity, float yaw, float tickDelta, MatrixStack stack, VertexConsumerProvider vertexConsumers, int light, PlayerLunacyManager.PlayerLunacyData<?> lunacyData, Operation<Void> original) {
        if(lunacyData == null) return;
        if(MinecraftClient.getInstance().player == null) return;

        switch (lunacyData.type()) {
            case OFFSET -> {
                if(!(lunacyData.data() instanceof Vec3d offset)) return;
                stack.translate(offset.getX(), offset.getY(), offset.getZ());
            }
            case DUPLICATED -> {
                if(!(lunacyData.data() instanceof Set<?> positions)) return;
                for(Object pos : positions) {
                    if(pos instanceof BlockPos blockPos) {
                        stack.push();

                        Vec3d entityPos = entity.getLerpedPos(tickDelta);
                        stack.translate(blockPos.getX() - entityPos.getX(), blockPos.getY() - entityPos.getY() + 1, blockPos.getZ() - entityPos.getZ());

                        original.call(instance, entity, yaw, tickDelta, stack, vertexConsumers, light);
                        stack.pop();
                    }
                }
            }
        }
    }

    @ModifyVariable(method = "renderFire", at = @At(value = "STORE"), ordinal = 2)
    private Sprite simplymore$modifyFire(Sprite original, @Local int l, @Local(argsOnly = true) Entity entity) {
        if(!(entity instanceof LivingEntity target)) return original;

        Optional<Pair<SpriteIdentifier, SpriteIdentifier>> override = RenderUtils.getFireOverride(target);
        if(override.isEmpty()) return original;

        return (l % 2 == 0 ? override.get().getLeft() : override.get().getRight()).getSprite();
    }
}
