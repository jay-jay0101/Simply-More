package net.rosemarythyme.simplymore.networking.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;

public record S2CAbilityManagerPacket(int ownerID, int duration, int currentDuration, int typeOrdinal) implements CustomPayload {
    public static final Identifier PACKET_ID = SimplyMore.identifier("ability");
    public static final Id<S2CAbilityManagerPacket> PAYLOAD_ID = new Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, S2CAbilityManagerPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, S2CAbilityManagerPacket::ownerID,
            PacketCodecs.INTEGER, S2CAbilityManagerPacket::duration,
            PacketCodecs.INTEGER, S2CAbilityManagerPacket::currentDuration,
            PacketCodecs.INTEGER, S2CAbilityManagerPacket::typeOrdinal,
            S2CAbilityManagerPacket::new
    );

    public S2CAbilityManagerPacket(LivingEntity owner, int duration, int currentDuration, ActiveAbilityManager.Type type) {
        this(owner.getId(), duration, currentDuration, type.ordinal());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }

    public static void handle(S2CAbilityManagerPacket packet, NetworkManager.PacketContext context) {
        ClientWorld world = MinecraftClient.getInstance().world;
        LivingEntity owner = world == null ? null : (LivingEntity) world.getEntityById(packet.ownerID);
        ClientActiveAbilityManager.CLIENT.add(owner, ActiveAbilityManager.Type.values()[packet.typeOrdinal()], packet.duration(), packet.currentDuration());
    }
}
