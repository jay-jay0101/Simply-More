package net.rosemarythyme.simplymore.world;

import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.networking.s2c.S2CAbilityManagerPacket;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;

import java.util.*;
import java.util.function.Function;

public class ActiveAbilityManager {
    public static final ActiveAbilityManager SERVER = new ActiveAbilityManager();

    protected final List<ActiveAbility> activeAbilities = new ArrayList<>();
    protected record ActiveAbility(LivingEntity owner, int duration, int remainingDuration, Type type) {
        private int tick() {
            return this.type.run.apply(this);
        }

        ActiveAbility setRemainingDuration(int time) {
            return new ActiveAbility(owner, duration, time, type);
        }
    }

    public enum Type {
        HARVEST(ActiveAbilityManager::harvestTick, 32);

        final Function<ActiveAbility, Integer> run;
        final double range;
        Type(Function<ActiveAbility, Integer> run, double range) {
            this.run = run;
            this.range = range;
        }
    }

    public void add(LivingEntity owner, Type type, int duration, int currentDuration) {
        if(owner == null) return;
        stop(owner, type);

        if(currentDuration == 0) return;
        activeAbilities.add(new ActiveAbility(owner, duration, currentDuration, type));
    }

    public void start(LivingEntity owner, Type type, int duration) {
        add(owner, type, duration, duration);
    }

    public void stop(LivingEntity owner, Type type) {
        activeAbilities.removeIf(ability ->
                ability.owner() == owner &&
                ability.type() == type
        );
    }

    public boolean isInAbility(LivingEntity owner, Type type) {
        return new ArrayList<>(activeAbilities).stream().anyMatch(ability ->
                ability.owner() == owner &&
                ability.type() == type
        );
    }

    public int getCurrentDuration(LivingEntity owner, Type type) {
        Optional<ActiveAbility> ability = new ArrayList<>(activeAbilities).stream().filter((a) ->
                a.owner() == owner &&
                        a.type() == type
        ).findFirst();

        if(ability.isEmpty()) return 0;

        return ability.get().remainingDuration;
    }

    private void sync(ActiveAbility ability) {
        List<ServerPlayerEntity> players = ability.owner.getWorld().getPlayers().stream().map((player -> (ServerPlayerEntity) player)).toList();
        NetworkManager.sendToPlayers(players, new S2CAbilityManagerPacket(ability.owner, ability.duration, ability.remainingDuration, ability.type));
    }

    public void tick() {
        List<ActiveAbility> remaining = new ArrayList<>();
        Set<ActiveAbility> toSync = new HashSet<>();
        for(ActiveAbility ability : new ArrayList<>(activeAbilities)) {
            if(ability.owner == null) continue;
            int time = ability.tick();

            ability = ability.setRemainingDuration(time);
            if(ability.remainingDuration > 0) {
                remaining.add(ability);
            }

            if(ability.remainingDuration == ability.duration - 1
                    || ability.remainingDuration % 5 == 0) {
                toSync.add(ability);
            }
        }

        toSync.forEach(this::sync);
        activeAbilities.clear();
        activeAbilities.addAll(remaining);
    }

    private static int harvestTick(ActiveAbility ability) {
        new TargetList(ability.owner)
                .applyEffect(StatusEffects.HASTE, 10, 3)
                .applyEffect(StatusEffects.SPEED, 10, 1);

        if(ability.remainingDuration % 10 == 0) {
            AudioVisualUtils.playSound(ability.owner.getWorld(), ability.owner.getPos(), new Sound(SoundEvents.ENTITY_WARDEN_HEARTBEAT).setPitch(1.2f));
        }

        if(!EntityUtils.isHolding(ability.owner, ItemRegistry.THE_BLOOD_HARVESTER.get())) {
            int outro = (int)(ability.duration / 10f);
            if(ability.remainingDuration > outro) {
                return outro;
            }
        }

        return ability.remainingDuration - 1;
    }
}
