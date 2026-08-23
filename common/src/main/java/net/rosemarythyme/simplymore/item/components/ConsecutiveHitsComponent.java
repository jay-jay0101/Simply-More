package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ConsecutiveHitsComponent(int num, boolean swungThisFrame, boolean hitThisFrame) {
    public static final Codec<ConsecutiveHitsComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.INT.fieldOf("num").forGetter(ConsecutiveHitsComponent::num),
                    Codec.BOOL.fieldOf("swungThisFrame").forGetter(ConsecutiveHitsComponent::swungThisFrame),
                    Codec.BOOL.fieldOf("hitThisFrame").forGetter(ConsecutiveHitsComponent::hitThisFrame))
            .apply(instance, ConsecutiveHitsComponent::new));

    public static final PacketCodec<RegistryByteBuf, ConsecutiveHitsComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, ConsecutiveHitsComponent::num,
            PacketCodecs.BOOL, ConsecutiveHitsComponent::swungThisFrame,
            PacketCodecs.BOOL, ConsecutiveHitsComponent::hitThisFrame,
            ConsecutiveHitsComponent::new
    );

    public static final ConsecutiveHitsComponent DEFAULT = new ConsecutiveHitsComponent(0, false, false);
}
