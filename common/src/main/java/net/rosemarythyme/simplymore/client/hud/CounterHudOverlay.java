package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.rosemarythyme.simplymore.client.util.HudUtils;

public class CounterHudOverlay extends AbstractCounterHudOverlay<Pair<Integer, Integer>> {
    public CounterHudOverlay(String translationKey, int borderColor, int fillColor, int textColor) {
        super(translationKey, borderColor, fillColor, textColor);
    }

    @Override
    public void renderHudOverlay(DrawContext context, ItemStack stack, ClientPlayerEntity player, RenderTickCounter tickCounter) {
        Pair<Integer, Integer> data = HudUtils.getCache(this, stack, player);
        renderOverlay(context, data);
    }

    @Override
    public Pair<Integer, Integer> getHudData(ItemStack stack, ClientPlayerEntity player) {
        return getData(stack);
    }
}
