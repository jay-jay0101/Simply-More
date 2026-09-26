package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record RotationComponent(float rot, long time, float speed) {
    public static final Codec<RotationComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.FLOAT.fieldOf("rot").forGetter(RotationComponent::rot),
            Codec.LONG.fieldOf("time").forGetter(RotationComponent::time),
            Codec.FLOAT.fieldOf("speed").forGetter(RotationComponent::speed))
            .apply(instance, RotationComponent::new));

    public static final PacketCodec<RegistryByteBuf, RotationComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, RotationComponent::rot,
            PacketCodecs.VAR_LONG, RotationComponent::time,
            PacketCodecs.FLOAT, RotationComponent::speed,
            RotationComponent::new
    );

    public static final RotationComponent DEFAULT = new RotationComponent(0, 0, 0);

    public RotationComponent update(long time, float newSpeed) {
        float newRot = getRotation(time);
        return new RotationComponent(newRot, time, newSpeed);
    }

    public float getRotation(double timeDelta) {
        double localTimeDelta = timeDelta - this.time;

        return (float)((localTimeDelta * speed) + this.rot) % 360f;
    }
}
