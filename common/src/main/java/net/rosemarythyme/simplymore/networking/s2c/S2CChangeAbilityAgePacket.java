package net.rosemarythyme.simplymore.networking.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.AbstractAbilityPlacementEntity;

public record S2CChangeAbilityAgePacket(int id, int age) implements CustomPayload {
    public static final Identifier PACKET_ID = SimplyMore.identifier("change_age");
    public static final Id<S2CChangeAbilityAgePacket> PAYLOAD_ID = new Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, S2CChangeAbilityAgePacket> CODEC = PacketCodec.tuple(
        PacketCodecs.INTEGER, S2CChangeAbilityAgePacket::id,
        PacketCodecs.INTEGER, S2CChangeAbilityAgePacket::age,
        S2CChangeAbilityAgePacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }

    public static void handle(S2CChangeAbilityAgePacket packet, NetworkManager.PacketContext context) {
        Entity entity = context.getPlayer().getWorld().getEntityById(packet.id);
        if(entity instanceof AbstractAbilityPlacementEntity ability) {
            ability.setAge(packet.age, false);
        }
    }
}
