package net.rosemarythyme.simplymore.forge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.rosemarythyme.simplymore.client.particles.StaticWaterParticle;
import net.rosemarythyme.simplymore.client.registry.ClientItemPropertyRegistry;
import net.rosemarythyme.simplymore.registry.ParticleRegistry;

@EventBusSubscriber(
        modid = "simplymore",
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class SimplyMoreForgeClient {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientItemPropertyRegistry::register);
    }

    @SubscribeEvent
    public static void onRegisterParticleFactories(RegisterParticleProvidersEvent event) {
        ParticleRegistry.HOLY_WATER.ifPresent(particle -> {
            event.registerSpriteSet(
                    particle,
                    StaticWaterParticle.HolyWaterFactory::new
            );
        });
    }
}