package net.rosemarythyme.simplymore.client.render.features;

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
import net.rosemarythyme.simplymore.item.uniques.BrassturnItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.InventoryUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import net.sweenus.simplyswords.client.api.ObserverStatusEffectClientApi;

public class FeatureRenderManager {
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

}
