package net.rosemarythyme.simplymore.registry;

import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.rosemarythyme.simplymore.networking.s2c.S2CParticleCylinderPacket;
import net.rosemarythyme.simplymore.networking.s2c.S2CScreenShakePacket;

public class PacketRegistry {
    @Environment(EnvType.SERVER)
    public static void registerS2C() {
        NetworkManager.registerS2CPayloadType(S2CScreenShakePacket.PAYLOAD_ID, S2CScreenShakePacket.CODEC);
        NetworkManager.registerS2CPayloadType(S2CParticleCylinderPacket.PAYLOAD_ID, S2CParticleCylinderPacket.CODEC);
    }

    @Environment(EnvType.CLIENT)
    public static void registerS2CRecievers() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, S2CScreenShakePacket.PAYLOAD_ID, S2CScreenShakePacket.CODEC, S2CScreenShakePacket::handle);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, S2CParticleCylinderPacket.PAYLOAD_ID, S2CParticleCylinderPacket.CODEC, S2CParticleCylinderPacket::handle);
    }
}
