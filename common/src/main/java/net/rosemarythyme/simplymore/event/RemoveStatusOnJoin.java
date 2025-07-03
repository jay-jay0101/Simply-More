package net.rosemarythyme.simplymore.event;

import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

public class RemoveStatusOnJoin implements PlayerEvent.PlayerJoin {
    @Override
    public void join(ServerPlayerEntity player) {
        player.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.GRASPING));
    }
}

