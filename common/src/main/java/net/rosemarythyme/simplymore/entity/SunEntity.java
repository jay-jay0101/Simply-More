package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.TimekeeperItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

public class SunEntity extends AbstractPlanetaryEntity {
    public SunEntity(EntityType<? extends SunEntity> entityType, World world) {
        super(entityType, world);
    }

    public SunEntity(@NotNull LivingEntity owner, boolean empowered) {
        super(owner, owner.getPos(), empowered, EntityRegistry.SUN.get());
    }

    @Override
    public void onHit(LivingEntity owner, TargetList targets) {
        float damage = this.empowered ? TimekeeperItem.SETTINGS.orbitDamage : TimekeeperItem.SETTINGS.unempoweredOrbitDamage;

        targets.forceDamage(damage, owner.getDamageSources().onFire())
                .setOnFireFor(Math.round(TimekeeperItem.SETTINGS.orbitEffectTime / 20f))
                .applyEffect(StatusEffects.GLOWING, TimekeeperItem.SETTINGS.orbitEffectTime, 0)
                .knockback(this.getPos(), TimekeeperItem.SETTINGS.orbitKnockback);
    }

    @Override
    public void aura(LivingEntity owner, float yaw) {
        float range = (1f - (float) Math.abs(MathUtils.getRiseFall(this))) * TimekeeperItem.SETTINGS.orbitAuraRange;

        for (int i = 0; i < 10; i++) {
            if(MathUtils.chance(this, 0.05f)) {
                float attackYaw = yaw + (36f * i);

                AudioVisualUtils.particleLine(getServerWorld(), this.getPos().offset(Direction.UP, 0.3f), attackYaw, 0, range, ParticleTypes.SMALL_FLAME, 0.25f, 15, 0.05f, 0.01f);
                AudioVisualUtils.playSound(owner.getWorld(), this.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get()).randomisePitch(0.8f, 1.3f, owner.getRandom()).setVolume(0.15f));

                TargetUtils.lineAttack(owner, this.getPos(), attackYaw, 0, range, 1, TargetUtils.TargetType.ENEMIES)
                        .damage(TimekeeperItem.SETTINGS.orbitAuraDamage, owner.getDamageSources().onFire())
                        .setOnFireFor(Math.round(TimekeeperItem.SETTINGS.orbitEffectTime / 20f))
                        .applyEffect(StatusEffects.GLOWING, TimekeeperItem.SETTINGS.orbitEffectTime, 0);
            }
        }
    }

    @Override
    public float getYawOffset() {
        return 0;
    }

    @Override
    public boolean isTidallyLocked() {
        return false;
    }
}
