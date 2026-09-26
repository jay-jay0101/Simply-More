package net.rosemarythyme.simplymore.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class ClientGameRendererMixin {
    @Inject(method = "renderHand", at=@At("HEAD"), cancellable = true)
    public void simplmore$thirdPerson(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo ci) {
        if(RenderUtils.shouldForceThirdPerson()) {
            ci.cancel();
        }
    }
}
