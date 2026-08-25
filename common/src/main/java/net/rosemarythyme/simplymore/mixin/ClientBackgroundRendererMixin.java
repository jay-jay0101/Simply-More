package net.rosemarythyme.simplymore.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.util.math.MathHelper;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.rosemarythyme.simplymore.world.ClientActiveAbilityManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BackgroundRenderer.class)
public class ClientBackgroundRendererMixin {
    @Shadow private static float red;
    @Shadow private static float green;
    @Shadow private static float blue;

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V"))
    private static void simplymore$tintFog(Args args) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if (strength > 0f) {
            args.set(0, MathHelper.lerp(strength, args.get(0), 0.545f));
            args.set(1, MathHelper.lerp(strength, args.get(1), 0.1f));
            args.set(2, MathHelper.lerp(strength, args.get(2), 0.1f));
        }
    }

    @Inject(method = "applyFogColor", at = @At("HEAD"), cancellable = true)
    private static void simplymore$fogTint(CallbackInfo ci) {
        float strength = ClientActiveAbilityManager.CLIENT.getAbilityStrength(ActiveAbilityManager.Type.HARVEST);
        if (strength > 0f) {
            RenderSystem.setShaderFogColor(
                    MathHelper.lerp(strength, red, 0.545f),
                    MathHelper.lerp(strength, green, 0.1f),
                    MathHelper.lerp(strength, blue, 0.1f)
            );
            ci.cancel();
        }
    }
}
