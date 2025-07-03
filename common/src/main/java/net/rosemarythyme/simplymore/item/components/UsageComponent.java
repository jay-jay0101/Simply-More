package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record UsageComponent(boolean using, boolean offhand, boolean offhandPrevious) {
    public static final Codec<UsageComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.BOOL.fieldOf("using").forGetter(UsageComponent::using),
            Codec.BOOL.fieldOf("offhand").forGetter(UsageComponent::offhand),
            Codec.BOOL.fieldOf("offhandPrevious").forGetter(UsageComponent::offhandPrevious))
            .apply(instance, UsageComponent::new));

    public static final PacketCodec<RegistryByteBuf, UsageComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, UsageComponent::using,
            PacketCodecs.BOOL, UsageComponent::offhand,
            PacketCodecs.BOOL, UsageComponent::offhandPrevious,
            UsageComponent::new
    );
}
