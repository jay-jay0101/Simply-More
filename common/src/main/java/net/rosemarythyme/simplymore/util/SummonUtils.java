package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.rosemarythyme.simplymore.entity.AbstractAbilityPlacementEntity;
import net.rosemarythyme.simplymore.entity.EnumeratedEntity;
import net.rosemarythyme.simplymore.entity.projectiles.AbstractAbilityProjectileEntity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class SummonUtils {
    public static <T extends AbstractAbilityPlacementEntity> List<T> getOwnedAbilities(LivingEntity owner, Class<T> clazz) {
        return owner.getWorld().getNonSpectatingEntities(clazz, MathUtils.createCubeBox(owner.getPos(), 120))
                .stream().filter((e) -> owner.getUuid().equals(e.getOwnerUUID().orElse(null))).toList();
    }

    public static <T extends AbstractAbilityProjectileEntity> List<T> getOwnedProjectiles(LivingEntity owner, Class<T> clazz) {
        return owner.getWorld().getNonSpectatingEntities(clazz, MathUtils.createCubeBox(owner.getPos(), 120))
                .stream().filter((e) -> e.getOwner() instanceof LivingEntity o && owner.getUuid().equals(o.getUuid())).toList();
    }

    public static <T extends LivingEntity & Ownable> List<T> getOwnedEntities(LivingEntity owner, Class<T> clazz) {
        return owner.getWorld().getNonSpectatingEntities(clazz, MathUtils.createCubeBox(owner.getPos(), 120))
                .stream().filter((e) -> e.getOwner() instanceof LivingEntity o && owner.getUuid().equals(o.getUuid())).toList();
    }

    public static <T extends Entity & EnumeratedEntity & Ownable> void ensureEnumeratedEntities(LivingEntity owner, Class<T> clazz, int number) {
        List<T> entities = owner.getWorld().getEntitiesByClass(clazz, MathUtils.createCubeBox(owner.getPos(), 120), (e) -> e.getOwner() instanceof LivingEntity eOwner && owner.getUuid().equals(eOwner.getUuid()) && e.shouldEnumerate());
        List<T> outOfRangeEntities = new ArrayList<>();
        List<Integer> ordinals = new ArrayList<>();

        for (T entity : entities) {
            int ordinal = entity.getOrdinal();

            if(ordinal >= number) {
                outOfRangeEntities.add(entity);
            } else {
                ordinals.add(ordinal);
            }
        }

        try {
            Constructor<T> constructor = clazz.getConstructor(LivingEntity.class, int.class);

            for (int i = 0; i < number; i++) {
                if(ordinals.contains(i)) continue;

                if(!outOfRangeEntities.isEmpty()) {
                    outOfRangeEntities.getFirst().setOrdinal(i);
                } else {
                    T entity = constructor.newInstance(owner, i);
                    owner.getWorld().spawnEntity(entity);
                }

                ordinals.add(i);
            }

            outOfRangeEntities.forEach(Entity::discard);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean spawnAbility(AbstractAbilityPlacementEntity ability, LivingEntity owner) {
        return spawnAbility(ability, owner, false);
    }

    public static boolean spawnProjectile(AbstractAbilityProjectileEntity ability, LivingEntity owner) {
        return owner.getWorld().spawnEntity(ability);
    }

    public static boolean spawnAbility(AbstractAbilityPlacementEntity ability, LivingEntity owner, boolean onGround) {
        if(onGround) {
            BlockHitResult block = EntityUtils.raycastDown(ability, ability.getPos(), owner.getWorld(), 10);
            if(block.getType() == HitResult.Type.MISS) {
                return false;
            }

            ability.setPos(block.getPos().getX(), block.getPos().getY(), block.getPos().getZ());
        }

        owner.getWorld().spawnEntity(ability);
        return true;
    }
}
