package net.rosemarythyme.simplymore.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.rosemarythyme.simplymore.client.particles.StaticWaterParticle;
import net.rosemarythyme.simplymore.client.registry.ClientItemPropertyRegistry;
import net.rosemarythyme.simplymore.registry.ParticleRegistry;

@Environment(EnvType.CLIENT)
public class SimplyMoreFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientItemPropertyRegistry.register();
        registerParticles();
    }

    public void registerParticles() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.HOLY_WATER.get(), StaticWaterParticle.HolyWaterFactory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.UNHOLY_WATER.get(), StaticWaterParticle.UnholyWaterFactory::new);
    }
}