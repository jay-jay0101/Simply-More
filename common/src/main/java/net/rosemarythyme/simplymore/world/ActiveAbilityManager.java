package net.rosemarythyme.simplymore.world;

import com.google.common.collect.HashMultimap;
import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.rosemarythyme.simplymore.networking.s2c.S2CAbilityManagerPacket;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.world.abilities.*;

import java.util.*;

public class ActiveAbilityManager {
    public static final ActiveAbilityManager SERVER = new ActiveAbilityManager();

    protected final List<ActiveAbility> activeAbilities = new ArrayList<>();
    public record ActiveAbility(LivingEntity owner, int duration, int remainingDuration, Type type) {
        private int tick() {
            return this.type.implementation.tick(this);
        }

        private boolean shouldContinue() {return this.type.implementation.shouldContinue(this);}
        private int tickOutro() {return this.type.implementation.tickOutro(this);}

        public ActiveAbility onOtherEntity(LivingEntity other) {return new ActiveAbility(other, duration, remainingDuration, type);}
        public ActiveAbility setRemainingDuration(int time) {
            return new ActiveAbility(owner, duration, time, type);
        }
    }

    public enum Type {
        HARVEST(new HarvestAbilityType()),
        DRILL(new DrillAbilityType()),
        STATUE(new StatueAbilityType()),
        PETRIFIED(new PetrifiedAbilityType()),
        VIPERS_CALL(new VipersCallAbilityType()),
        GRASPING(new GraspingAbilityType()),
        ;

        final ActiveAbilityType implementation;
        Type(ActiveAbilityType implementation) {
            this.implementation = implementation;
        }
    }

    public void clear() {
        activeAbilities.clear();
    }

    public float getInOutStrength(LivingEntity player, Type type) {
        if(!isInAbility(player, type)) return 0f;

        float durationPercentage = (float) this.getCurrentDuration(player, type) / this.getDuration(player, type);
        float durationStrength = 1f;

        if(durationPercentage <= 0.1f) {
            durationStrength =  MathUtils.clampedLerp(durationPercentage, 0, 0.1f, 0f, 1f);
        }

        if(durationPercentage >= 0.9f) {
            durationStrength = 1f - MathUtils.clampedLerp(durationPercentage, 0.9f, 1f, 0f, 1f);
        }

        return durationStrength;
    }

    public void stop(LivingEntity owner, Type type) {
        Optional<ActiveAbility> ability = get(owner, type);
        if(ability.isEmpty()) return;

        remove(owner, type, true);
        type.implementation.onFinish(ability.get());
    }

    protected void add(LivingEntity owner, Type type, int duration, int currentDuration) {
        if(owner == null) return;
        remove(owner, type, false);

        activeAbilities.add(new ActiveAbility(owner, duration, currentDuration, type));
        owner.getAttributes().addTemporaryModifiers(type.implementation.getModifiers(HashMultimap.create(), owner));
    }

    public void start(LivingEntity owner, Type type, int duration) {
        add(owner, type, duration, duration);
    }

    private void removeAttributes(Type type, LivingEntity owner) {
        owner.getAttributes().removeModifiers(type.implementation.getModifiers(HashMultimap.create(), owner));
    }

    protected void remove(LivingEntity owner, Type type, boolean updateClient) {
        Optional<ActiveAbility> ability = get(owner, type);
        removeAttributes(type, owner);

        if(ability.isEmpty()) return;
        ActiveAbility active = ability.get();

        activeAbilities.remove(active);
        if(updateClient) {
            sync(new ActiveAbility(active.owner, active.duration, 0, active.type));
        }
    }

    public boolean isInAbility(LivingEntity owner, Type type) {
        return get(owner, type).isPresent();
    }

    public int getCurrentDuration(LivingEntity owner, Type type) {
        Optional<ActiveAbility> ability = get(owner, type);
        return ability.isEmpty() ? 0 : ability.get().remainingDuration;
    }

    public int getDuration(LivingEntity owner, Type type) {
        Optional<ActiveAbility> ability = get(owner, type);
        return ability.isEmpty() ? 0 : ability.get().duration;
    }

    public Optional<ActiveAbility> get(LivingEntity owner, Type type) {
        return new ArrayList<>(activeAbilities).stream().filter((a) ->
                a.owner() == owner &&
                        a.type() == type
        ).findFirst();
    }

    private void sync(ActiveAbility ability) {
        List<ServerPlayerEntity> players = ability.owner.getWorld().getPlayers().stream().map((player -> (ServerPlayerEntity) player)).toList();
        NetworkManager.sendToPlayers(players, new S2CAbilityManagerPacket(ability.owner, ability.duration, ability.remainingDuration, ability.type));
    }

    public void tick() {
        List<ActiveAbility> remaining = new ArrayList<>();
        List<ActiveAbility> finishing = new ArrayList<>();

        for(ActiveAbility ability : new ArrayList<>(activeAbilities)) {
            if(ability.owner == null) continue;

            int time = ability.tick();
            if(!ability.shouldContinue()) {
                time = ability.tickOutro();
            }

            ability = ability.setRemainingDuration(time);
            if(!ability.owner.isAlive() || ability.remainingDuration <= 0) {
                finishing.add(ability);
                continue;
            }

            remaining.add(ability);
            if(ability.remainingDuration == ability.duration - 1 || ability.remainingDuration % 5 == 0) {
                sync(ability);
            }
        }

        finishing.forEach(ability -> stop(ability.owner, ability.type));

        activeAbilities.clear();
        activeAbilities.addAll(remaining);
    }


    public boolean isDrilling(LivingEntity entity) {
        return isInAbility(entity, Type.DRILL) || (entity.getFirstPassenger() instanceof LivingEntity rider && isInAbility(rider, Type.DRILL));
    }

    public boolean isStatue(LivingEntity entity) {
        return isInAbility(entity, Type.STATUE) || isInAbility(entity, Type.PETRIFIED);
    }
}
