package net.rosemarythyme.simplymore.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;

import java.util.HashMap;
import java.util.Map;

public class HudUtils {
    private static final Map<HudOverlayItem<?>, Object> CACHE = new HashMap<>();

    public static void clearCache() {
        CACHE.clear();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCache(HudOverlayItem<T> item, ItemStack stack, ClientPlayerEntity player) {
        return (T) CACHE.computeIfAbsent(item, data -> item.getHudData(stack, player));
    }


    public static final int SQUARE_BORDER_SIZE = 4;
    public static final int SQUARE_FILLED_SIZE = SQUARE_BORDER_SIZE - 1;
    public static void renderSquareProgress(DrawContext context, int totalSquares, int squaresCompleted, int borderColor, int filledColor) {
        int dx = (totalSquares - 1) * SQUARE_BORDER_SIZE;
        for(int i = 0; i < totalSquares; i++) {
            int x = i * 2 * SQUARE_BORDER_SIZE - dx;
            context.fill(x - SQUARE_BORDER_SIZE, -SQUARE_BORDER_SIZE, x + SQUARE_BORDER_SIZE, -SQUARE_FILLED_SIZE, borderColor);
            context.fill(x - SQUARE_BORDER_SIZE, -SQUARE_BORDER_SIZE, x - SQUARE_BORDER_SIZE + 1, SQUARE_FILLED_SIZE, borderColor);
            context.fill(x - SQUARE_BORDER_SIZE, SQUARE_BORDER_SIZE, x + SQUARE_BORDER_SIZE, SQUARE_FILLED_SIZE, borderColor);
            context.fill(x + SQUARE_BORDER_SIZE, SQUARE_BORDER_SIZE, x + SQUARE_BORDER_SIZE - 1, -SQUARE_FILLED_SIZE, borderColor);

            if(squaresCompleted-- > 0) {
                context.fill(x - SQUARE_FILLED_SIZE, -SQUARE_FILLED_SIZE, x + SQUARE_FILLED_SIZE, SQUARE_FILLED_SIZE, filledColor);
            }
        }
    }
}
