package net.rosemarythyme.simplymore.util.data;

import net.minecraft.sound.SoundEvent;

public record Sound(SoundEvent event, float volume, float pitch) {
    public Sound(SoundEvent event) {
        this(event, 1f, 1f);
    }

    public Sound setVolume(float volume) {
        return new Sound(this.event, volume, this.pitch);
    }

    public Sound setPitch(float pitch) {
        return new Sound(this.event, this.volume, pitch);
    }
}
