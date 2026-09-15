package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ActiveHitsComponent(int value, long time) {
    public static final Codec<ActiveHitsComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("value").forGetter(ActiveHitsComponent::value),
            Codec.LONG.fieldOf("time").forGetter(ActiveHitsComponent::time))
            .apply(instance, ActiveHitsComponent::new));

    public static final PacketCodec<RegistryByteBuf, ActiveHitsComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, ActiveHitsComponent::value,
            PacketCodecs.VAR_LONG, ActiveHitsComponent::time,
            ActiveHitsComponent::new
    );

    public ActiveHitsComponent increment(int addition, int maxDuration, long time) {
        int value = time - this.time <= maxDuration ? this.value + addition : addition;
        return new ActiveHitsComponent(value, time);
    }
}
