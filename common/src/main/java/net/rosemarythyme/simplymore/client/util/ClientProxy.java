package net.rosemarythyme.simplymore.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class ClientProxy {
    public static boolean ClientStatueSnapshot$isPlayer(Class<? extends LivingEntity> clazz) {
        return ClientStatueSnapshot.isPlayer(clazz);
    }

    public static LivingEntity ClientStatueSnapshot$snapshotPlayer(LivingEntity player) throws ReflectiveOperationException {
        return ClientStatueSnapshot.snapshotPlayer(player);
    }
}
