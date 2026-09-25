package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.World;

public record DayTimeComponent(DayTimeForm form) {
    public static final Codec<DayTimeComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.INT.fieldOf("isDay").forGetter((component) -> component.form().ordinal()))
            .apply(instance, DayTimeComponent::new));

    public static final PacketCodec<RegistryByteBuf, DayTimeComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, (component) -> component.form().ordinal(),
            DayTimeComponent::new
    );

    public DayTimeComponent(int ordinal) {
        this(DayTimeForm.getByOrdinal(ordinal));
    }

    public enum DayTimeForm {
        DAY,
        NIGHT,
        TIMELESS;

        public static DayTimeForm getByOrdinal(int ordinal) {
            DayTimeForm[] values = DayTimeForm.values();
            if(ordinal < values.length) {
                return values[ordinal];
            }

            return TIMELESS;
        }

        public static DayTimeForm getFromWorld(World world) {
            if(world.getDimension().hasFixedTime()) return TIMELESS;

            long dayTime = Math.abs(world.getTimeOfDay() % 24000);
            return dayTime < 13000 ? DAY : NIGHT;
        }
    }
}
