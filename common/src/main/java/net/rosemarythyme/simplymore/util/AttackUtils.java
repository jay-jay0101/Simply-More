package net.rosemarythyme.simplymore.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.sweenus.simplyswords.api.SpellScalingProfile;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.util.HelperMethods;

public class AttackUtils {
    public static float scaleDamage(SpellScalingProfile spellSchool, LivingEntity actor, ItemStack stack, float attackScaling, float spellScaling, float baseDamage) {
        return  Math.max(baseDamage, HelperMethods.abilityScaledDamage(spellSchool, actor, stack, attackScaling, spellScaling) * baseDamage);
    }

    public static float scaleDamage(SpellScalingProfile spellSchool, LivingEntity actor, float attackScaling, float spellScaling, float baseDamage) {
        return Math.max(baseDamage, HelperMethods.abilityScaledDamage(spellSchool, actor, attackScaling, spellScaling) * baseDamage);
    }

    public static DamageSource getHitSource(LivingEntity attacker) {
        return attacker instanceof PlayerEntity player ?
                attacker.getDamageSources().playerAttack(player) :
                attacker.getDamageSources().mobAttack(attacker);
    }

    public static TypedActionResult<ItemStack> holdToUse(ServerWorld world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(!(stack.getItem() instanceof UniqueWeaponActiveAbility weapon)) return TypedActionResult.fail(stack);
        if(!ItemStackUtils.isStackAwakened(stack)) return TypedActionResult.fail(stack);

        if(!weapon.canActivate(WeaponAbilityContext.of(world, stack, user, null, null, hand, WeaponAbilityActivationSource.PLAYER))) return TypedActionResult.fail(stack);

        if (stack.getDamage() >= stack.getMaxDamage() - 1) {
            return TypedActionResult.fail(stack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
    }

    public static void applyExtraDamage(LivingEntity target, float damageBonus, DamageSource source) {
        int regenTime = target.timeUntilRegen;

        target.timeUntilRegen = 0;
        target.damage(source, damageBonus);

        target.timeUntilRegen = regenTime;
    }

    public static void applyExtraDamageWithEnchants(LivingEntity attacker, LivingEntity target, float damageBonus) {
        int regenTime = target.timeUntilRegen;

        target.timeUntilRegen = 0;
        hitWithEnchants(attacker, target, damageBonus);

        target.timeUntilRegen = regenTime;
    }

    public static boolean isDamageSourceMelee(DamageSource source) {
        return source.getSource() instanceof LivingEntity entitySource
                && source.getAttacker() instanceof LivingEntity attacker
                && attacker.equals(entitySource);
    }

    public static void hitWithEnchants(LivingEntity attacker, LivingEntity target, float damage) {
        if(!(attacker.getWorld() instanceof ServerWorld world)) return;

        ItemStack weapon = attacker.getStackInHand(Hand.MAIN_HAND);

        DamageSource source = getHitSource(attacker);
        float damageTotal = EnchantmentHelper.getDamage(world, weapon, target, source, damage);

        if (target.damage(source, damageTotal)) {
            EnchantmentHelper.onTargetDamaged(world, target, source, weapon);
        }
    }

    public static void breakShield(LivingEntity target) {
        if(target.isBlocking() && target instanceof PlayerEntity playerEntity) playerEntity.disableShield();
    }

    public static void knockback(LivingEntity attacker, LivingEntity target, double scale) {
        Vec3d attackerPos = attacker.getPos();
        knockback(attackerPos, target, scale);
    }

    public static void knockback(Vec3d attackerPos, LivingEntity target, double scale) {
        Vec3d targetPos = target.getPos();

        Vec3d direction = MathUtils.normalisedDirectionBetween(attackerPos, targetPos, false);
        target.setVelocity(direction.multiply(scale).add(0, 0.2, 0));
        target.velocityModified = true;
    }
}
