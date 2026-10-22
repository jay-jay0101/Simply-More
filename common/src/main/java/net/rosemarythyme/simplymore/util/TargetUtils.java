package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.item.custom.StealSwordItem;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

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

    public static Optional<Entity> findTarget(LivingEntity attacker, float range, Predicate<Entity> predicate) {
        if(attacker instanceof PlayerEntity player) {
            return findLenientTarget(player, range, predicate);
        } else if (attacker instanceof MobEntity mob) {
            return Optional.ofNullable(mob.getTarget());
        }

        return Optional.empty();
    }

    private static Optional<Entity> findLenientTarget(PlayerEntity player, double range, Predicate<Entity> targetPredicate) {
        if(player == null || targetPredicate == null) return Optional.empty();

        Vec3d origin = player.getEyePos();
        Vec3d end = origin.add(player.getRotationVec(1.0F).normalize().multiply(range));
        Box searchBox = player.getBoundingBox().stretch(player.getRotationVec(1.0F).normalize().multiply(range)).expand(range);
        Optional<Entity> closestTarget = Optional.empty();
        double closestDistance = range * range;

        for(Entity entity : player.getWorld().getOtherEntities(player, searchBox, targetPredicate)) {
            Optional<Vec3d> hitPos = entity.getBoundingBox().expand(0.75f).raycast(origin, end);
            if (hitPos.isPresent()) {
                double distance = origin.squaredDistanceTo(hitPos.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestTarget = Optional.of(entity);
                }
            }
        }

        return closestTarget;
    }


    public static Optional<LivingEntity> findLivingTarget(LivingEntity attacker, float range, TargetType type) {
        if(attacker instanceof PlayerEntity player) {
            return Optional.ofNullable(StealSwordItem.findLenientTarget(player, range, PredicateUtils.createForLivingTargetType(attacker, type)));
        } else if (attacker instanceof MobEntity mob) {
            return Optional.ofNullable(mob.getTarget());
        }

        return Optional.empty();
    }

    public static TargetList cylinderSelect(LivingEntity attacker, Vec3d centerPos, double horizontalRange, double verticalRange, TargetType targetType) {
        double horizontalDistance = horizontalRange * horizontalRange;

        return cuboidSelect(attacker, centerPos, horizontalRange, verticalRange, targetType).filter(
                (target) -> target.squaredDistanceTo(new Vec3d(centerPos.x, target.getY(), centerPos.z)) < horizontalDistance
        );
    }

    public static TargetList cuboidSelect(LivingEntity attacker, Vec3d centerPos, double horizontalRange, double verticalRange, TargetType targetType) {
        Box box = MathUtils.createCuboidBox(centerPos, horizontalRange, verticalRange, horizontalRange);
        return boxSelect(attacker, box, targetType);
    }

    public static TargetList cubeSelect(LivingEntity attacker, Vec3d centerPos, double range, TargetType targetType) {
        Box box = MathUtils.createCubeBox(centerPos, range);
        return boxSelect(attacker, box, targetType);
    }

    public static TargetList boxSelect(LivingEntity attacker, Box box, TargetType targetType) {
        if (attacker == null) return TargetList.empty();

        List<LivingEntity> targets = attacker.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

        return new TargetList(Set.copyOf(targets)).filter(
                (target) -> canTarget(attacker, target, targetType)
        );
    }

    public static TargetList lineSelect(LivingEntity attacker, Vec3d startPos, Vec3d endPos, double width, TargetType targetType) {
        Vec3d delta = endPos.subtract(startPos);
        return lineSelect(attacker, startPos, delta.normalize(), delta.length(), width, targetType);
    }

    public static TargetList lineSelect(LivingEntity attacker, Vec3d startPos, float yaw, float pitch, double length, double width, TargetType targetType) {
        return lineSelect(attacker, startPos, MathUtils.getDirectionalVector(yaw, pitch), length, width, targetType);
    }

    private static TargetList lineSelect(LivingEntity attacker, Vec3d startPos, Vec3d direction, double length, double width, TargetType targetType) {
        int count = (int) Math.ceil(length / width);

        TargetList targets = TargetList.empty();
        for (int i = 0; i <= count; i++) {
            Vec3d pos = startPos.add(direction.multiply(i * width));
            targets = targets.include(cubeSelect(attacker, pos, width, targetType));
        }

        return targets;
    }

    public static LivingEntity getEntityByUUID(LivingEntity anchor, UUID uuid) {
        if(uuid == null) return null;

        return anchor.getWorld().getNonSpectatingEntities(LivingEntity.class, MathUtils.createCubeBox(anchor.getPos(), 120f))
                .stream().filter((e) -> e.getUuid().equals(uuid)).findAny().orElse(null);
    }

    public static LivingEntity getEntityByUUID(MinecraftServer server, UUID uuid) {
        if(uuid == null) return null;

        for (ServerWorld world : server.getWorlds()) {
            Entity entity = world.getEntity(uuid);
            if (entity instanceof LivingEntity target) return target;
        }

        return null;
    }
}
