package net.rosemarythyme.simplymore.entity.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAbilityProjectileEntity extends ProjectileEntity {
    public AbstractAbilityProjectileEntity(@NotNull LivingEntity owner, Vec3d position, Vec3d velocity, EntityType<? extends AbstractAbilityProjectileEntity> entityType) {
        this(entityType, owner.getWorld());
        this.setOwner(owner);
        this.refreshPositionAfterTeleport(position);
        this.setVelocity(velocity);

        Pair<Float, Float> yawAndPitch = MathUtils.getYawAndPitch(velocity);
        this.setYaw(yawAndPitch.getLeft());
        this.setPitch(yawAndPitch.getRight());
    }

    public AbstractAbilityProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void onHit(HitResult result) {}

    protected void serverTick(LivingEntity owner) {}

    private void serverTick() {
        Entity owner = getOwner();

        if(!(owner instanceof LivingEntity livingOwner) || !owner.isAlive() || owner.getWorld() != this.getWorld()) {
            this.discard();
            return;
        }

        HitResult hitresult = ProjectileUtil.getCollision(this, PredicateUtils.createForTargetType(livingOwner, AttackUtils.AttackTarget.ENEMIES));
        if(hitresult.getType() != HitResult.Type.MISS) {
            if(canCollide(hitresult)) {
                onHit(hitresult);

                if(hitresult instanceof EntityHitResult entityHitResult) onEntityHit(entityHitResult);
                if(hitresult instanceof BlockHitResult blockHitResult) onBlockHit(blockHitResult);

                this.discard();
            }
        }

        serverTick(livingOwner);
    }

    protected boolean canCollide(HitResult result) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        ProjectileUtil.setRotationFromVelocity(this, 0.2F);

        Vec3d velocity = this.getVelocity();
        float drag = getAirDrag();
        if(isTouchingWater()) {
            AudioVisualUtils.particleAroundEntity(this, ParticleTypes.BUBBLE, 4, 0.25f, 0);
            drag = getWaterDrag();
        }

        this.setVelocity(velocity.multiply(drag));
        applyGravity();

        if(!this.getWorld().isClient) serverTick();

        velocity = getVelocity();
        this.setPosition(getPos().add(velocity));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    abstract float getAirDrag();
    abstract float getWaterDrag();

    @Override
    protected double getGravity() {
        return 0.08f;
    }
}
