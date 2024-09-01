package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class SimplyMoreHelperMethods {

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

    public static void simplyMore$IdolHitEffects(LivingEntity attacker, ParticleEffect particleEffect, int particleCount, double deltaX, double deltaY, double deltaZ, double particleSpeed, AreaEffectCloudEntity auraEntity) {
        if (!attacker.getWorld().isClient() && attacker.getRandom().nextBetween(1, 100) <= 15) {
            ((ServerWorld) attacker.getWorld()).spawnParticles(particleEffect, attacker.getX(), attacker.getY() + 1, attacker.getZ(), particleCount, deltaX, deltaY, deltaZ, particleSpeed);
            attacker.getWorld().spawnEntity(auraEntity);
            attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, attacker.getSoundCategory(), 2.0F, 0.3F);
        }
    }

    public static void simplyMore$IdolUseEffects(Item item, PlayerEntity user, StatusEffect statusEffect, int duration, SoundEvent soundEvent, float soundVolume, float soundPitch, ParticleEffect particleEffect, int particleCount, double deltaX, double deltaY, double deltaZ, double particleSpeed, int skillCooldown) {
        if (!user.getWorld().isClient()) {
            boolean isPositive = statusEffect.isBeneficial();
            Box box = new Box(user.getX() - 10, user.getY() - 10, user.getZ() - 10, user.getX() + 10, user.getY() + 10, user.getZ() + 10);
            for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
                if ((livingEntity == user || livingEntity.isTeammate(user)) != isPositive) continue;

                livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, duration));
            }

            user.getWorld().playSound(null, user.getBlockPos(), soundEvent, user.getSoundCategory(), soundVolume, soundPitch);
            ((ServerWorld) user.getWorld()).spawnParticles(particleEffect, user.getX(), user.getY() + 1, user.getZ(), particleCount, deltaX, deltaY, deltaZ, particleSpeed);
            user.getItemCooldownManager().set(item.getDefaultStack().getItem(), skillCooldown);
        }
    }

    public static void simplyMore$footfallsHelper(Entity entity, ItemStack stack, World world, int stepMod, DefaultParticleType particleEffect) {
        if (stepMod > 0) {
            stepMod--;
        } else {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, particleEffect, particleEffect, particleEffect, true);
    }

    public static void simplyMore$footfallsHelper(Entity entity, ItemStack stack, World world, int stepMod, DefaultParticleType particleEffect, DefaultParticleType sprintParticleEffect, DefaultParticleType passiveParticleEffect) {
        if (stepMod > 0) {
            stepMod--;
        } else {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, particleEffect, sprintParticleEffect, passiveParticleEffect, true);
    }

    public static void simplyMore$applyBlessingOrCurse(float amount, DamageSource source, CallbackInfo info, LivingEntity livingEntity) {
        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.BLESSING)) {

            livingEntity.removeStatusEffect(ModEffectsRegistry.BLESSING);

            livingEntity.heal(4);


            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.WAX_ON,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            info.cancel();
            return;
        }

        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.CURSE)) {

            livingEntity.removeStatusEffect(ModEffectsRegistry.CURSE);

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,100,3));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,100,0));

            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            livingEntity.damage(source, amount * 1.5f);
            info.cancel();
        }
    }
}
