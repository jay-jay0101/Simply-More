package net.rosemarythyme.simplymore.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class AttackUtils {
    public static int INFINITE_DURATION = 9999999;

    public static void hitWithEnchants(PlayerEntity attacker, LivingEntity target, float damage) {
        if(!(attacker.getWorld() instanceof ServerWorld world)) return;

        ItemStack weapon = attacker.getStackInHand(Hand.MAIN_HAND);

        DamageSource source = target.getDamageSources().playerAttack(attacker);
        float damageTotal = EnchantmentHelper.getDamage(world, weapon, target, source, damage);

        if (target.damage(source, damageTotal)) {
            EnchantmentHelper.onTargetDamaged(world, target, source, weapon);
        }
    }

    public static boolean canHitTarget(LivingEntity attacker, LivingEntity target) {
        return attacker != null &&
            target != null &&
            target != attacker &&
            target != attacker.getVehicle() &&
            attacker != target.getVehicle() &&
            !target.isInvulnerable() &&
            !target.isDead() &&
            HelperMethods.checkFriendlyFire(attacker, target);
    }

    public static List<LivingEntity> getTargets(LivingEntity attacker, Box box) {
        if (attacker == null) return List.of();

        return attacker.getWorld().getNonSpectatingEntities(LivingEntity.class, box).stream().filter(
                (target) -> canHitTarget(attacker, target)
        ).toList();
    }

    public static void breakShield(LivingEntity target) {
        if(target.isBlocking() && target instanceof PlayerEntity playerEntity) playerEntity.disableShield();
    }
}
