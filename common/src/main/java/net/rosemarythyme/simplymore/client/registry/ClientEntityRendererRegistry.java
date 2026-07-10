package net.rosemarythyme.simplymore.client.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.rosemarythyme.simplymore.client.models.CrowEntityModel;
import net.rosemarythyme.simplymore.client.render.entity.CrowEntityRenderer;
import net.rosemarythyme.simplymore.registry.EntityRegistry;

public class ClientEntityRendererRegistry {
    @Environment(EnvType.CLIENT)
    public static void register() {
        EntityRendererRegistry.register(EntityRegistry.CROW, CrowEntityRenderer::new);
        EntityModelLayerRegistry.register(CrowEntityModel.CROW_LAYER, CrowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.GHOST_FALLING_BLOCK, FallingBlockEntityRenderer::new);
    }
}
