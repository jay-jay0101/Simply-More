package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.BlackPearlItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import org.jetbrains.annotations.NotNull;

public class CannonballEntity extends AbstractAbilityProjectileEntity {
    public CannonballEntity(EntityType<? extends AbstractAbilityProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public CannonballEntity(@NotNull LivingEntity owner, Vec3d position, Vec3d velocity) {
        super(owner, position, velocity, EntityRegistry.CANNONBALL.get());
    }

    @Override
    protected void onHit(HitResult result) {
        if(!(getOwner() instanceof LivingEntity owner)) return;

        AudioVisualUtils.playSound(getWorld(), getPos(), new Sound(SoundEvents.ENTITY_GENERIC_EXPLODE.value()));
        AudioVisualUtils.applyScreenshake((ServerWorld) getWorld(), getPos(), owner, 15, 1, 15);
        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.EXPLOSION, 1, 0, 0);
        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.SMOKE, 100, 0, 0.5);

        AttackUtils.cubeAttack(owner, getPos(), 4, AttackUtils.AttackTarget.ENEMIES)
                .damage(BlackPearlItem.SETTINGS.cannonballDamage, this.getDamageSources().explosion(this, owner))
                .knockback(this.getPos(), BlackPearlItem.SETTINGS.cannonballKnockback);
    }

    @Override
    float getAirDrag() {
        return 0.95f;
    }

    @Override
    float getWaterDrag() {
        return 0.8f;
    }

    @Override
    protected double getGravity() {
        return MathUtils.clampedLerp(age, 0, 40, 0f, 0.08f);
    }
}
