package net.rosemarythyme.simplymore.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.rosemarythyme.simplymore.SimplyMore;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> SOUND = DeferredRegister.create(SimplyMore.ID, RegistryKeys.SOUND_EVENT);

    public static RegistrySupplier<SoundEvent> COINS = registerSound("coins");
    public static RegistrySupplier<SoundEvent> SUMMON_GUARDIAN = registerSound("summon_guardian");

    public static RegistrySupplier<SoundEvent> registerSound(String id) {
        return SOUND.register(id, () -> SoundEvent.of(SimplyMore.identifier(id)));
    }

    public static void register() {
        SOUND.register();
    }
}
