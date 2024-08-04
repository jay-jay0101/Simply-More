package net.rosemarythmye.simplymore.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.util.GrieflessExplosionBehavior;

public class BlackPearlFireball extends FireballEntity {
    public BlackPearlFireball(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(world, owner, velocityX, velocityY, velocityZ, 1);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, (DamageSource)null, new GrieflessExplosionBehavior(),this.getX(), this.getY(), this.getZ(), 1.8f,false, World.ExplosionSourceType.MOB);
            this.discard();
        }

    }
}
