package net.rosemarythyme.simplymore.registry;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.screen.ReformingScreenHandler;

public class ScreenHandlerRegistry {
    public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLERS =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.SCREEN_HANDLER);

    public static final RegistrySupplier<ScreenHandlerType<ReformingScreenHandler>> REFORM =
            SCREEN_HANDLERS.register("reforming",
                    () -> MenuRegistry.ofExtended(ReformingScreenHandler::new));

    public static void register() {
        SCREEN_HANDLERS.register();
    }
}
