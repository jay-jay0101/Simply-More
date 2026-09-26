package net.rosemarythyme.simplymore.world;

import net.minecraft.entity.LivingEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SwingCacheManager {
    private static final Map<LivingEntity, Long> SWING_CACHE = new HashMap<>();

    public static void putInCache(LivingEntity entity, long time) {
        SWING_CACHE.put(entity, time);
    }

    public static long getCache(LivingEntity entity) {
        return SWING_CACHE.getOrDefault(entity, 0L);
    }

    public static void cleanCache() {
        for(Map.Entry<LivingEntity, Long> entry : new HashSet<>(SWING_CACHE.entrySet())) {
            if(!entry.getKey().isAlive()) {
                SWING_CACHE.remove(entry.getKey());
            }
        }
    }
}
