package net.rosemarythyme.simplymore.event;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;

public class TickAbilityStrength implements ClientTickEvent.Client {
    @Override
    public void tick(MinecraftClient instance) {
        ClientActiveAbilityManager.CLIENT.clearCache();
        ClientActiveAbilityManager.CLIENT.tick();
    }
}
