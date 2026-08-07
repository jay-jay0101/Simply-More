package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.StasisItem;
import net.rosemarythyme.simplymore.registry.DamageTypeRegistry;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import org.jetbrains.annotations.NotNull;

public class LightningPointEntity extends AbstractVisibleAbilityEntity {
    public int delay;

    public LightningPointEntity(EntityType<LightningPointEntity> entityType, World world) {
        super(entityType, world);
    }

    public LightningPointEntity(@NotNull LivingEntity owner, Vec3d position, int delay) {
        super(owner, position, EntityRegistry.LIGHTNING_POINT.get());
        this.delay = delay;
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        ServerWorld world = getServerWorld();

        int offsetAge = age + getIntroTicks();
        if(offsetAge <= getLifespan()) {
            Vec3d offset = MathUtils.getDirectionalVector(owner.getYaw(), 0);
            double strength = (delay + 1) * ((StasisItem.SETTINGS.radius * 2) + 0.75f);
            Vec3d pos = owner.getPos().add(offset.multiply(strength));

            this.setPosition(EntityUtils.raycastDown(this, pos, world, 20).getPos());
        } else if (offsetAge == getLifespan() + getOutroTicks()) {
            for (int i = 0; i < 10; i++) {
                float yaw = getRandom().nextFloat() * 360f;
                float offsetRadius = (float) (getRandom().nextFloat() * StasisItem.SETTINGS.radius);

                Vec3d offset = MathUtils.getDirectionalVector(yaw, 0).multiply(offsetRadius);

                Vec3d pos = getPos().add(offset);
                spawnLightning(world, pos, null);
            }

            AudioVisualUtils.applyScreenshake(world, getPos(), owner, 40, 2.4f, 10);

            AttackUtils.cylinderAttack(getOwner(), getPos().offset(Direction.UP, 50), StasisItem.SETTINGS.radius, 50, AttackUtils.AttackTarget.ENEMIES)
                    .forceDamage(AttackUtils.scaleDamage("lightning", owner, 0, 1, StasisItem.SETTINGS.strikeDamage), DamageTypeRegistry.damageSourceOf(world, DamageTypeRegistry.LIGHTNING))
                    .onEach((target) -> spawnLightning(world, target.getPos(), target));
            discard();
        }
    }

    private void spawnLightning(ServerWorld world, Vec3d pos, LivingEntity target) {
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
        if (lightning != null) {
            lightning.refreshPositionAfterTeleport(pos);
            lightning.setCosmetic(true);
            world.spawnEntity(lightning);

            if(target != null) {
                target.onStruckByLightning(world, lightning);
            }
        }
    }

    @Override
    public int getOutroTicks() {
        return (delay + 1) * StasisItem.SETTINGS.delay;
    }

    @Override
    public int getIntroTicks() {
        return 10;
    }

    @Override
    public int getLifespan() {
        return StasisItem.SETTINGS.strikeWindup;
    }
}
