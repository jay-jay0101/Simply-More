package net.rosemarythyme.simplymore.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.rosemarythyme.simplymore.effect.LunacyEffect;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;

import java.util.*;

public class MobLunacyManager {
    private static final Map<UUID, MobLunacyData> LUNACY_MAP = new HashMap<>();

    public record MobLunacyData(Optional<LivingEntity> target, int duration) {
        public static MobLunacyData create(MobEntity entity) {
            if(LUNACY_MAP.containsKey(entity.getUuid())) return LUNACY_MAP.get(entity.getUuid());

            TargetList list = TargetUtils.cubeSelect(entity, entity.getPos(), 50, TargetUtils.TargetType.ALLIES);
            Optional<LivingEntity> target = entity.getRandom().nextBoolean() ? list.getAny() : Optional.empty();

            return new MobLunacyData(target, LunacyEffect.LUNACY_SUBEFFECT_DURATION);
        }

        public MobLunacyData tick(MinecraftServer server, UUID uuid) {
            Entity entity = TargetUtils.getEntityByUUID(server, uuid);
            if(!(entity instanceof MobEntity mob)) return null;

            if(!mob.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LUNACY))) return null;
            if(this.target.isPresent() && !this.target.get().isAlive()) return null;

            int newDuration = this.duration - 1;
            if(newDuration <= 0) return null;

            return new MobLunacyData(this.target, newDuration);
        }
    }

    public static MobLunacyData get(MobEntity entity) {
        return LUNACY_MAP.get(entity.getUuid());
    }

    public static void tickEffect(MobEntity entity, StatusEffectInstance instance) {
        int level = Math.max(1, instance.getAmplifier() + 1);
        int interim = 50 + 100 - (10 * level);

        if(instance.getDuration() % interim == 49) {
            LUNACY_MAP.put(entity.getUuid(), MobLunacyData.create(entity));
        }
    }

    public static void tick(MinecraftServer server) {
        for (Map.Entry<UUID, MobLunacyData> data : new HashSet<>(LUNACY_MAP.entrySet())) {
            MobLunacyData newData = data.getValue().tick(server, data.getKey());
            if(newData == null) {
                LUNACY_MAP.remove(data.getKey());
            } else {
                LUNACY_MAP.put(data.getKey(), newData);
            }
        }
    }
}
