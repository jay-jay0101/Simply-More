package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;
import java.util.Set;

public class TargetUtils {
    public enum TargetType {
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

        TargetType(boolean canHitUser, boolean canHitAllies, boolean canHitEnemies, boolean canHitPetOrMount) {
            this.canHitAllies = canHitAllies;
            this.canHitEnemies = canHitEnemies;
            this.canHitUser = canHitUser;
            this.canHitPetOrMount = canHitPetOrMount;
        }
    }

    public static boolean canTarget(LivingEntity attacker, LivingEntity target, TargetType targetType) {
        if(attacker == null || target == null) return false;
        if(EntityUtils.isUntargetable(target)) return false;

        if(!target.canHit()) return false;

        if(!targetType.canHitUser && attacker == target) return false;

        boolean isEnemy = HelperMethods.checkFriendlyFire(target, attacker);
        if(!targetType.canHitEnemies && isEnemy) return false;
        if(!targetType.canHitAllies && !isEnemy) return false;

        return targetType.canHitPetOrMount || !EntityUtils.isPetOrMount(attacker, target);
    }


    public static TargetList cylinderAttack(LivingEntity attacker, Vec3d centerPos, double horizontalRange, double verticalRange, TargetType targetType) {
        double horizontalDistance = horizontalRange * horizontalRange;

        return cuboidAttack(attacker, centerPos, horizontalRange, verticalRange, targetType).filter(
                (target) -> target.squaredDistanceTo(new Vec3d(centerPos.x, target.getY(), centerPos.z)) < horizontalDistance
        );
    }

    public static LivingEntity getTargetedEntity(LivingEntity attacker, float range, TargetType targetType) {
        Entity entity = HelperMethods.getTargetedEntity(attacker, range);
        if(!(entity instanceof LivingEntity target)) return null;
        if(canTarget(attacker, target, targetType)) return target;

        return null;
    }

    public static TargetList cuboidAttack(LivingEntity attacker, Vec3d centerPos, double horizontalRange, double verticalRange, TargetType targetType) {
        Box box = MathUtils.createCuboidBox(centerPos, horizontalRange, verticalRange, horizontalRange);
        return boxAttack(attacker, box, targetType);
    }

    public static TargetList cubeAttack(LivingEntity attacker, Vec3d centerPos, double range, TargetType targetType) {
        Box box = MathUtils.createCubeBox(centerPos, range);
        return boxAttack(attacker, box, targetType);
    }

    public static TargetList boxAttack(LivingEntity attacker, Box box, TargetType targetType) {
        if (attacker == null) return TargetList.empty();

        List<LivingEntity> targets = attacker.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

        return new TargetList(Set.copyOf(targets)).filter(
                (target) -> canTarget(attacker, target, targetType)
        );
    }

    public static TargetList lineAttack(LivingEntity attacker, Vec3d startPos, Vec3d endPos, double width, TargetType targetType) {
        Vec3d delta = endPos.subtract(startPos);
        return lineAttack(attacker, startPos, delta.normalize(), delta.length(), width, targetType);
    }

    public static TargetList lineAttack(LivingEntity attacker, Vec3d startPos, float yaw, float pitch, double length, double width, TargetType targetType) {
        return lineAttack(attacker, startPos, MathUtils.getDirectionalVector(yaw, pitch), length, width, targetType);
    }

    private static TargetList lineAttack(LivingEntity attacker, Vec3d startPos, Vec3d direction, double length, double width, TargetType targetType) {
        int count = (int) Math.ceil(length / width);

        TargetList targets = TargetList.empty();
        for (int i = 0; i <= count; i++) {
            Vec3d pos = startPos.add(direction.multiply(i * width));
            targets = targets.include(cubeAttack(attacker, pos, width, targetType));
        }

        return targets;
    }
}
