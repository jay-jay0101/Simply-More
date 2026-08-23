package net.rosemarythyme.simplymore.event;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.rosemarythyme.simplymore.client.camera.ScreenshakeManager;
import net.rosemarythyme.simplymore.util.HudUtils;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;

public class TickClientEffects implements ClientTickEvent.Client {
    @Override
    public void tick(MinecraftClient instance) {
        ScreenshakeManager.tickScreenShakes();

        ClientActiveAbilityManager.CLIENT.clearCache();
        ClientActiveAbilityManager.CLIENT.tick();

        if(instance.player != null && instance.player.age % 10 == 0) {
            HudUtils.clearCache();
        }
    }
}
