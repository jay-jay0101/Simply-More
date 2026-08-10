package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ConsecutiveHitsComponent(int num, long lastHitTime, long lastSwingTime) {
    public static final Codec<ConsecutiveHitsComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.INT.fieldOf("num").forGetter(ConsecutiveHitsComponent::num),
                    Codec.LONG.fieldOf("lastHitTime").forGetter(ConsecutiveHitsComponent::lastHitTime),
                    Codec.LONG.fieldOf("lastSwingTime").forGetter(ConsecutiveHitsComponent::lastSwingTime))
            .apply(instance, ConsecutiveHitsComponent::new));

    public static final PacketCodec<RegistryByteBuf, ConsecutiveHitsComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, ConsecutiveHitsComponent::num,
            PacketCodecs.VAR_LONG, ConsecutiveHitsComponent::lastHitTime,
            PacketCodecs.VAR_LONG, ConsecutiveHitsComponent::lastSwingTime,
            ConsecutiveHitsComponent::new
    );

    public static final ConsecutiveHitsComponent DEFAULT = new ConsecutiveHitsComponent(0, 0, 0);
}
