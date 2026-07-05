package net.rosemarythyme.simplymore.registry;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKeys;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.components.DayTimeComponent;
import net.rosemarythyme.simplymore.item.components.GrabbedComponent;
import net.rosemarythyme.simplymore.item.components.UsageComponent;

public class ItemComponentRegistry {
    public static final DeferredRegister<ComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(SimplyMore.ID, RegistryKeys.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<ComponentType<CounterComponent>> COUNTER = COMPONENT_TYPES.register("counter",
            () -> ComponentType.<CounterComponent>builder()
                    .codec(CounterComponent.CODEC)
                    .packetCodec(CounterComponent.PACKET_CODEC).build());

    public static final RegistrySupplier<ComponentType<UsageComponent>> USAGE = COMPONENT_TYPES.register("usage",
            () -> ComponentType.<UsageComponent>builder()
                    .codec(UsageComponent.CODEC)
                    .packetCodec(UsageComponent.PACKET_CODEC).build());

    public static final RegistrySupplier<ComponentType<DayTimeComponent>> DAYTIME = COMPONENT_TYPES.register("daytime",
            () -> ComponentType.<DayTimeComponent>builder()
                    .codec(DayTimeComponent.CODEC)
                    .packetCodec(DayTimeComponent.PACKET_CODEC).build());

    public static final RegistrySupplier<ComponentType<GrabbedComponent>> GRABBED = COMPONENT_TYPES.register("grabbed",
            () -> ComponentType.<GrabbedComponent>builder()
                    .codec(GrabbedComponent.CODEC)
                    .packetCodec(GrabbedComponent.PACKET_CODEC).build());

    public static final RegistrySupplier<ComponentType<Boolean>> CHANGE = COMPONENT_TYPES.register("should_change",
            () -> ComponentType.<Boolean>builder()
                    .codec(Codec.BOOL)
                    .packetCodec(PacketCodecs.BOOL).build());

    public static void registerModComponents() {
        COMPONENT_TYPES.register();
    }
}
