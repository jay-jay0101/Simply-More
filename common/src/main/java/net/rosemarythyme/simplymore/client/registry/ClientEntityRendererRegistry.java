package net.rosemarythyme.simplymore.client.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EvokerFangsEntityRenderer;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.rosemarythyme.simplymore.client.models.CrowEntityModel;
import net.rosemarythyme.simplymore.client.models.VolcanicVentModel;
import net.rosemarythyme.simplymore.client.render.entity.CrowEntityRenderer;
import net.rosemarythyme.simplymore.client.render.entity.LavaRenderer;
import net.rosemarythyme.simplymore.client.render.entity.VolcanicVentRenderer;
import net.rosemarythyme.simplymore.registry.EntityRegistry;

public class ClientEntityRendererRegistry {
    @Environment(EnvType.CLIENT)
    public static void register() {
        EntityRendererRegistry.register(EntityRegistry.VOLCANIC_VENT, VolcanicVentRenderer::new);
        EntityModelLayerRegistry.register(VolcanicVentModel.LAYER, VolcanicVentModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.CROW, CrowEntityRenderer::new);
        EntityModelLayerRegistry.register(CrowEntityModel.LAYER, CrowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.GREAT_SLITHER_FANG, EvokerFangsEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.GHOST_FALLING_BLOCK, FallingBlockEntityRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.LAVA, LavaRenderer::new);
    }
}
