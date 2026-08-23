package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

public record ReformTypeComponent(Identifier block) {
    public static final Codec<ReformTypeComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Identifier.CODEC.fieldOf("block").forGetter(ReformTypeComponent::block))
            .apply(instance, ReformTypeComponent::new));

    public static final PacketCodec<RegistryByteBuf, ReformTypeComponent> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, ReformTypeComponent::block,
            ReformTypeComponent::new
    );

    public static final ReformTypeComponent DEFAULT = new ReformTypeComponent(Identifier.ofVanilla("air"));
}
