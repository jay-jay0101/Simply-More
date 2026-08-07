package net.rosemarythyme.simplymore.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.entity.AbstractAbilityPlacementEntity;
import net.rosemarythyme.simplymore.entity.AbstractAbilityProjectileEntity;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;
import java.util.Set;

public class AttackUtils {
    public static int PSEUDOINFINITE_DURATION = 9999999;

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

    public static float scaleDamage(String spellSchool, LivingEntity actor, ItemStack stack, float attackScaling, float spellScaling, float baseDamage) {
        return  Math.max(baseDamage, HelperMethods.abilityScaledDamage(spellSchool, actor, stack, attackScaling, spellScaling) * baseDamage);
    }

    public static float scaleDamage(String spellSchool, LivingEntity actor, float attackScaling, float spellScaling, float baseDamage) {
        return Math.max(baseDamage, HelperMethods.abilityScaledDamage(spellSchool, actor, attackScaling, spellScaling) * baseDamage);
    }

    public static TypedActionResult<ItemStack> holdToUse(PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (stack.getDamage() >= stack.getMaxDamage() - 1) {
            return TypedActionResult.fail(stack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
    }

    public static int getUseTicksFromInfiniteDuration(int duration) {
        return PSEUDOINFINITE_DURATION - duration;
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
        if(target instanceof AbstractAbilityPlacementEntity) return false;

        if(!target.canHit()) return false;

        if(!targetType.canHitUser && attacker == target) return false;

        boolean isEnemy = HelperMethods.checkFriendlyFire(target, attacker);
        if(!targetType.canHitEnemies && isEnemy) return false;
        if(!targetType.canHitAllies && !isEnemy) return false;

        return targetType.canHitPetOrMount || !isPetOrMount(attacker, target);
    }

    public static TargetList cylinderAttack(LivingEntity attacker, Vec3d centerPos, double horizontalRange, double verticalRange, AttackTarget targetType) {
        double horizontalDistance = horizontalRange * horizontalRange;

        return cuboidAttack(attacker, centerPos, horizontalRange, verticalRange, targetType).filter(
                (target) -> target.squaredDistanceTo(new Vec3d(centerPos.x, target.getY(), centerPos.z)) < horizontalDistance
        );
    }

    public static LivingEntity getTargetedEntity(LivingEntity attacker, float range, AttackTarget targetType) {
        Entity entity = HelperMethods.getTargetedEntity(attacker, range);
        if(!(entity instanceof LivingEntity target)) return null;
        if(canTarget(attacker, target, targetType)) return target;

        return null;
    }

    public static TargetList cuboidAttack(LivingEntity attacker, Vec3d centerPos, double horizontalRange, double verticalRange, AttackTarget targetType) {
        Box box = MathUtils.createCuboidBox(centerPos, horizontalRange, verticalRange, horizontalRange);
        return boxAttack(attacker, box, targetType);
    }

    // TODO: REMOVE, ITS ONLY HERE TO KEEP THE MOD COMPILING
    public static List<LivingEntity> cuboidAttack(LivingEntity attacker, Box box) {
        return List.of();
    }

    public static TargetList cubeAttack(LivingEntity attacker, Vec3d centerPos, double range, AttackTarget targetType) {
        Box box = MathUtils.createCubeBox(centerPos, range);
        return boxAttack(attacker, box, targetType);
    }

    public static TargetList boxAttack(LivingEntity attacker, Box box, AttackTarget targetType) {
        if (attacker == null) return TargetList.empty();

        List<LivingEntity> targets = attacker.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

        return new TargetList(Set.copyOf(targets)).filter(
                (target) -> canTarget(attacker, target, targetType)
        );
    }

    public static TargetList lineAttack(LivingEntity attacker, Vec3d startPos, Vec3d endPos, double width, AttackTarget targetType) {
        Vec3d delta = endPos.subtract(startPos);
        return lineAttack(attacker, startPos, delta.normalize(), delta.length(), width, targetType);
    }

    public static TargetList lineAttack(LivingEntity attacker, Vec3d startPos, float yaw, float pitch, double length, double width, AttackTarget targetType) {
        return lineAttack(attacker, startPos, MathUtils.getDirectionalVector(yaw, pitch), length, width, targetType);
    }

    private static TargetList lineAttack(LivingEntity attacker, Vec3d startPos, Vec3d direction, double length, double width, AttackTarget targetType) {
        int count = (int) Math.ceil(length / width);

        TargetList targets = TargetList.empty();
        for (int i = 0; i <= count; i++) {
            Vec3d pos = startPos.add(direction.multiply(i * width));
            targets = targets.include(cubeAttack(attacker, pos, width, targetType));
        }

        return targets;
    }

    public static void breakShield(LivingEntity target) {
        if(target.isBlocking() && target instanceof PlayerEntity playerEntity) playerEntity.disableShield();
    }

    public static boolean spawnAbility(AbstractAbilityPlacementEntity ability, LivingEntity owner) {
        return spawnAbility(ability, owner, false);
    }

    public static boolean spawnProjectile(AbstractAbilityProjectileEntity ability, LivingEntity owner) {
        owner.getWorld().spawnEntity(ability);
        return true;
    }

    public static boolean spawnAbility(AbstractAbilityPlacementEntity ability, LivingEntity owner, boolean onGround) {
        if(onGround) {
            BlockHitResult block = EntityUtils.raycastDown(ability, owner.getPos(), owner.getWorld(), 10);
            if(block.getType() == HitResult.Type.MISS) {
                return false;
            }

            ability.setPos(block.getPos().getX(), block.getPos().getY(), block.getPos().getZ());
        }

        owner.getWorld().spawnEntity(ability);
        return true;
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
