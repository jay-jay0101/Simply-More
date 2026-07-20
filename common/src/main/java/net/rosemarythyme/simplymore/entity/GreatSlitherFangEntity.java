package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;

public class GreatSlitherFangEntity extends EvokerFangsEntity {
    LivingEntity owner;
    protected static UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;


    public GreatSlitherFangEntity(World world, double x, double y, double z, float yaw, int warmup, LivingEntity owner) {
        this(EntityRegistry.GREAT_SLITHER_FANG.get(), world);
        this.warmup = warmup;
        this.setOwner(owner);
        this.setYaw((float) Math.toRadians(yaw));
        this.setPosition(x, y, z);
    }

    public GreatSlitherFangEntity(EntityType<GreatSlitherFangEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void damage(LivingEntity target) {
        if (!AttackUtils.canTarget(getOwner(), target, AttackUtils.AttackTarget.ENEMIES)) return;

        new TargetList(target)
            .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.VENOM), UNIQUE_CONFIG.great_slither.fangsVenomTime, 0)
            .applyEffect(StatusEffects.SLOWNESS, UNIQUE_CONFIG.great_slither.fangsSlowTime, 2)
            .damage(UNIQUE_CONFIG.great_slither.fangDamage, getDamageSources().indirectMagic(this, owner));
    }
}
