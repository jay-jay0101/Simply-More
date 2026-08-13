package net.rosemarythyme.simplymore.forge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.rosemarythyme.simplymore.client.screen.ReformingScreen;
import net.rosemarythyme.simplymore.registry.ScreenHandlerRegistry;

@EventBusSubscriber(
        modid = "simplymore",
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class SimplyMoreForgeClient {
//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public static void onClientSetup(FMLClientSetupEvent event) {
//    }
//
//    @SubscribeEvent
//    public static void onRegisterParticleFactories(RegisterParticleProvidersEvent event) {
//        ParticleRegistry.BLOOD_RAIN.ifPresent(particle -> event.registerSpriteSet(
//                particle,
//                BloodRainParticle.Factory::new
//        ));
//    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ScreenHandlerRegistry.REFORM.get(), ReformingScreen::new);
    }
}