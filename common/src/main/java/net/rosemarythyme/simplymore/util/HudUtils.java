package net.rosemarythyme.simplymore.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;

import java.util.HashMap;
import java.util.Map;

public class HudUtils {
    private static final Map<HudOverlayItem<?>, Object> CACHE = new HashMap<>();
    private static final Map<HudOverlayItem<?>, Object> LAST_CACHE = new HashMap<>();

    public static void clearCache() {
        LAST_CACHE.clear();
        LAST_CACHE.putAll(CACHE);

        CACHE.clear();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCache(HudOverlayItem<T> item, ItemStack stack, ClientPlayerEntity player) {
        return (T) CACHE.computeIfAbsent(item, data -> item.getHudData(stack, player));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPreviousCache(HudOverlayItem<T> item) {
        return (T) LAST_CACHE.getOrDefault(item, null);
    }


    public static final int SQUARE_BORDER_SIZE = 4;
    public static final int SQUARE_FILLED_SIZE = SQUARE_BORDER_SIZE - 1;
    public static void renderSquareProgress(DrawContext context, int totalSquares, int squaresCompleted, int borderColor, int filledColor) {
        int dx = (totalSquares - 1) * SQUARE_BORDER_SIZE;
        for(int i = 0; i < totalSquares; i++) {
            int x = i * 2 * SQUARE_BORDER_SIZE - dx;
            context.drawBorder(x - SQUARE_BORDER_SIZE, -SQUARE_BORDER_SIZE, SQUARE_BORDER_SIZE * 2, SQUARE_BORDER_SIZE * 2, borderColor);

            if(squaresCompleted-- > 0) {
                context.fill(x - SQUARE_FILLED_SIZE, -SQUARE_FILLED_SIZE, x + SQUARE_FILLED_SIZE, SQUARE_FILLED_SIZE, filledColor);
            }
        }
    }

    public static final int PROGRESS_BAR_WIDTH = 100;
    public static final int PROGRESS_BAR_HEIGHT = 10;
    public static void renderProgressBar(DrawContext context, float progress, int borderColor, int backgroundColor, int filledColor) {
        int halfWidth = PROGRESS_BAR_WIDTH / 2;
        int halfHeight = PROGRESS_BAR_HEIGHT / 2;

        context.drawBorder(-halfWidth, -halfHeight, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT, borderColor);
        context.fill(-halfWidth + 1, -halfHeight + 1, halfWidth - 1, halfHeight - 1, backgroundColor);

        int filledWidth = (int) ((PROGRESS_BAR_WIDTH - 2) * progress);
        context.fill(-halfWidth + 1, -halfHeight + 1, -halfWidth + filledWidth + 1, halfHeight - 1, filledColor);

    }
}
