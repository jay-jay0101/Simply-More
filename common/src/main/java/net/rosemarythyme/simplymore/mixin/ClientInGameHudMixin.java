package net.rosemarythyme.simplymore.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.config.Config;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ClientInGameHudMixin {
    @Unique private static final int WEAPON_HUD_BOTTOM_OFFSET = 68;
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    private void simplymore$hudOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ClientPlayerEntity player = client.player;
        if(player == null) return;

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        if(simplymore$isStackInvalid(stack)) {
            stack = player.getStackInHand(Hand.OFF_HAND);

            if(simplymore$isStackInvalid(stack)) return;
            if(stack.getItem() instanceof TwoHandedWeapon) return;
        }

        if(stack.getItem() instanceof HudOverlayItem<?> hudOverlayItem) {
            MatrixStack matrices = context.getMatrices();

            matrices.push();
            matrices.translate(
                    context.getScaledWindowWidth() / 2f + Config.gui.xOffset,
                    context.getScaledWindowHeight() - WEAPON_HUD_BOTTOM_OFFSET + Config.gui.yOffset,
                    0.0F
            );

            float scale = Math.clamp(Config.gui.scale, 0.25F, 4.0F);
            matrices.scale(scale, scale, scale);

            hudOverlayItem.renderHudOverlay(context, stack, player, client.getRenderTickCounter());
            matrices.pop();
        }
    }

    @Unique
    private static boolean simplymore$isStackInvalid(ItemStack stack) {
        return stack.isEmpty() || !(stack.getItem() instanceof HudOverlayItem<?>) || (AwakeningApi.isAwakeningSystemEnabled() && !AwakeningApi.isAbilityUnlocked(stack));
    }
}
