package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.TimekeeperItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

public class PlanetaryBurstEntity extends AbstractAbilityPlacementEntity {
    public final boolean isMoon;
    public final boolean isEmpowered;

    public PlanetaryBurstEntity(EntityType<PlanetaryBurstEntity> entityType, World world) {
        super(entityType, world);
        isMoon = false;
        this.isEmpowered = false;
    }

    public PlanetaryBurstEntity(@NotNull LivingEntity owner, boolean isMoon, boolean isEmpowered) {
        super(owner, owner.getPos(), EntityRegistry.PLANETARY_BURST.get());
        this.isMoon = isMoon;
        this.isEmpowered = isEmpowered;
    }

    @Override
    public int getLifespan() {
        return 10;
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        float range = MathUtils.clampedLerp(age, 0, getLifespan(), 0f, TimekeeperItem.SETTINGS.orbitRange);
        float damage = this.isEmpowered ? TimekeeperItem.SETTINGS.orbitDamage : TimekeeperItem.SETTINGS.unempoweredOrbitDamage / 1.5f;

        if(isMoon) {
            moonAttack(owner, range, damage);
        } else {
            sunAttack(owner, range, damage);
        }
    }

    private void moonAttack(LivingEntity owner, float range, float damage) {
        AudioVisualUtils.playSound(owner.getWorld(), this.getPos().offset(Direction.UP, 0.3f), new Sound(SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_02.get()).randomisePitch(0.8f, 1.3f, owner.getRandom()));
        AudioVisualUtils.particleRing(getServerWorld(), owner.getPos(), ParticleTypes.SWEEP_ATTACK, range, 40);
        AttackUtils.cylinderAttack(owner, this.getPos().offset(Direction.UP, 2), range, 2, AttackUtils.AttackTarget.ENEMIES)
                .forceDamage(damage, owner.getDamageSources().onFire())
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), TimekeeperItem.SETTINGS.orbitEffectTime, 0)
                .applyEffect(StatusEffects.BLINDNESS, TimekeeperItem.SETTINGS.orbitEffectTime, 0)
                .knockback(this.getPos(), TimekeeperItem.SETTINGS.orbitKnockback);
    }

    private void sunAttack(LivingEntity owner, float range, float damage) {
        AudioVisualUtils.playSound(owner.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_02.get()).randomisePitch(0.8f, 1.3f, owner.getRandom()));
        AudioVisualUtils.particleRing(getServerWorld(), owner.getPos().offset(Direction.UP, 0.25f), ParticleTypes.FLAME, range, 400);
        AttackUtils.cylinderAttack(owner, this.getPos().offset(Direction.UP, 2), range, 2, AttackUtils.AttackTarget.ENEMIES)
                .forceDamage(damage, owner.getDamageSources().onFire())
                .setOnFireFor(Math.round(TimekeeperItem.SETTINGS.orbitEffectTime / 20f))
                .applyEffect(StatusEffects.GLOWING, TimekeeperItem.SETTINGS.orbitEffectTime, 0)
                .knockback(this.getPos(), TimekeeperItem.SETTINGS.orbitKnockback);
    }

    @Override
    public int getOutroTicks() {
        return 0;
    }

    @Override
    public int getIntroTicks() {
        return 0;
    }
}
