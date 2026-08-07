package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

public interface HudOverlayItem<T> {
    void renderHudOverlay(DrawContext context, ItemStack stack, ClientPlayerEntity player);
    T getHudData(ItemStack stack, ClientPlayerEntity player);
}
