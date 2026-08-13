package net.rosemarythyme.simplymore.registry.item;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKeys;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.components.*;

public class ItemComponentRegistry {
    public static final DeferredRegister<ComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(SimplyMore.ID, RegistryKeys.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<ComponentType<CounterComponent>> COUNTER = COMPONENT_TYPES.register("counter",
            () -> ComponentType.<CounterComponent>builder()
                    .codec(CounterComponent.CODEC)
                    .packetCodec(CounterComponent.PACKET_CODEC).build());

    public static final RegistrySupplier<ComponentType<ConsecutiveHitsComponent>> CONSECUTIVE_HITS = COMPONENT_TYPES.register("consecutive_hits",
            () -> ComponentType.<ConsecutiveHitsComponent>builder()
                    .codec(ConsecutiveHitsComponent.CODEC)
                    .packetCodec(ConsecutiveHitsComponent.PACKET_CODEC).build());

    public static final RegistrySupplier<ComponentType<ReformTypeComponent>> REFORM = COMPONENT_TYPES.register("reform_block",
            () -> ComponentType.<ReformTypeComponent>builder()
                    .codec(ReformTypeComponent.CODEC)
                    .packetCodec(ReformTypeComponent.PACKET_CODEC).build());

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

    public static void register() {
        COMPONENT_TYPES.register();
    }
}
