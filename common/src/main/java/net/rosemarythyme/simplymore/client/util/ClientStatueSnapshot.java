package net.rosemarythyme.simplymore.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.stat.StatHandler;

@Environment(EnvType.CLIENT)
public class ClientStatueSnapshot {
    public static boolean isPlayer(Class<? extends LivingEntity> clazz) {
        return (ClientPlayerEntity.class.isAssignableFrom(clazz));
    }

    public static ClientPlayerEntity snapshotPlayer(LivingEntity player) throws ReflectiveOperationException {
        return ClientPlayerEntity.class.getDeclaredConstructor(MinecraftClient.class, ClientWorld.class, ClientPlayNetworkHandler.class, StatHandler.class, ClientRecipeBook.class, boolean.class, boolean.class).newInstance(MinecraftClient.getInstance(), ((ClientPlayerEntity) player).clientWorld, MinecraftClient.getInstance().getNetworkHandler(), null, null, false, false);
    }
}
