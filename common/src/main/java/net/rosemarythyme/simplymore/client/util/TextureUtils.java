package net.rosemarythyme.simplymore.client.util;

import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;

public class TextureUtils {
    public static final Identifier FULL_DECAYING_HEART = SimplyMore.identifier("hud/heart/decaying_full");
    public static final Identifier FULL_DECAYING_HEART_BLINKING = SimplyMore.identifier("hud/heart/decaying_full_blinking");
    public static final Identifier HALF_DECAYING_HEART = SimplyMore.identifier("hud/heart/decaying_half");
    public static final Identifier HALF_DECAYING_HEART_BLINKING = SimplyMore.identifier("hud/heart/decaying_half_blinking");

    public static Identifier getDecayingHeartTexture(boolean half, boolean blinking) {
        if (half) {
            return blinking ? HALF_DECAYING_HEART_BLINKING : HALF_DECAYING_HEART;
        } else {
            return blinking ? FULL_DECAYING_HEART_BLINKING : FULL_DECAYING_HEART;
        }
    }
}
