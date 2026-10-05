package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.rosemarythyme.simplymore.client.util.HudUtils;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.util.ItemStackUtils;

public abstract class AbstractCounterHudOverlay<T> implements HudOverlay<T> {
    protected final String translationKey;
    protected final int borderColor;
    protected final int fillColor;
    protected final int textColor;

    public AbstractCounterHudOverlay(String translationKey, int borderColor, int fillColor, int textColor) {
        this.translationKey = translationKey;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.textColor = textColor;
    }

    public void renderOverlay(DrawContext context, Pair<Integer, Integer> data) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();

        HudUtils.renderSquareProgress(context, data.getRight(), data.getLeft(), borderColor, fillColor);

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        Text text = Text.translatable(translationKey, data.getLeft(), data.getRight());

        int width = renderer.getWidth(text);
        context.drawText(renderer, text, -width/2, -15, textColor, true);

        matrices.pop();
    }

    public Pair<Integer, Integer> getData(ItemStack stack) {
        CounterComponent component = ItemStackUtils.getCounterComponent(stack);
        return new Pair<>(component.value(), component.max());
    }
}
