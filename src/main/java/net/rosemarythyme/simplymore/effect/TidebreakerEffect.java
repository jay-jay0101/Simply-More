package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

public class TidebreakerEffect extends StatusEffect {


    public TidebreakerEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity affectedEntity, int amplifier) {
        if (!affectedEntity.getWorld().isClient) {
            ServerWorld serverWorld = (ServerWorld) affectedEntity.getWorld();
            spawnParticles(serverWorld, affectedEntity);
            playRainSound(serverWorld, affectedEntity);
            applyInsanityEffect(affectedEntity, serverWorld);
        }

        super.applyUpdateEffect(affectedEntity, amplifier);
    }

    private void spawnParticles(ServerWorld serverWorld, LivingEntity affectedEntity) {
        serverWorld.spawnParticles(ParticleTypes.CLOUD, affectedEntity.getX(), affectedEntity.getY() + 5, affectedEntity.getZ(), 100, 3, 0, 3, 0.05);
        serverWorld.spawnParticles(ParticleTypes.FALLING_WATER, affectedEntity.getX(), affectedEntity.getY() + 5, affectedEntity.getZ(), 100, 3, 0, 3, 0);
    }

    private void playRainSound(ServerWorld serverWorld, LivingEntity affectedEntity) {
        if (serverWorld.getTime() % 5 == 0) {
            serverWorld.playSound(null, affectedEntity.getX(), affectedEntity.getY(), affectedEntity.getZ(), SoundEvents.WEATHER_RAIN, SoundCategory.PLAYERS, 1, 1);
        }
    }

    private void applyInsanityEffect(LivingEntity affectedEntity, ServerWorld serverWorld) {
        for (LivingEntity target : serverWorld.getNonSpectatingEntities(LivingEntity.class, new Box(affectedEntity.getX() - 3, affectedEntity.getY() - 2, affectedEntity.getZ() - 3, affectedEntity.getX() + 3, affectedEntity.getY() + 8, affectedEntity.getZ() + 3))) {
            if (target == affectedEntity || target.isTeammate(affectedEntity)) {
                continue;
            }
            target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.INSANITY, 160, 0), affectedEntity);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
