package net.rosemarythyme.simplymore.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.Vector2f;
import net.rosemarythyme.simplymore.client.camera.ScreenshakeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class ClientCameraMixin {

    @Unique
    private static Vector2f offset = new Vector2f(0, 0);

    @Inject(method = "setRotation", at=@At("HEAD"))
    public void simplmore$calculateRotation(float yaw, float pitch, CallbackInfo ci) {
        offset = ScreenshakeManager.calculateRotation();
    };

    @ModifyVariable(method = "setRotation", at=@At("HEAD"), ordinal = 0, argsOnly = true)
    public float simplymore$modifyYaw(float yaw) {
        return yaw + offset.getX();
    }

    @ModifyVariable(method = "setRotation", at=@At("HEAD"), ordinal = 1, argsOnly = true)
    public float simplymore$modifyPitch(float pitch) {
        return pitch + offset.getY();
    }
}
