package net.rosemarythyme.simplymore.forge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.rosemarythyme.simplymore.client.SimplyMoreClientInit;

@EventBusSubscriber(
        modid = "simplymore",
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class SimplyMoreForgeClient {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(SimplyMoreClientInit::registerModelPredicates);
    }
}