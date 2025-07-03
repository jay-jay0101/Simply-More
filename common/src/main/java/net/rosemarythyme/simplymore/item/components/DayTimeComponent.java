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
    
    public static DayTimeComponent of(DayForm form) {
        return switch (form) {
            case DAY -> new DayTimeComponent(true, false);
            case NIGHT -> new DayTimeComponent(false, false);
            case TIMELESS -> new DayTimeComponent(false, true);
        };
    }

    public DayForm getForm() {
        if(this.isTimeless) {
            return DayForm.TIMELESS;
        } else {
            if (this.isDay) {
                return DayForm.DAY;
            } else {
                return DayForm.NIGHT;
            }
        }
    }
    
    public enum DayForm {
        DAY,
        NIGHT,
        TIMELESS
    }
}
