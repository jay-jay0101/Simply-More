package net.rosemarythyme.simplymore.entity.projectiles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.DeathsEyrieItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

public class CrowProjectileEntity extends AbstractAbilityProjectileEntity {

    public CrowProjectileEntity(@NotNull LivingEntity owner, Vec3d position, float yaw) {
        super(owner, position, getSpawnVelocity(yaw), EntityRegistry.CROW_PROJECTILE.get());
    }

    private static Vec3d getSpawnVelocity(float yaw) {
        return MathUtils.getDirectionalVector(yaw, 0).multiply(1.2f);
    }

    public CrowProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        if(MathUtils.chance(owner, 1/30f)) {
            AudioVisualUtils.playSound(owner.getWorld(), this.getPos(), new Sound(SoundEvents.ENTITY_PARROT_IMITATE_PHANTOM).randomisePitch(0.6f, 1.2f, this.getRandom()));
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult)  {
        if(!(entityHitResult.getEntity() instanceof LivingEntity target)) return;
        if(!(this.getOwner() instanceof LivingEntity owner)) return;

        if(target.hurtTime <= 0) {
            AudioVisualUtils.playSound(this.getWorld(), this.getPos(), new Sound(SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_03.get()).randomisePitch(1.2f, 2f, this.getRandom()));
        }

        new TargetList(target)
                .damage(DeathsEyrieItem.SETTINGS.damage, this.getDamageSources().mobProjectile(this, owner))
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), DeathsEyrieItem.SETTINGS.effectTime, 0)
                .applyEffect(StatusEffects.BLINDNESS, DeathsEyrieItem.SETTINGS.effectTime, 0);
    }

    @Override
    protected boolean shouldPierce(HitResult result) {
        return result instanceof EntityHitResult;
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

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public Text getName() {
        return Text.translatable("entity.simplymore.crow");
    }

    @Override
    public int getLifespan() {
        return 20 * 4;
    }
}
