package net.rosemarythyme.simplymore.client.world;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.rosemarythyme.simplymore.effect.LunacyEffect;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;

import java.util.*;

public class PlayerLunacyManager {
    private static final Map<UUID, PlayerLunacyData<?>> LUNACY_MAP = new HashMap<>();

    public enum Type {
        OFFSET,
        INVISIBLE,
        DUPLICATED
    }

    public record PlayerLunacyData<T>(Type type, T data, int duration) {
        public PlayerLunacyData(Type type, T data) {
            this(type, data, LunacyEffect.LUNACY_SUBEFFECT_DURATION);
        }

        public static PlayerLunacyData<?> create(ClientPlayerEntity player, int strength) {
            Random random = player.getRandom();
            int halfStrength = (int) Math.max(1, Math.ceil(strength / 2f));

            return switch (player.getRandom().nextBetween(0, 2)) {
                case 0 -> {
                    Vec3d offset = MathUtils.getDirectionalVector(random.nextFloat() * 360f, 0).multiply(random.nextFloat() * halfStrength);
                    yield new PlayerLunacyData<>(Type.OFFSET, offset);
                }
                case 1 -> new PlayerLunacyData<>(Type.INVISIBLE, null);
                case 2 -> {
                    Set<BlockPos> positions = MathUtils.getPositionsOnFloor(player.getWorld(), player.getBlockPos(), 10, 3, 5, halfStrength);
                    yield new PlayerLunacyData<>(Type.DUPLICATED, positions);
                }
                default -> null;
            };
        }

        public PlayerLunacyData<?> tick(ClientPlayerEntity player, UUID uuid) {
            Entity entity = TargetUtils.getEntityByUUID(player, uuid);
            if(!(entity instanceof LivingEntity livingEntity)) return null;

            if(!livingEntity.isAlive()) return null;

            int newDuration = this.duration - 1;
            if(newDuration <= 0) return null;

            return new PlayerLunacyData<>(this.type, this.data, newDuration);
        }
    }

    public static PlayerLunacyData<?> get(LivingEntity entity) {
        return LUNACY_MAP.get(entity.getUuid());
    }

    public static void tickEffect(ClientPlayerEntity player, StatusEffectInstance instance) {
        int level = Math.max(1, instance.getAmplifier() + 1);
        int interim = 30 + 100 - (10 * level);

        if(instance.getDuration() % interim == 19) {
            TargetList targets = TargetUtils.cubeSelect(player, player.getPos(), 25, TargetUtils.TargetType.OTHERS_POSITIVELY)
                .filter((entity) -> !LUNACY_MAP.containsKey(entity.getUuid()));

            int count = (int) Math.ceil(targets.size() * level  / 100f);

            for(int i = 0; i < count; i++) {
                Optional<LivingEntity> target = targets.getAny();
                if (target.isEmpty()) return;

                LUNACY_MAP.put(target.get().getUuid(), PlayerLunacyData.create(player, level));
                targets = targets.exclude(target.get());
            }
        }
    }

    public static void tickEffects(ClientPlayerEntity player) {
        for (Map.Entry<UUID, PlayerLunacyData<?>> data : new HashSet<>(LUNACY_MAP.entrySet())) {
            PlayerLunacyData<?> newData = data.getValue().tick(player, data.getKey());
            if(newData == null) {
                LUNACY_MAP.remove(data.getKey());
            } else {
                LUNACY_MAP.put(data.getKey(), newData);
            }
        }
    }

    public static void tick(ClientPlayerEntity player) {
        StatusEffectInstance instance = player.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LUNACY));
        if(instance == null) {
            LUNACY_MAP.clear();
        } else {
            tickEffect(player, instance);
            tickEffects(player);
        }
    }
}
