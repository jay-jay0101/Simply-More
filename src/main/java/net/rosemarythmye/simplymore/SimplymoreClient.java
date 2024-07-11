package net.rosemarythmye.simplymore;

import dev.architectury.event.events.client.ClientTooltipEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sweenus.simplyswords.SimplySwords;

@Environment(EnvType.CLIENT)
public class SimplymoreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
    }
}
