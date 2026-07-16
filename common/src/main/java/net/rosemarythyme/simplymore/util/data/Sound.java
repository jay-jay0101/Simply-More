package net.rosemarythyme.simplymore.util.data;

import net.minecraft.sound.SoundEvent;

public record Sound(SoundEvent event, float volume, float pitch) {
    public static Sound of(SoundEvent event) {
        return new Sound(event, 1f, 1f);
    }

    public Sound setVolume(float volume) {
        return new Sound(this.event, volume, this.pitch);
    }

    public Sound setPitch(float pitch) {
        return new Sound(this.event, this.volume, pitch);
    }
}
