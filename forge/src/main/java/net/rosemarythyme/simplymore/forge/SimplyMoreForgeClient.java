package net.rosemarythyme.simplymore.forge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

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
}