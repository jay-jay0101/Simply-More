package net.rosemarythyme.simplymore.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.Vector2f;
import net.rosemarythyme.simplymore.client.camera.ScreenshakeManager;
import net.rosemarythyme.simplymore.item.uniques.MoundshifterItem;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public class ClientCameraMixin {

    @Unique
    private static Vector2f simplymore$offset = new Vector2f(0, 0);

    @Inject(method = "setRotation", at=@At("HEAD"))
    public void simplmore$calculateRotation(float yaw, float pitch, CallbackInfo ci) {
        simplymore$offset = ScreenshakeManager.calculateRotation();
    }

    @ModifyArgs(method = "update", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setPos(DDD)V"))
    public void simplmore$translate(Args args) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) return;

        double currentDuration = ClientActiveAbilityManager.CLIENT.getCurrentDuration(player, ActiveAbilityManager.Type.DRILL);
        if(currentDuration > 0) {
            double duration = MoundshifterItem.SETTINGS.maxDrillTime;
            double ticksUsed = duration - currentDuration;
            args.set(1, (double) args.get(1) - MathUtils.clampedLerp((float) ticksUsed, 0, 10, 0, player.getHeight() / 2));
        }
    }

    @ModifyVariable(method = "setRotation", at=@At("HEAD"), ordinal = 0, argsOnly = true)
    public float simplymore$modifyYaw(float yaw) {
        return yaw + simplymore$offset.getX();
    }

    @ModifyVariable(method = "setRotation", at=@At("HEAD"), ordinal = 1, argsOnly = true)
    public float simplymore$modifyPitch(float pitch) {
        return pitch + simplymore$offset.getY();
    }
}
