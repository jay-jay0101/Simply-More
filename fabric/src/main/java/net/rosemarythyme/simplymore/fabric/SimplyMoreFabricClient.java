package net.rosemarythyme.simplymore.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SimplyMoreFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
//        registerParticles();
    }

//    public void registerParticles() {
//        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.HOLY_WATER.get(), StaticWaterParticle.HolyWaterFactory::new);
//        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.UNHOLY_WATER.get(), StaticWaterParticle.UnholyWaterFactory::new);
//    }
}