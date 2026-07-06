package net.rosemarythyme.simplymore.util.data;

import net.minecraft.particle.SimpleParticleType;

public record FootfallParticles(SimpleParticleType walkingParticle, SimpleParticleType sprintingParticle, SimpleParticleType passiveParticle) {
    public FootfallParticles(SimpleParticleType particle) {
        this(particle, particle, particle);
    }

    public static FootfallParticles none() {
        return new FootfallParticles(null);
    }

    public boolean hasParticles() {
        return walkingParticle != null;
    }
}
