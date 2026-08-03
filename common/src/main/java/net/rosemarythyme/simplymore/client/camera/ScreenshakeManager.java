package net.rosemarythyme.simplymore.client.camera;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector2f;
import net.rosemarythyme.simplymore.config.ClientConfig;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.util.MathUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ScreenshakeManager {
    public static ArrayList<Screenshake> shakeInstances = new ArrayList<>();

    public static void add(float intensity, int duration, boolean fromSelf) {
        if(ConfigWrapper.CLIENT.screenshake.get() == ClientConfig.Screenshake.NONE) return;
        if(ConfigWrapper.CLIENT.screenshake.get() == ClientConfig.Screenshake.OTHERS && fromSelf) return;
        if(ConfigWrapper.CLIENT.screenshake.get() == ClientConfig.Screenshake.SELF && !fromSelf) return;

        shakeInstances.add(new Screenshake(duration, 0, intensity));
    }

    public static final class Screenshake {
        int duration;
        int currentDuration;
        float intensity;

        public Screenshake(int duration, int currentDuration, float intensity) {
            this.duration = duration;
            this.currentDuration = currentDuration;
            this.intensity = intensity;
        }

        public void add(int add) {
            this.currentDuration += add;
        }

        public float getLerpedIntensity() {
            float intro = duration / 10f;
            float outro = duration / 2f;

            if(currentDuration < intro) {
                return MathUtils.clampedLerp(currentDuration, 0, intro, 0, intensity);
            } else if(currentDuration < outro) {
                return intensity;
            }

            return intensity - MathUtils.clampedLerp(currentDuration, outro, duration, 0, intensity);
        }
    }

    public static void tickScreenShakes() {
        for (Screenshake screenshake : List.copyOf(shakeInstances)) {
            screenshake.add(1);

            if(screenshake.currentDuration > screenshake.duration) {
                shakeInstances.remove(screenshake);
            }
        }
    }

    public static Vector2f calculateRotation() {
        Optional<Screenshake> mostIntenseShake = shakeInstances.stream().max(Comparator.comparingDouble(Screenshake::getLerpedIntensity));
        if(mostIntenseShake.isEmpty()) return new Vector2f(0, 0);

        float intensity = mostIntenseShake.get().getLerpedIntensity();

        float time = MinecraftClient.getInstance().world.getTime() + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true);
        float yawNoise = (float)(
                Math.sin(time * 17.0) * 0.5 +
                        Math.sin(time * 31.0) * 0.3 +
                        Math.sin(time * 47.0) * 0.2
        );

        float pitchNoise = (float)(
                Math.sin(time * 17.0 + 100) * 0.5 +
                        Math.sin(time * 31.0 + 50) * 0.3 +
                        Math.sin(time * 47.0 + 20) * 0.2
        );

        return new Vector2f(yawNoise * intensity, pitchNoise * intensity);
    }
}
