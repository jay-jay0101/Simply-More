package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.rosemarythyme.simplymore.client.world.PlayerLunacyManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderer.class)
public class ClientEntityRendererMixin {

    @WrapMethod(method = "shouldRender")
    private boolean simplymore$shouldrender(Entity entity, Frustum frustum, double x, double y, double z, Operation<Boolean> original) {
        if(entity instanceof LivingEntity livingEntity) {
            PlayerLunacyManager.PlayerLunacyData<?> data = PlayerLunacyManager.get(livingEntity);
            if(data != null && (data.type() == PlayerLunacyManager.Type.OFFSET || data.type() == PlayerLunacyManager.Type.DUPLICATED)) return entity.shouldRender(x, y, z);
        }

        return original.call(entity, frustum, x, y, z);
    }
}
