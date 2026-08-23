package net.rosemarythyme.simplymore.networking.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.client.camera.ScreenshakeManager;

public record S2CScreenShakePacket(float intensity, int duration, boolean fromSelf) implements CustomPayload {
    public static final Identifier PACKET_ID = SimplyMore.identifier("screenshake");
    public static final CustomPayload.Id<S2CScreenShakePacket> PAYLOAD_ID = new Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, S2CScreenShakePacket> CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, S2CScreenShakePacket::intensity,
            PacketCodecs.INTEGER, S2CScreenShakePacket::duration,
            PacketCodecs.BOOL, S2CScreenShakePacket::fromSelf,
            S2CScreenShakePacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }

    public static void handle(S2CScreenShakePacket packet, NetworkManager.PacketContext context) {
        ScreenshakeManager.add(packet.intensity, packet.duration, packet.fromSelf);
    }
}
