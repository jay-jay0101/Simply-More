package net.rosemarythyme.simplymore.util;

import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.loader.api.FabricLoader;
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
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

public class SimplyMoreHelperMethods {

    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;

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

    public static int getMatterbaneColor(Object color) {
        if (color == null) return 14;
        String colorString = color.toString().replaceAll("\"", "");
        int colorInt;
        try {
            colorInt = Integer.parseInt(colorString);
        } catch (NumberFormatException e) {
            return 14;
        }
        return Math.max(0, Math.min(colorInt, 15));
    }

    public static int getBrassturnOxidisation(ItemStack stack) {
        final int maxOxidisation = 16; // Constant

        NbtElement oxiRaw = stack.getOrCreateNbt().get("simplymore:oxidisation");
        if (oxiRaw == null) {
            return maxOxidisation;
        }

        String oxiString = oxiRaw.toString().replaceAll("\"", "");

        int oxiNumber;
        try {
            oxiNumber = Math.min(maxOxidisation, Integer.parseInt(oxiString));
        } catch (NumberFormatException e) {
            oxiNumber = maxOxidisation;
        }

        return oxiNumber;
    }

    public static int getDeathsEyrieCrows(ItemStack stack) {
        final int minCrows = 1; // Constant

        int crows = stack.getOrCreateNbt().getInt("simplymore:crow");

        crows = Math.max(minCrows, crows);
        crows = Math.min(5, crows);

        return crows;
    }

    public static void simplyMore$IdolHitEffects(LivingEntity attacker, ParticleEffect particleEffect, int particleCount, double deltaX, double deltaY, double deltaZ, double particleSpeed, AreaEffectCloudEntity auraEntity) {
        if (!attacker.getWorld().isClient() && attacker.getRandom().nextBetween(1, 100) <= effect.getIdolSpreadAuraChance()) {
            ((ServerWorld) attacker.getWorld()).spawnParticles(particleEffect, attacker.getX(), attacker.getY() + 1, attacker.getZ(), particleCount, deltaX, deltaY, deltaZ, particleSpeed);
            attacker.getWorld().spawnEntity(auraEntity);
            attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, attacker.getSoundCategory(), 2.0F, 0.3F);
        }
    }

    public static Vector3d getNormalised2dVector(float yaw) {
        double yawAngle = Math.toRadians(yaw);
        double cosYaw = Math.cos(yawAngle);
        double sinYaw = Math.sin(yawAngle);

        return new Vector3d(-sinYaw, 0, cosYaw);
    }

    public static Vector3d getNormalised3dVector(Entity entity) {
        Vec3d vector = entity.getRotationVec(1.0F).normalize();
        return new Vector3d(
                vector.getX(),
                vector.getY(),
                vector.getZ()
        );
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

    public static int simplyMore$footfallsHelper(Entity entity, ItemStack stack, World world, int stepMod, DefaultParticleType particleEffect) {
        if (stepMod > 0) {
            stepMod--;
        } else {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, particleEffect, particleEffect, particleEffect, true);
        return stepMod;
    }

    public static int simplyMore$footfallsHelper(Entity entity, ItemStack stack, World world, int stepMod, DefaultParticleType particleEffect, DefaultParticleType sprintParticleEffect, DefaultParticleType passiveParticleEffect) {
        if (stepMod > 0) {
            stepMod--;
        } else {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, particleEffect, sprintParticleEffect, passiveParticleEffect, true);
        return stepMod;
    }

    public static String translateTicks(int ticks) {
        float seconds = ticks / 20f;
        return new DecimalFormat("#.##").format(seconds);
    }

    public static String toPercentage(float decimal) {
        float output =  decimal * 100;
        return String.valueOf((int) Math.floor(output))
                .concat("%");
    }

    public static void simplyMore$onDamageEffects(float amount, DamageSource source, CallbackInfo info, LivingEntity livingEntity) {
        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.SOLIDIFIED.get())) {
            if (source.getAttacker() != livingEntity) {
                livingEntity.removeStatusEffect(ModEffectsRegistry.SOLIDIFIED.get());
                BladeOfTheGrotesqueItem.causeStun(livingEntity);

                info.cancel();
                return;
            }
        }

        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.BLESSING.get())) {

            livingEntity.removeStatusEffect(ModEffectsRegistry.BLESSING.get());

            livingEntity.heal(effect.getIdolBlessingHeal());


            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.WAX_ON,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            info.cancel();
            return;
        }

        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.CURSE.get())) {

            livingEntity.removeStatusEffect(ModEffectsRegistry.CURSE.get());

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,effect.getIdolCurseNegativeAdditionsTime(),3));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,effect.getIdolCurseNegativeAdditionsTime(),0));

            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            livingEntity.damage(source, amount * (effect.getIdolCurseDamageMultiplier() + 1));
            info.cancel();
        }
    }
}
