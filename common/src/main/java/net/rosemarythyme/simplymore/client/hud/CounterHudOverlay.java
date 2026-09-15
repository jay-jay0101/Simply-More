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
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.util.MathUtils;

public class CounterHudOverlay implements HudOverlay<Pair<Integer, Integer>> {
    private final String translationKey;
    private final int borderColor;
    private final int fillColor;
    private final int textColor;

    public CounterHudOverlay(String translationKey, int borderColor, int fillColor, int textColor) {
        this.translationKey = translationKey;
        this.borderColor = borderColor;
        this.fillColor = fillColor;
        this.textColor = textColor;
    }

    @Override
    public void renderHudOverlay(DrawContext context, ItemStack stack, ClientPlayerEntity player, RenderTickCounter tickCounter) {
        Pair<Integer, Integer> data = HudUtils.getCache(this, stack, player);

        MatrixStack matrices = context.getMatrices();
        matrices.push();

        HudUtils.renderSquareProgress(context, data.getRight(), data.getLeft(), borderColor, fillColor);

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        Text text = Text.translatable(translationKey, data.getLeft(), data.getRight());

        int width = renderer.getWidth(text);
        context.drawText(renderer, text, -width/2, -15, textColor, true);

        matrices.pop();
    }

    @Override
    public Pair<Integer, Integer> getHudData(ItemStack stack, ClientPlayerEntity player) {
        CounterComponent component = MathUtils.getCounterComponent(stack);
        return new Pair<>(component.value() - component.min(), component.max() - component.min());
    }
}
