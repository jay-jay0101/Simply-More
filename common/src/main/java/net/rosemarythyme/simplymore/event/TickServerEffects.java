package net.rosemarythyme.simplymore.event;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.MinecraftServer;
import net.rosemarythyme.simplymore.world.SwingCacheManager;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.PlayerItemUseManager;

public class TickServerEffects implements TickEvent.Server {
    @Override
    public void tick(MinecraftServer instance) {
        PlayerItemUseManager.tick(instance);
        ActiveAbilityManager.SERVER.tick();
        SwingCacheManager.cleanCache();
    }
}

