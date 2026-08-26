package net.rosemarythyme.simplymore.world;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.rosemarythyme.simplymore.util.MathUtils;

import java.util.*;

public class ClientActiveAbilityManager extends ActiveAbilityManager {
    public static final ClientActiveAbilityManager CLIENT = new ClientActiveAbilityManager();

    @Override
    public void tick() {
        if(MinecraftClient.getInstance().isPaused()) return;

        List<ActiveAbility> remaining = new ArrayList<>();
        for(ActiveAbility ability : new ArrayList<>(activeAbilities)) {
            if(ability.owner() == null) continue;

            ability = ability.setRemainingDuration(ability.remainingDuration() - 1);
            if(ability.remainingDuration() > 0) {
                remaining.add(ability);
            }
        }

        activeAbilities.clear();
        activeAbilities.addAll(remaining);
    }

    public void clearCache() {
        STRENGTH_CACHE.clear();
    }

    private final Map<Type, Float> STRENGTH_CACHE = new HashMap<>();

    public float getAbilityStrength(Type type) {
        if(STRENGTH_CACHE.containsKey(type)) return STRENGTH_CACHE.get(type);

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) {
            STRENGTH_CACHE.put(type, 0f);
            return 0f;
        }

        OptionalDouble strength = activeAbilities.stream().filter((ability) -> ability.type() == type).mapToDouble((ability) -> {
            float durationPercentage = (float) ability.remainingDuration() / ability.duration();
            float durationStrength = 1f;

            if(durationPercentage <= 0.1f) {
                durationStrength =  MathUtils.clampedLerp(durationPercentage, 0, 0.1f, 0f, 1f);
            }

            if(durationPercentage >= 0.9f) {
                durationStrength = 1f - MathUtils.clampedLerp(durationPercentage, 0.9f, 1f, 0f, 1f);
            }

            float rangePercentage = (float) (ability.owner().getPos().distanceTo(player.getPos()) / type.range);
            float rangeStrength = 1f;

            if(rangePercentage >= 0.9f) {
                rangeStrength = 1f - MathUtils.clampedLerp(rangePercentage, 0.9f, 1f, 0f, 1f);
            }

            return rangeStrength * durationStrength;
        }).max();

        float value = 0f;
        if(strength.isPresent()) value = (float) strength.getAsDouble();

        STRENGTH_CACHE.put(type, value);
        return value;
    }
}
