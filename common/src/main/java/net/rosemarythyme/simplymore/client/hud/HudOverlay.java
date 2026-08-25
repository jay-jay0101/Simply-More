package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;

public interface HudOverlay<T> {
    void renderHudOverlay(DrawContext context, ItemStack stack, ClientPlayerEntity player, RenderTickCounter tickCounter);
    T getHudData(ItemStack stack, ClientPlayerEntity player);
}
