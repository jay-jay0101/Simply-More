package net.rosemarythyme.simplymore.event;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.rosemarythyme.simplymore.client.camera.ScreenshakeManager;

public class TickScreenshake implements ClientTickEvent.Client {
    @Override
    public void tick(MinecraftClient instance) {
        ScreenshakeManager.tickScreenShakes();
    }
}
