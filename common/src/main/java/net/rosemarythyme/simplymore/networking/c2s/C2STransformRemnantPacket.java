package net.rosemarythyme.simplymore.networking.c2s;

import dev.architectury.networking.NetworkManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.ReformingRemnantItem;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.registry.SoundRegistry;

public record C2STransformRemnantPacket(Identifier item, boolean isMainHand) implements CustomPayload {
    public static final Identifier PACKET_ID = SimplyMore.identifier("transform");
    public static final Id<C2STransformRemnantPacket> PAYLOAD_ID = new Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, C2STransformRemnantPacket> CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, C2STransformRemnantPacket::item,
            PacketCodecs.BOOL, C2STransformRemnantPacket::isMainHand,
            C2STransformRemnantPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }

    public static void handle(C2STransformRemnantPacket packet, NetworkManager.PacketContext context) {
        ItemStack stack = context.getPlayer().getStackInHand(packet.isMainHand ? Hand.MAIN_HAND : Hand.OFF_HAND);
        if(ReformingRemnantItem.getUniques(stack).contains(packet.item)) {
            Item item = Registries.ITEM.get(packet.item);
            stack.decrement(1);

            AudioVisualUtils.particleRing((ServerWorld) context.getPlayer().getWorld(), context.getPlayer().getPos(), ParticleTypes.CAMPFIRE_COSY_SMOKE, 1f, 6);
            AudioVisualUtils.playSound(context.getPlayer().getWorld(), context.getPlayer().getPos(), new Sound(SoundRegistry.DARK_ACTIVATION_DISTORTED.get(), 0.4f, 1.8f));

            context.getPlayer().dropItem(AwakeningApi.initializeNaturalDrop(new ItemStack(item)), false);
        }
    }
}
