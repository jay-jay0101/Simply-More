package net.rosemarythyme.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.rosemarythyme.simplymore.item.uniques.MoltenFlareItem;

public class MoltenFlareEffect extends StatusEffect {


    public MoltenFlareEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity.getMainHandStack().getItem() instanceof MoltenFlareItem)) {
            livingEntity.removeStatusEffect(this);
            return;
        }

        if (!livingEntity.getEntityWorld().isClient()) {
            spawnParticles(livingEntity);
        }

        super.applyUpdateEffect(livingEntity, amplifier);
    }

    private void spawnParticles(LivingEntity livingEntity) {
        ServerWorld serverWorld = (ServerWorld) livingEntity.getEntityWorld();
        serverWorld.spawnParticles(
                ParticleTypes.SMOKE,
                livingEntity.getX(),
                livingEntity.getY() + 2,
                livingEntity.getZ(),
                3,
                0.5d,
                0.5d,
                0.5d,
                0);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
