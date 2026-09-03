package net.rosemarythyme.simplymore.client.registry;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EvokerFangsEntityRenderer;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.rosemarythyme.simplymore.client.models.*;
import net.rosemarythyme.simplymore.client.render.entity.*;
import net.rosemarythyme.simplymore.registry.EntityRegistry;

public class ClientEntityRendererRegistry {
    @Environment(EnvType.CLIENT)
    public static void register() {
        EntityRendererRegistry.register(EntityRegistry.VOLCANIC_VENT, VolcanicVentEntityRenderer::new);
        EntityModelLayerRegistry.register(VolcanicVentModel.LAYER, VolcanicVentModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.ICEWALL, IcewallEntityRenderer::new);
        EntityModelLayerRegistry.register(IcewallModel.LAYER, IcewallModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.CROW, CrowEntityRenderer::new);
        EntityModelLayerRegistry.register(CrowEntityModel.LAYER, CrowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(EntityRegistry.GREAT_SLITHER_FANG, EvokerFangsEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.GHOST_FALLING_BLOCK, FallingBlockEntityRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.LAVA, LavaEntityRenderer::new);

        EntityModelLayerRegistry.register(CannonballModel.LAYER, CannonballModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityRegistry.CANNONBALL, CannonballRenderer::new);

        EntityModelLayerRegistry.register(LightOrbModel.LAYER, LightOrbModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityRegistry.LIGHT_ORB, LightOrbEntityRenderer::new);

        EntityModelLayerRegistry.register(LightbeamModel.LAYER, LightbeamModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityRegistry.LIGHTBEAM, LightbeamEntityRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.SOUL_FRAGMENT, SoulFragmentEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LIGHTNING_POINT, LightningPointEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.EARTHQUAKE, EarthquakeEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.DUG_BLOCK, DugBlockEntityRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.STATUE, StatueRenderer::new);

        EntityModelLayerRegistry.register(SpiritualTormentorModel.LAYER, SpiritualTormentorModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityRegistry.SPIRITUAL_TORMENTOR, SpiritualTormentorRenderer::new);

        EntityModelLayerRegistry.register(SpiritualGuardianModel.LAYER, SpiritualGuardianModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityRegistry.SPIRITUAL_GUARDIAN, SpiritualGuardianRenderer::new);
    }
}
