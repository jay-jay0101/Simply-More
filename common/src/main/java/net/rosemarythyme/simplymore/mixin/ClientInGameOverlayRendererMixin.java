package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Pair;
import net.rosemarythyme.simplymore.client.util.RenderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(InGameOverlayRenderer.class)
public class ClientInGameOverlayRendererMixin {
    @ModifyVariable(method = "renderFireOverlay", at = @At(value = "STORE"), ordinal = 0)
    private static Sprite simplymore$modifyFire(Sprite original, @Local(argsOnly = true) MinecraftClient client) {
        if(client.player == null) return original;

        Optional<Pair<SpriteIdentifier, SpriteIdentifier>> override = RenderUtils.getFireOverride(client.player);
        return override.isEmpty() ? original : override.get().getRight().getSprite();
    }
}
