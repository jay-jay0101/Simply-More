package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.rosemarythyme.simplymore.client.util.HudUtils;
import net.rosemarythyme.simplymore.item.uniques.DeathsEyrieItem;

public class DeathsEyrieHud extends AbstractCounterHudOverlay<DeathsEyrieHud.DeathsEyrieHudData> {
    public record DeathsEyrieHudData(int maxCrows, int crows, int chargedCrows) {}

    protected final int chargeColor;

    public DeathsEyrieHud(String translationKey, int borderColor, int fillColor, int chargeColor, int textColor) {
        super(translationKey, borderColor, fillColor, textColor);
        this.chargeColor = chargeColor;
    }

    @Override
    public void renderHudOverlay(DrawContext context, ItemStack stack, ClientPlayerEntity player, RenderTickCounter tickCounter) {
        DeathsEyrieHudData data = HudUtils.getCache(this, stack, player);
        renderOverlay(context, new Pair<>(data.crows, data.maxCrows));
        HudUtils.renderUnderSquares(context, data.maxCrows, data.chargedCrows, chargeColor);
    }

    @Override
    public DeathsEyrieHudData getHudData(ItemStack stack, ClientPlayerEntity player) {
        Pair<Integer, Integer> data = getData(stack);

        int chargedCrows = 0;
        if(player.isUsingItem()) {
            chargedCrows = Math.min(data.getLeft(), player.getItemUseTime() / DeathsEyrieItem.CROW_CHARGE_TIME);
        }

        return new DeathsEyrieHudData(data.getRight(), data.getLeft(), chargedCrows);
    }
}
