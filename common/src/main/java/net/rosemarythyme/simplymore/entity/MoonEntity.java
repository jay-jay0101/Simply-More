package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.projectiles.IceSpikeEntity;
import net.rosemarythyme.simplymore.item.uniques.TimekeeperItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.SummonUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

public class MoonEntity extends AbstractPlanetaryEntity {
    public MoonEntity(EntityType<? extends MoonEntity> entityType, World world) {
        super(entityType, world);
    }

    public MoonEntity(@NotNull LivingEntity owner, boolean empowered) {
        super(owner, owner.getPos(), empowered, EntityRegistry.MOON.get());
    }

    @Override
    public void onHit(LivingEntity owner, TargetList targets) {
        float damage = this.empowered ? TimekeeperItem.SETTINGS.orbitDamage : TimekeeperItem.SETTINGS.unempoweredOrbitDamage;

        targets.forceDamage(damage, owner.getDamageSources().onFire())
                .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), TimekeeperItem.SETTINGS.orbitEffectTime, 0)
                .applyEffect(StatusEffects.BLINDNESS, TimekeeperItem.SETTINGS.orbitEffectTime, 0)
                .knockback(this.getPos(), TimekeeperItem.SETTINGS.orbitKnockback);
    }

    @Override
    public void aura(LivingEntity owner, float yaw) {
        for (int i = 0; i < 10; i++) {
            if(MathUtils.chance(this, 0.05f)) {
                AudioVisualUtils.playSound(owner.getWorld(), this.getPos().offset(Direction.UP, 0.3f), new Sound(SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_03.get()).randomisePitch(1.7f, 2f, owner.getRandom()).setVolume(0.15f));

                float attackYaw = yaw + (36f * i);
                SummonUtils.spawnProjectile(new IceSpikeEntity(owner, this.getPos(), attackYaw), owner);
            }
        }
    }

    @Override
    public float getYawOffset() {
        return 180f;
    }

    @Override
    public boolean isTidallyLocked() {
        return true;
    }
}
