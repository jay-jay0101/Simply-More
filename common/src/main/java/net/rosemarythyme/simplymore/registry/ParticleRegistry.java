package net.rosemarythyme.simplymore.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.RegistryKeys;
import net.rosemarythyme.simplymore.SimplyMore;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.PARTICLE_TYPE);

    public static final RegistrySupplier<SimpleParticleType> HOLY_WATER = PARTICLES.register(
            "holy_water",
            () -> new SimpleParticleType(true) {}
    );

    public static final RegistrySupplier<SimpleParticleType> UNHOLY_WATER = PARTICLES.register(
            "unholy_water",
            () -> new SimpleParticleType(true) {}
    );

    public static void register() {
        PARTICLES.register();
    }
}
