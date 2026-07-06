package net.rosemarythyme.simplymore.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record DayTimeComponent(boolean isDay, boolean isTimeless) {
    public static final Codec<DayTimeComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                    Codec.BOOL.fieldOf("isDay").forGetter(net.rosemarythyme.simplymore.item.components.DayTimeComponent::isDay),
                    Codec.BOOL.fieldOf("isTimeless").forGetter(net.rosemarythyme.simplymore.item.components.DayTimeComponent::isTimeless))
            .apply(instance, DayTimeComponent::new));

    public static final PacketCodec<RegistryByteBuf, DayTimeComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, net.rosemarythyme.simplymore.item.components.DayTimeComponent::isDay,
            PacketCodecs.BOOL, net.rosemarythyme.simplymore.item.components.DayTimeComponent::isTimeless,
            DayTimeComponent::new
    );
    
    public static DayTimeComponent of(DayTimeForm form) {
        return switch (form) {
            case DAY -> new DayTimeComponent(true, false);
            case NIGHT -> new DayTimeComponent(false, false);
            case TIMELESS -> new DayTimeComponent(false, true);
        };
    }

    public DayTimeForm getForm() {
        if(this.isTimeless) {
            return DayTimeForm.TIMELESS;
        }

        return this.isDay ? DayTimeForm.DAY : DayTimeForm.NIGHT;
    }
    
    public enum DayTimeForm {
        DAY,
        NIGHT,
        TIMELESS
    }
}
