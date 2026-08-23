package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record CounterComponent(int min, int max, int value) {
    public static final Codec<CounterComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("min").forGetter(CounterComponent::min),
            Codec.INT.fieldOf("max").forGetter(CounterComponent::max),
            Codec.INT.fieldOf("value").forGetter(CounterComponent::value))
            .apply(instance, CounterComponent::new));

    public static final PacketCodec<RegistryByteBuf, CounterComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, CounterComponent::min,
            PacketCodecs.VAR_INT, CounterComponent::max,
            PacketCodecs.VAR_INT, CounterComponent::value,
            CounterComponent::new
    );

    public CounterComponent(int min, int max) {
        this(min, max, min);
    }

    public CounterComponent add(int addition) {
        return set(value + addition);
    }

    public CounterComponent set(int newValue) {
        return new CounterComponent(min, max, Math.max(Math.min(max, newValue), min));
    }

    public CounterComponent setMin() {
        return new CounterComponent(min, max, min);
    }

    public CounterComponent setMax() {
        return new CounterComponent(min, max, max);
    }
}
