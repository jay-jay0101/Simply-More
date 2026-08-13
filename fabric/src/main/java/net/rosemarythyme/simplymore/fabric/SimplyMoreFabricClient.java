package net.rosemarythyme.simplymore.fabric;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.rosemarythyme.simplymore.client.screen.ReformingScreen;
import net.rosemarythyme.simplymore.registry.ScreenHandlerRegistry;

@Environment(EnvType.CLIENT)
public class SimplyMoreFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientLifecycleEvent.CLIENT_STARTED.register(this::register);
    }

    public void register(MinecraftClient client) {
        MenuRegistry.registerScreenFactory(
                ScreenHandlerRegistry.REFORM.get(),
                ReformingScreen::new
        );
    }
}