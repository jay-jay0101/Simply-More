package net.rosemarythyme.simplymore.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.sweenus.simplyswords.util.HelperMethods;

public class AttackUtils {
    public static void hitWithEnchants(PlayerEntity attacker, LivingEntity target, float damage) {
        if(!(attacker.getWorld() instanceof ServerWorld world)) return;

        ItemStack weapon = attacker.getStackInHand(Hand.MAIN_HAND);

        DamageSource source = target.getDamageSources().playerAttack(attacker);
        float damageTotal = EnchantmentHelper.getDamage(world, weapon, target, source, damage);

        if (target.damage(source, damageTotal)) {
            EnchantmentHelper.onTargetDamaged(world, target, source, weapon);
        }
    }

    public static boolean checkFriendlyFire(LivingEntity livingEntity, LivingEntity livingEntityB) {
        return !(HelperMethods.checkFriendlyFire(livingEntity, livingEntityB)
                || HelperMethods.checkFriendlyFire(livingEntityB, livingEntity));
    }
}
