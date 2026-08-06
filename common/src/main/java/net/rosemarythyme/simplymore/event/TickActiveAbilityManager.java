package net.rosemarythyme.simplymore.event;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.MinecraftServer;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;

public class TickActiveAbilityManager implements TickEvent.Server {
    @Override
    public void tick(MinecraftServer instance) {
        ActiveAbilityManager.SERVER.tick();
    }
}

