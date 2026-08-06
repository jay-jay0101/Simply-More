package net.rosemarythyme.simplymore.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SimplyMoreFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
//        LifecycleEvent.SETUP.register(this::registerParticles);
    }
//
//    public void registerParticles() {
//        ParticleProviderRegistry.register(ParticleRegistry.BLOOD_RAIN.get(), BloodRainParticle.Factory::new);
//        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.BLOOD_RAIN.get(),BloodRainParticle.Factory::new);
//    }
}