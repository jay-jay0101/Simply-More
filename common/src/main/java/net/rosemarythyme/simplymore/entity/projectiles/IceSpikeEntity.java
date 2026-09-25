package net.rosemarythyme.simplymore.entity.projectiles;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.TimekeeperItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import org.jetbrains.annotations.NotNull;

public class IceSpikeEntity extends AbstractAbilityProjectileEntity {
    @Override
    protected void updateRotation() {
        Pair<Float, Float> yawPitch = MathUtils.getYawAndPitch(this.getVelocity());
        this.setRotation(yawPitch.getLeft(), yawPitch.getRight());
    }

    public IceSpikeEntity(EntityType<IceSpikeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    float getAirDrag() {
        return 1f;
    }

    @Override
    float getWaterDrag() {
        return 1f;
    }

    @Override
    protected double getGravity() {
        return 0f;
    }

    protected void serverTick(LivingEntity owner) {
        if(age % 10 == 0) {
            AudioVisualUtils.particleAroundEntity(this, new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.ICE.getDefaultState()), 1, 0.05f, 0.005f);
        }

        if(this.age >= TimekeeperItem.SETTINGS.orbitAuraRange * 4) {
            this.onHit(BlockHitResult.createMissed(this.getPos(), Direction.DOWN, this.getBlockPos()));
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        AudioVisualUtils.playSound(getWorld(), result.getPos(), new Sound(SoundEvents.BLOCK_GLASS_BREAK).setVolume(0.25f));
        AudioVisualUtils.particleAroundEntity(this, new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.ICE.getDefaultState()), 5, 0.25f, 0.4f);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = getOwner();
        if(!(entity instanceof LivingEntity owner)) return;
        if(!(entityHitResult.getEntity() instanceof LivingEntity target)) return;

        new TargetList(target)
                .forceDamage(TimekeeperItem.SETTINGS.orbitAuraDamage, AttackUtils.getHitSource(owner))
                .applyEffect(StatusEffects.BLINDNESS, TimekeeperItem.SETTINGS.orbitEffectTime, 0)
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), TimekeeperItem.SETTINGS.orbitEffectTime, 0);
    }

    public IceSpikeEntity(@NotNull LivingEntity owner, Vec3d pos, float yaw) {
        super(owner, pos, MathUtils.getDirectionalVector(yaw, 0).multiply(0.5f), EntityRegistry.ICE_SPIKE.get());
    }
}
