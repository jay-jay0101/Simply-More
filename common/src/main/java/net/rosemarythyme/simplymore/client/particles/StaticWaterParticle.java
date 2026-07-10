package net.rosemarythyme.simplymore.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;

public class StaticWaterParticle extends SpriteBillboardParticle {
    private final SimpleParticleType splashParticle;

    StaticWaterParticle(ClientWorld world, double x, double y, double z, SimpleParticleType splashParticle) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.gravityStrength = 0;
        this.splashParticle = splashParticle;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        if(this.isAlive()) {
            this.world.addParticle(this.splashParticle, this.x, this.y, this.z, 0d, 0d, 0d);
            this.markDead();
        }
    }

    public static SpriteBillboardParticle createHolyWater(ClientWorld world, double x, double y, double z, SpriteProvider provider) {
        StaticWaterParticle particle = new StaticWaterParticle(world, x, y, z, ParticleTypes.SPLASH);
        particle.setSprite(provider);
        particle.setColor(0.2F, 0.3F, 1.0F);
        return particle;
    }

    public static SpriteBillboardParticle createUnholyWater(ClientWorld world, double x, double y, double z, SpriteProvider provider) {
        StaticWaterParticle particle = new StaticWaterParticle(world, x, y, z, ParticleTypes.SPLASH);
        particle.setSprite(provider);
        particle.setColor(0.2F, 0.3F, 1.0F);
        return particle;
    }

    public static class HolyWaterFactory implements ParticleFactory<SimpleParticleType> {
        final SpriteProvider provider;
        public HolyWaterFactory(SpriteProvider spriteProvider) {
            this.provider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return createHolyWater(world, x, y, z, provider);
        }
    }
}
