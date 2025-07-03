package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.GrieflessExplosionBehavior;

public class BlackPearlFireballEntity extends FireballEntity {
    public BlackPearlFireballEntity(World world, LivingEntity owner, Vec3d velocity) {
        super(world, owner, velocity, 1);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(
                    this,
                    null,
                    new GrieflessExplosionBehavior(),
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    1.8f,
                    false,
                    World.ExplosionSourceType.MOB);
            this.discard();
        }

    }
}
