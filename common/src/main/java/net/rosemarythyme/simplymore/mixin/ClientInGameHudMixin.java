package net.rosemarythyme.simplymore.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.config.ClientConfig;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.item.uniques.LustrousMoxieItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
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
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    private void simplymore$hudOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        ClientPlayerEntity player = client.player;
        if(player == null) return;

        simplymore$dazzled(context, player);
        simplymore$renderItemHudRenders(context, tickCounter, player);
    }

    @Unique
    private static void simplymore$renderItemHudRenders(DrawContext context, RenderTickCounter counter, ClientPlayerEntity player) {
        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
        if(simplymore$isStackInvalid(stack)) {
            stack = player.getStackInHand(Hand.OFF_HAND);

            if(simplymore$isStackInvalid(stack)) return;
            if(stack.getItem() instanceof TwoHandedWeapon) return;
        }

        if(stack.getItem() instanceof HudOverlayItem<?> hudOverlayItem) {
            SimplySwordsClientAPI.pushWeaponHudTransform(context);
            hudOverlayItem.renderHudOverlay(context, stack, player, counter);
            context.getMatrices().pop();
        }
    }

    @Unique
    private static boolean simplymore$isStackInvalid(ItemStack stack) {
        return stack.isEmpty() || !(stack.getItem() instanceof HudOverlayItem<?>) || (AwakeningApi.isAwakeningSystemEnabled() && !AwakeningApi.isAbilityUnlocked(stack));
    }

    @Unique
    private static void simplymore$dazzled(DrawContext context, ClientPlayerEntity player) {
        StatusEffectInstance dazzled = player.getStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.DAZZLED));
        if(dazzled == null) return;

        int duration = dazzled.getDuration();
        int used = LustrousMoxieItem.SETTINGS.dazzleDuration - duration;

        float alpha = 0xFF;
        if(used <= 10) {
            alpha = MathUtils.clampedLerp(used, 0, 10, 0x00, 0xFF);
        } else if (duration <= LustrousMoxieItem.SETTINGS.dazzleDuration - 40) {
            alpha = MathUtils.clampedLerp(duration, 0, LustrousMoxieItem.SETTINGS.dazzleDuration - 40, 0x00, 0xFF);
        }

        int color = ConfigWrapper.CLIENT.flashbang.get() == ClientConfig.Flashbang.LIGHT ? 0xFFFFFF : 0x000000;
        context.fill(0, 0, context.getScaledWindowWidth(), context.getScaledWindowHeight(), ((int) alpha << 24) | color);
    }
}
