package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.rosemarythyme.simplymore.client.util.HudUtils;
import net.rosemarythyme.simplymore.util.MathUtils;

public class CounterBarHudOverlay implements HudOverlay<Pair<Float, Long>> {
    protected final String translationKey;
    protected final int borderColor;
    protected final int backgroundColor;
    protected final int fillColor;
    protected final int textColor;

    public CounterBarHudOverlay(String translationKey, int borderColor, int backgroundColor, int fillColor, int textColor) {
        this.translationKey = translationKey;
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        this.fillColor = fillColor;
        this.textColor = textColor;
    }

    @Override
    public void renderHudOverlay(DrawContext context, ItemStack stack, ClientPlayerEntity player, RenderTickCounter tickCounter) {
        Pair<Float, Long> data = HudUtils.getCache(this, stack, player);
        Pair<Float, Long> prevData = HudUtils.getPreviousCache(this);
        if(prevData == null) prevData = data;

        long time = data.getRight();

        MatrixStack matrices = context.getMatrices();
        matrices.push();

        float progress = MathUtils.clampedLerp(player.getWorld().getTime() + tickCounter.getTickDelta(false), time, time + 10, prevData.getLeft(), data.getLeft());
        HudUtils.renderProgressBar(context, progress, getBorderColor(data.getLeft()), getBackgroundColor(data.getLeft()), getFillColor(data.getLeft()));

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

        Text text = Text.translatable(translationKey, MathUtils.toPercentage(progress));
        int width = renderer.getWidth(text);
        context.drawText(renderer, text, -width/2, -15, getTextColor(data.getLeft()), true);
        matrices.pop();
    }

    protected int getBorderColor(float data) {
        return borderColor;
    }

    protected int getBackgroundColor(float data) {
        return backgroundColor;
    }

    protected int getFillColor(float data) {
        return fillColor;
    }

    protected int getTextColor(float data) {
        return textColor;
    }

    @Override
    public Pair<Float, Long> getHudData(ItemStack stack, ClientPlayerEntity player) {
        return new Pair<>(MathUtils.getCounterComponentProgress(stack), player.getWorld().getTime());
    }
}
