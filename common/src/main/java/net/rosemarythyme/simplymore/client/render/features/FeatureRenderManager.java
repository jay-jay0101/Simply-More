package net.rosemarythyme.simplymore.client.render.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import net.rosemarythyme.simplymore.item.uniques.BrassturnItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.InventoryUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import net.sweenus.simplyswords.client.api.ObserverStatusEffectClientApi;

import java.util.List;

public class FeatureRenderManager {
    public static void renderFeatures(Camera camera, RenderTickCounter tickCounter, VertexConsumerProvider vertexConsumers) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ClientWorld world = MinecraftClient.getInstance().world;
        if(world == null || player == null) return;

        MatrixStack stack = new MatrixStack();
        Vec3d cameraPos = camera.getPos();

        List<LivingEntity> closeEntities = world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(32), LivingEntity::isAlive);
        RenderUtils.TargetData target = RenderUtils.getTarget(player);

        for (LivingEntity entity : closeEntities) {
            stack.push();

            Vec3d pos = entity.getLerpedPos(tickCounter.getTickDelta(false));
            stack.translate(
                    pos.x - cameraPos.x,
                    pos.y - cameraPos.y,
                    pos.z - cameraPos.z
            );

            FeatureRenderManager.render(entity, stack, vertexConsumers, player, camera, tickCounter, world);
            if(entity.equals(target.target())) {
                FeatureRenderManager.tryRenderTarget(entity, target, stack, vertexConsumers);
            }

            stack.pop();
        }
    }

    public static void render(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumers, ClientPlayerEntity player, Camera camera, RenderTickCounter tickCounter, ClientWorld world) {
        if(InventoryUtils.isHolding(entity, ItemRegistry.SOULFRACTURE.get())) {
            SoulfractureAuraRenderer.render(entity, stack, vertexConsumers);
        }

        if(InventoryUtils.isHolding(entity, ItemRegistry.BLADE_OF_THE_GROTESQUE.get())) {
            BladeOfTheGrotesqueAuraRenderer.render(entity, stack, vertexConsumers, WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getBlockPos()));
        }

        if(ClientActiveAbilityManager.CLIENT.isInAbility(entity, ActiveAbilityManager.Type.VIPERS_CALL)) {
            VipersCallAuraRenderer.render(entity, stack, vertexConsumers);
        }

        if(ClientActiveAbilityManager.CLIENT.isInAbility(player, ActiveAbilityManager.Type.HARVEST)) {
            if(TargetUtils.canTarget(player, entity, TargetUtils.TargetType.ENEMIES)) {
                BloodHarvesterSenseRenderer.render(entity, stack, vertexConsumers, camera);
            }
        }

        if(ObserverStatusEffectClientApi.isActive(entity, StatusEffectRegistry.BLESSING.getId())) {
            BlessingEffectFeatureRenderer.render(entity, stack, tickCounter.getTickDelta(true), vertexConsumers);
        }

        if(ClientActiveAbilityManager.CLIENT.isInAbility(entity, ActiveAbilityManager.Type.WRAITH)) {
            WraithEyesRenderer.render(entity, stack, vertexConsumers, tickCounter.getTickDelta(true));
        }

        tryRenderBrassturn(entity, stack, vertexConsumers, tickCounter, world);
    }

    private static void tryRenderBrassturn(LivingEntity entity, MatrixStack stack, VertexConsumerProvider vertexConsumers, RenderTickCounter tickCounter, ClientWorld world) {
        ItemStack brassturn = entity.getStackInHand(Hand.MAIN_HAND);
        if(brassturn.getItem() != ItemRegistry.BRASSTURN.get()
                || !BrassturnItem.isSecondaryEffectUnlocked(brassturn)) {
            brassturn = entity.getStackInHand(Hand.OFF_HAND);
        }

        if(brassturn.getItem() == ItemRegistry.BRASSTURN.get()
                && BrassturnItem.isSecondaryEffectUnlocked(brassturn)) {
            BrassturnCogFeatureRenderer.render(world, stack, tickCounter.getTickDelta(true), vertexConsumers, WorldRenderer.getLightmapCoordinates(world, entity.getBlockPos()), brassturn);
        }
    }

    public static void tryRenderTarget(LivingEntity entity, RenderUtils.TargetData data, MatrixStack stack, VertexConsumerProvider vertexConsumers) {
        if(data.target().equals(entity)) {
            TargetRenderer.render(data, stack, vertexConsumers);
        }
    }
}
