package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.rosemarythyme.simplymore.util.MathUtils;

public record CogRotationComponent(float rot, long time, float extraSpeedMult) {
    public static final Codec<CogRotationComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.FLOAT.fieldOf("rot").forGetter(CogRotationComponent::rot),
            Codec.LONG.fieldOf("time").forGetter(CogRotationComponent::time),
            Codec.FLOAT.fieldOf("extra_speed_mult").forGetter(CogRotationComponent::extraSpeedMult))
            .apply(instance, CogRotationComponent::new));

    public static final PacketCodec<RegistryByteBuf, CogRotationComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, CogRotationComponent::rot,
            PacketCodecs.VAR_LONG, CogRotationComponent::time,
            PacketCodecs.FLOAT, CogRotationComponent::extraSpeedMult,
            CogRotationComponent::new
    );

    public static final CogRotationComponent DEFAULT = new CogRotationComponent(0, 0, 0);

    public CogRotationComponent update(long time, float oxidation, boolean isUsingItem) {
        return update(time, oxidation, 1, isUsingItem);
    }

    public CogRotationComponent update(long time, float oxidation, float extraSpeedMult, boolean isUsingItem) {
        float newRot = getRotation(time, oxidation, isUsingItem);
        return new CogRotationComponent(newRot, time, extraSpeedMult);
    }

    public float getRotation(double timeDelta, float oxidation, boolean isUsingItem) {
        double localTimeDelta = timeDelta - this.time;

        float rotSpeed = getRotationSpeed(oxidation, isUsingItem);
        return (float)((localTimeDelta * rotSpeed * 120f / 20f) + this.rot) % 360f;
    }

    public float getRotationSpeed(float oxidation, boolean isUsingItem) {
        return MathUtils.clampedLerp(oxidation, 0, 1, isUsingItem ? extraSpeedMult : 1f, 0f);
    }
}
