package net.rosemarythyme.simplymore.networking.s2c;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.util.MathUtils;

public record S2CParticleCylinderPacket(ParticleEffect particle, boolean longDistance, double x, double y, double z, float offsetXZ, float offsetY, float speed, int count) implements CustomPayload {
    public static final Identifier PACKET_ID = SimplyMore.identifier("particle_cylinder");
    public static final Id<S2CParticleCylinderPacket> PAYLOAD_ID = new Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, S2CParticleCylinderPacket> CODEC = PacketCodec.of(
            (value, buf) -> {
                ParticleTypes.PACKET_CODEC.encode(buf, value.particle());
                PacketCodecs.BOOL.encode(buf, value.longDistance());
                PacketCodecs.DOUBLE.encode(buf, value.x());
                PacketCodecs.DOUBLE.encode(buf, value.y());
                PacketCodecs.DOUBLE.encode(buf, value.z());
                PacketCodecs.FLOAT.encode(buf, value.offsetXZ());
                PacketCodecs.FLOAT.encode(buf, value.offsetY());
                PacketCodecs.FLOAT.encode(buf, value.speed());
                PacketCodecs.INTEGER.encode(buf, value.count());
            },
            buf -> new S2CParticleCylinderPacket(
                    ParticleTypes.PACKET_CODEC.decode(buf),
                    PacketCodecs.BOOL.decode(buf),
                    PacketCodecs.DOUBLE.decode(buf),
                    PacketCodecs.DOUBLE.decode(buf),
                    PacketCodecs.DOUBLE.decode(buf),
                    PacketCodecs.FLOAT.decode(buf),
                    PacketCodecs.FLOAT.decode(buf),
                    PacketCodecs.FLOAT.decode(buf),
                    PacketCodecs.INTEGER.decode(buf)
            )
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }

    public static void handle(S2CParticleCylinderPacket packet, NetworkManager.PacketContext context) {
        Random random = context.getPlayer().getRandom();

        for(int i = 0; i < packet.count(); i++) {
            Vec3d offset = MathUtils.getDirectionalVector(random.nextFloat() * 360, 0);

            Vec3d dxz = offset.multiply((random.nextFloat() * 2 * packet.offsetXZ()) - packet.offsetXZ());
            double dy = (random.nextFloat() * 2 * packet.offsetY()) - packet.offsetY();

            Vec3d velocity = MathUtils.getDirectionalVector(random.nextFloat() * 360, random.nextFloat() * 90).multiply(packet.speed);

            context.getPlayer().getWorld().addParticle(packet.particle, packet.x() + dxz.getX(), packet.y() + dy, packet.z() + dxz.getZ(), velocity.getX(), velocity.getY(), velocity.getZ());
        }
    }
}
