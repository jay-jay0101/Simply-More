package net.rosemarythyme.simplymore.util;

import net.minecraft.enchantment.EnchantmentHelper;
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
import net.minecraft.item.ToolItem;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.rosemarythyme.simplymore.item.uniques.BladeOfTheGrotesqueItem;
import net.rosemarythyme.simplymore.registry.ModComponentRegistry;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

public class SimplyMoreHelperMethods {

    protected static UniqueEffectConfig effect = ConfigWrapper.unique;

    public static void hitWithEnchants(PlayerEntity attacker, LivingEntity target, float damage) {
        if(!(attacker.getWorld() instanceof ServerWorld world)) return;

        ItemStack weapon = attacker.getStackInHand(Hand.MAIN_HAND);

        DamageSource source = target.getDamageSources().playerAttack(attacker);
        float damageTotal = EnchantmentHelper.getDamage(world, weapon, target, source, damage);

        if (target.damage(source, damageTotal)) {
            EnchantmentHelper.onTargetDamaged(world, target, source, weapon);
        }
    }

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


    public static CounterComponent getCounterComponent(ItemStack stack) {
        if((stack.getItem() instanceof SimplyMoreUniqueSwordItem swordItem)) {
            if(stack.getComponents().contains(ModComponentRegistry.COUNTER.get())) {
                return stack.getComponents().get(ModComponentRegistry.COUNTER.get());
            } else {
                return setCounterComponent(stack, swordItem.getDefaultComponent());
            }
        }

        return null;
    }

    public static CounterComponent setCounterComponent(ItemStack stack, CounterComponent component) {
        stack.set(ModComponentRegistry.COUNTER.get(), component);
        return component;
    }

    public static void simplyMore$IdolHitEffects(LivingEntity attacker, ParticleEffect particleEffect, int particleCount, double deltaX, double deltaY, double deltaZ, double particleSpeed, AreaEffectCloudEntity auraEntity, float chance) {
        if (!attacker.getWorld().isClient() && chance(attacker, chance)) {
            ((ServerWorld) attacker.getWorld()).spawnParticles(particleEffect, attacker.getX(), attacker.getY() + 1, attacker.getZ(), particleCount, deltaX, deltaY, deltaZ, particleSpeed);
            attacker.getWorld().spawnEntity(auraEntity);
            attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.ITEM_BUCKET_FILL, attacker.getSoundCategory(), 2.0F, 0.3F);
        }
    }

    public static boolean shouldGrantLanceEffect(LivingEntity entity) {
        return isLanceInMainHand(entity) && isRidingLivingEntity(entity) && isOffHandEmpty(entity);
    }

    private static boolean isLanceInMainHand(LivingEntity livingEntity) {

        return livingEntity.getMainHandStack().getItem() instanceof Weapon weapon && weapon.swordType() == Weapon.SwordTypes.LANCE;
    }

    private static boolean isRidingLivingEntity(LivingEntity entity) {
        return entity.getVehicle() instanceof LivingEntity;
    }

    private static boolean isOffHandEmpty(LivingEntity livingEntity) {
        return !(livingEntity.getStackInHand(Hand.OFF_HAND).getItem() instanceof ToolItem);
    }

    public static boolean chance(LivingEntity player, float chance) {
        return player.getRandom().nextFloat() <= chance;
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

    public static void simplyMore$IdolUseEffects(Item item, PlayerEntity user, RegistryEntry<StatusEffect> statusEffect, int duration, SoundEvent soundEvent, float soundVolume, float soundPitch, ParticleEffect particleEffect, int particleCount, double deltaX, double deltaY, double deltaZ, double particleSpeed, int skillCooldown) {
        if (!user.getWorld().isClient()) {
            boolean isPositive = statusEffect.value().isBeneficial();
            Box box = new Box(user.getX() - 10, user.getY() - 10, user.getZ() - 10, user.getX() + 10, user.getY() + 10, user.getZ() + 10);
            for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
                if ((livingEntity == user || SimplyMoreHelperMethods.checkFriendlyFire(livingEntity, user)) != isPositive) continue;

                livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, duration));
            }

            user.getWorld().playSound(null, user.getBlockPos(), soundEvent, user.getSoundCategory(), soundVolume, soundPitch);
            ((ServerWorld) user.getWorld()).spawnParticles(particleEffect, user.getX(), user.getY() + 1, user.getZ(), particleCount, deltaX, deltaY, deltaZ, particleSpeed);
            user.getItemCooldownManager().set(item, skillCooldown);
        }
    }

    public static void simplyMore$footfallsHelper(Entity entity, ItemStack stack, World world, SimpleParticleType particleEffect) {
        simplyMore$footfallsHelper(entity, stack, world, particleEffect, particleEffect, particleEffect);
    }

    public static void simplyMore$footfallsHelper(Entity entity, ItemStack stack, World world, SimpleParticleType particleEffect, SimpleParticleType sprintParticleEffect, SimpleParticleType passiveParticleEffect) {
        HelperMethods.createFootfalls(entity, stack, world, particleEffect, sprintParticleEffect, passiveParticleEffect, true);
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
        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.SOLIDIFIED))) {
            if (source.getAttacker() != livingEntity) {
                livingEntity.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.SOLIDIFIED));
                BladeOfTheGrotesqueItem.causeStun(livingEntity);

                info.cancel();
                return;
            }
        }

        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.BLESSING))) {

            livingEntity.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.BLESSING));

            livingEntity.heal(effect.holylight.blessingHeal);


            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE.value(), SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.WAX_ON,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            info.cancel();
            return;
        }

        if (!livingEntity.isInvulnerableTo(source) && livingEntity.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.CURSE))) {

            livingEntity.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.CURSE));

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, effect.darksent.curseWeakenTime,3));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, effect.darksent.curseWeakenTime,0));

            livingEntity.getWorld().playSound(null,livingEntity.getBlockPos(), SoundEvents.ENTITY_ALLAY_ITEM_TAKEN, SoundCategory.PLAYERS);
            ((ServerWorld) livingEntity.getWorld()).spawnParticles(ParticleTypes.SCULK_SOUL,livingEntity.getX(),livingEntity.getY()+1,livingEntity.getZ(),50,0.25,0.5,0.25,0.1);
            livingEntity.damage(source, amount * (effect.darksent.curseDamageMultiplier + 1));
            info.cancel();
        }
    }

    public static boolean checkFriendlyFire(LivingEntity livingEntity, LivingEntity livingEntityB) {
        return !(HelperMethods.checkFriendlyFire(livingEntity, livingEntityB)
                || HelperMethods.checkFriendlyFire(livingEntityB, livingEntity));
    }
}
