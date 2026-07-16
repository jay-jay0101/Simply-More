package net.rosemarythyme.simplymore.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.entity.AbstractAbilityPlacementEntity;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class AttackUtils {
    public static int INFINITE_DURATION = 9999999;

    public enum AttackTarget {
        OTHERS_AND_USER_NEGATIVELY(true, true, true, false),
        OTHERS_AND_USER_POSITIVELY(true, true, true, true),
        OTHERS_NEGATIVELY(false, true, true, false),
        OTHERS_POSITIVELY(false, true, true, true),
        ALLIES_AND_USER(true, true, false, true),
        ALLIES(false, true, false, true),
        ENEMIES_AND_USER(true, false, true, false),
        ENEMIES(false, false, true, false);

        public final boolean canHitUser;
        public final boolean canHitAllies;
        public final boolean canHitEnemies;
        public final boolean canHitPetOrMount;

        AttackTarget(boolean canHitUser, boolean canHitAllies, boolean canHitEnemies, boolean canHitPetOrMount) {
            this.canHitAllies = canHitAllies;
            this.canHitEnemies = canHitEnemies;
            this.canHitUser = canHitUser;
            this.canHitPetOrMount = canHitPetOrMount;
        }
    }

    public static int getUseTicksFromInfiniteDuration(int duration) {
        return INFINITE_DURATION - duration;
    }

    public static void applyExtraDamage(LivingEntity target, float damageBonus, DamageSource source) {
        int regenTime = target.timeUntilRegen;

        target.timeUntilRegen = 0;
        target.damage(source, damageBonus);

        target.timeUntilRegen = regenTime;
    }

    public static void hitWithEnchants(PlayerEntity attacker, LivingEntity target, float damage) {
        if(!(attacker.getWorld() instanceof ServerWorld world)) return;

        ItemStack weapon = attacker.getStackInHand(Hand.MAIN_HAND);

        DamageSource source = target.getDamageSources().playerAttack(attacker);
        float damageTotal = EnchantmentHelper.getDamage(world, weapon, target, source, damage);

        if (target.damage(source, damageTotal)) {
            EnchantmentHelper.onTargetDamaged(world, target, source, weapon);
        }
    }

    public static boolean isPetOrMount(LivingEntity entity, LivingEntity entity2) {
        return entity == entity2.getVehicle() ||
                entity2 == entity.getVehicle() ||
                (entity instanceof TameableEntity ownableEntity && ownableEntity.getOwner() == entity2) ||
                (entity2 instanceof TameableEntity ownableEntity2 && ownableEntity2.getOwner() == entity);
    }

    public static boolean canTarget(LivingEntity attacker, LivingEntity target, AttackTarget targetType) {
        if(attacker == null || target == null) return false;

        if(!targetType.canHitUser && attacker == target) return false;

        boolean isEnemy = HelperMethods.checkFriendlyFire(target, attacker);
        if(!targetType.canHitEnemies && isEnemy) return false;
        if(!targetType.canHitAllies && !isEnemy) return false;

        return targetType.canHitPetOrMount || !isPetOrMount(attacker, target);
    }

    public static TargetList cylinderAttack(LivingEntity attacker, Vec3d centerPos, float horizontalRange, float verticalRange, AttackTarget targetType) {
        float horizontalDistance = horizontalRange * horizontalRange;

        return cuboidAttack(attacker, centerPos, horizontalRange, verticalRange, targetType).filter(
                (target) -> target.squaredDistanceTo(new Vec3d(centerPos.x, target.getY(), centerPos.z)) < horizontalDistance
        );
    }

    public static TargetList cuboidAttack(LivingEntity attacker, Vec3d centerPos, float horizontalRange, float verticalRange, AttackTarget targetType) {
        Box box = MathUtils.createCuboidBox(centerPos, horizontalRange, verticalRange, horizontalRange);
        return boxAttack(attacker, box, targetType);
    }

    // TODO: REMOVE, ITS ONLY HERE TO KEEP THE MOD COMPILING
    public static List<LivingEntity> cuboidAttack(LivingEntity attacker, Box box) {
        return List.of();
    }

    public static TargetList cubeAttack(LivingEntity attacker, Vec3d centerPos, float range, AttackTarget targetType) {
        Box box = MathUtils.createCubeBox(centerPos, range);
        return boxAttack(attacker, box, targetType);
    }

    public static TargetList boxAttack(LivingEntity attacker, Box box, AttackTarget targetType) {
        if (attacker == null) return TargetList.empty();

        return new TargetList(attacker.getWorld().getNonSpectatingEntities(LivingEntity.class, box)).filter(
                (target) -> canTarget(attacker, target, targetType)
        );
    }

    public static void breakShield(LivingEntity target) {
        if(target.isBlocking() && target instanceof PlayerEntity playerEntity) playerEntity.disableShield();
    }

    public static void spawnAbility(AbstractAbilityPlacementEntity ability, LivingEntity owner) {
        owner.getWorld().spawnEntity(ability);
    }
}
