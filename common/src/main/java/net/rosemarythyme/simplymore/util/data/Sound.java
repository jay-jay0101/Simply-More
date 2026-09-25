package net.rosemarythyme.simplymore.util.data;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

public record Sound(SoundEvent event, float volume, float pitch) {
    public Sound(SoundEvent event) {
        this(event, 1f, 1f);
    }

    public Sound randomisePitch(float min, float max, Random random) {
        return new Sound(this.event, this.volume, random.nextBetween(Math.round(min * 10), Math.round(max * 10)) / 10f);
    }

    public Sound setVolume(float volume) {
        return new Sound(this.event, volume, this.pitch);
    }

    public Sound setPitch(float pitch) {
        return new Sound(this.event, this.volume, pitch);
    }
}
