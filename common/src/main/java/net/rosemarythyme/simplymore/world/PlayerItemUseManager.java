package net.rosemarythyme.simplymore.world;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;

import java.util.HashMap;
import java.util.Map;

public class PlayerItemUseManager {
    private static final Map<PlayerEntity, Usage> CACHE = new HashMap<>();
    private record Usage(ItemStack item, Hand hand, long time) {
        private static Usage of(PlayerEntity player) {
            if(player == null) return null;
            if(!player.isAlive()) return null;
            if(!player.isUsingItem()) return null;

            return new Usage(player.getActiveItem(), player.getActiveHand(), player.getWorld().getTime());
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Usage(ItemStack otherItem, Hand otherHand, long otherTime))) return false;
            return time == otherTime && hand == otherHand && item.getItem() == otherItem.getItem();
        }
    }

    public static void stop(PlayerEntity player, ItemStack stack, boolean triggerStop) {
        if(player.getActiveItem().getItem() != stack.getItem()) return;

        if(triggerStop) {
            stack.getItem().onStoppedUsing(stack, player.getWorld(), player, player.getItemUseTimeLeft());
        }

        CACHE.remove(player);

        player.stopUsingItem();
    }


    public static void tick(MinecraftServer server) {
        Map<PlayerEntity, Usage> newMap = new HashMap<>();

        for(PlayerEntity player : server.getPlayerManager().getPlayerList()) {
            Usage usage = Usage.of(player);

            if(CACHE.containsKey(player)) {
                Usage prevUsage = CACHE.get(player);

                if(usage != null) {
                    usage = new Usage(usage.item, usage.hand, prevUsage.time);
                }

                if(!prevUsage.equals(usage)) {
                    stop(player, prevUsage);
                    continue;
                }
            }

            if(usage != null) {
                newMap.put(player, usage);
            }
        }

        CACHE.clear();
        CACHE.putAll(newMap);
    }

    private static void stop(PlayerEntity player, Usage usage) {
        player.clearActiveItem();
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;

        if(usage.item.getItem() instanceof StoppableAbilityItem abilityItem) {
            int maxUseTime = usage.item.getItem().getMaxUseTime(usage.item, player);
            int used = (int) (player.getWorld().getTime() - usage.time);
            int remaining = maxUseTime - used;

            abilityItem.stop(usage.item, serverWorld, player, remaining);
        }
    }
}
