package net.rosemarythyme.simplymore.client.hud;

import net.minecraft.util.math.ColorHelper;

public class BrassturnHud extends CounterBarHudOverlay {
    protected final int maxBorderColor;
    protected final int maxBackgroundColor;
    protected final int maxFillColor;
    protected final int maxTextColor;

    public BrassturnHud(String translationKey, int borderColor, int maxBorderColor, int backgroundColor, int maxBackgroundColor, int fillColor, int maxFillColor, int textColor, int maxTextColor) {
        super(translationKey, borderColor, backgroundColor, fillColor, textColor);

        this.maxBorderColor = maxBorderColor;
        this.maxBackgroundColor = maxBackgroundColor;
        this.maxFillColor = maxFillColor;
        this.maxTextColor = maxTextColor;
    }

    @Override
    protected int getBackgroundColor(float data) {
        return ColorHelper.Argb.lerp(data, backgroundColor, maxBackgroundColor);
    }

    @Override
    protected int getBorderColor(float data) {
        return ColorHelper.Argb.lerp(data, borderColor, maxBorderColor);
    }

    @Override
    protected int getFillColor(float data) {
        return ColorHelper.Argb.lerp(data, fillColor, maxFillColor);
    }

    @Override
    protected int getTextColor(float data) {
        return ColorHelper.Argb.lerp(data, textColor, maxTextColor);
    }
}
