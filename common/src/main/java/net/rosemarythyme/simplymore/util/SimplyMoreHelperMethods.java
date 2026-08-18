package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class SimplyMoreHelperMethods {

    // TODO: remove this class

    protected static UniqueEffectConfig effect = ConfigWrapper.UNIQUE;

    // TODO: remove
    public static void simplyMore$setAreaEffectCloudParameters(AreaEffectCloudEntity areaEffectCloudEntity, ParticleEffect particleEffect, int radius, float radiusGrowth, int radiusOnUse, LivingEntity owner, int duration) {
        if (areaEffectCloudEntity != null) {
            areaEffectCloudEntity.setParticleType(particleEffect);
            areaEffectCloudEntity.setRadius(radius);
            areaEffectCloudEntity.setRadiusOnUse(radiusOnUse);
            areaEffectCloudEntity.setRadiusGrowth(radiusGrowth);
            areaEffectCloudEntity.setOwner(owner);
            areaEffectCloudEntity.setDuration(duration);
        }
    }

    // TODO: remove
    public static void simplyMore$setAreaEffectCloudParameters(AreaEffectCloudEntity areaEffectCloudEntity, ParticleEffect particleEffect, float radius, float radiusGrowth, int radiusOnUse, LivingEntity owner, int duration) {
        if (areaEffectCloudEntity != null) {
            areaEffectCloudEntity.setParticleType(particleEffect);
            areaEffectCloudEntity.setRadius(radius);
            areaEffectCloudEntity.setRadiusOnUse(radiusOnUse);
            areaEffectCloudEntity.setRadiusGrowth(radiusGrowth);
            areaEffectCloudEntity.setOwner(owner);
            areaEffectCloudEntity.setDuration(duration);
        }
    }


    public static void simplyMore$IdolHitEffects(LivingEntity attacker, ParticleEffect particleEffect, int particleCount, double deltaX, double deltaY, double deltaZ, double particleSpeed, AreaEffectCloudEntity auraEntity, float chance) {
        if (!attacker.getWorld().isClient() && MathUtils.chance(attacker, chance)) {
            ((ServerWorld) attacker.getWorld()).spawnParticles(particleEffect, attacker.getX(), attacker.getY() + 1, attacker.getZ(), particleCount, deltaX, deltaY, deltaZ, particleSpeed);
            attacker.getWorld().spawnEntity(auraEntity);
            attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, attacker.getSoundCategory(), 2.0F, 0.3F);
        }
    }


    public static void simplyMore$onDamageEffects(float amount, DamageSource source, CallbackInfo info, LivingEntity livingEntity) {
//        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.SOLIDIFIED))) {
//            if (source.getAttacker() != livingEntity) {
//                livingEntity.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.SOLIDIFIED));
//                BladeOfTheGrotesqueItem.causeStun(livingEntity);
//
//                info.cancel();
//                return;
//            }
//        }

        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLESSING))) {

            livingEntity.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLESSING));

            livingEntity.heal(effect.holylight.blessingHeal);


            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.WAX_ON,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            info.cancel();
            return;
        }

        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CURSE))) {

            livingEntity.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.CURSE));

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, effect.darksent.curseWeakenTime,3));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, effect.darksent.curseWeakenTime,0));

            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            livingEntity.damage(source, amount * (effect.darksent.curseDamageMultiplier + 1));
            info.cancel();
        }
    }

}
