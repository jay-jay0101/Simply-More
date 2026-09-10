package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record GrabbedComponent(UUID entityId, long time) {
    public static final Codec<GrabbedComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Uuids.CODEC.fieldOf("entityId").forGetter(GrabbedComponent::entityId),
                    Codec.LONG.fieldOf("time").forGetter(GrabbedComponent::time))
            .apply(instance, GrabbedComponent::new));

    public static final PacketCodec<RegistryByteBuf, GrabbedComponent> PACKET_CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC, GrabbedComponent::entityId,
            PacketCodecs.VAR_LONG, GrabbedComponent::time,
            GrabbedComponent::new
    );
}
