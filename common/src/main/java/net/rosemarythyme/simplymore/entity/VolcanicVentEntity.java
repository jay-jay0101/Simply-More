package net.rosemarythyme.simplymore.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MagmaseepItem;
import net.rosemarythyme.simplymore.registry.EntityRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.jetbrains.annotations.NotNull;

public class VolcanicVentEntity extends AbstractCollidableAbilityEntity {
    int timeSteppedOn = 0;

    public VolcanicVentEntity(EntityType<VolcanicVentEntity> entityType,  World world) {
        super(entityType, world);
    }

    public VolcanicVentEntity(@NotNull LivingEntity owner, Vec3d position) {
        super(owner, position, EntityRegistry.VOLCANIC_VENT.get());
    }

    @Override
    public int getLifespan() {
        return MagmaseepItem.SETTINGS.ventDuration;
    }

    private void visual(Boolean isFlowBlocked, float range) {
        if(!isFlowBlocked) {
            AudioVisualUtils.particleAroundEntity(this, ParticleTypes.LAVA, 4, 0f, 0f);
            AudioVisualUtils.rainParticlesAboveEntity(this, ParticleTypes.FALLING_LAVA, 40, range, 5.5d, 0f);

            if(age % 8 == 0) {
                AudioVisualUtils.playSound(getServerWorld(), getPos(), new Sound(SoundEvents.ITEM_FIRECHARGE_USE, 0.3f, this.getRandom().nextFloat()));
                AudioVisualUtils.playSound(getServerWorld(), getPos(), new Sound(SoundEvents.BLOCK_LAVA_AMBIENT, 0.3f, this.getRandom().nextFloat()));
            }
        }

        AudioVisualUtils.rainParticlesAboveEntity(this, ParticleTypes.LARGE_SMOKE, 60, range, 5.5d, 0f);

        if(timeSteppedOn == 1) {
            AudioVisualUtils.playSound(getServerWorld(), getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_03.get()).setPitch(0f));
        }
    }

    @Override
    protected void serverTick(LivingEntity owner) {
        TargetList blockingFlow = AttackUtils.cubeAttack(owner, this.getPos().offset(Direction.UP, 0.5f), 0.3f, AttackUtils.AttackTarget.ENEMIES);
        float range = MathUtils.clampedLerp(age, 0, 30, 0, MagmaseepItem.SETTINGS.lavaRainRange);

        visual(blockingFlow.isPopulated(), range);

        if(blockingFlow.isEmpty()) {
            lavaRain(owner, range);
        } else {
            tryExplode(owner, blockingFlow);
        }

        spreadLava(owner);
    }

    private void spreadLava(LivingEntity owner) {
        if(age == 1) {
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos(), 0.2f), owner);
        }

        if(age == 10) {
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.NORTH, 1), 0.13f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.EAST, 1), 0.13f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.WEST, 1), 0.13f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.SOUTH, 1), 0.13f), owner);
        }

        if(age == 20) {
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().add(1, 0, 1), 0.06f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().add(-1, 0, 1), 0.06f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().add(1, 0, -1), 0.06f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().add(-1, 0, -1), 0.06f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.NORTH, 2), 0.06f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.EAST, 2), 0.06f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.WEST, 2), 0.06f), owner);
            AttackUtils.spawnAbility(new LavaLiquidEntity(owner, this.getPos().offset(Direction.SOUTH, 2), 0.06f), owner);
        }
    }

    private void lavaRain(LivingEntity owner, float range) {
        AttackUtils.cylinderAttack(owner, this.getPos(), range, 5, AttackUtils.AttackTarget.ENEMIES)
                .damage(AttackUtils.scaleDamage("fire", owner, 0, 1, MagmaseepItem.SETTINGS.lavaRainDamage), this.getDamageSources().inFire())
                .setOnFireFor(2);

        timeSteppedOn = 0;
    }

    private void tryExplode(LivingEntity owner, TargetList blockingFlow) {
        timeSteppedOn++;

        if(timeSteppedOn > 25) {
            explode(owner, blockingFlow);
        }
    }

    private void explode(LivingEntity owner, TargetList standingOn) {
        ServerWorld world = getServerWorld();

        timeSteppedOn = 0;
        AttackUtils.cylinderAttack(owner, getPos(), MagmaseepItem.SETTINGS.lavaRainRange, 5, AttackUtils.AttackTarget.ENEMIES)
                .forceDamage(AttackUtils.scaleDamage("fire", owner, 0, 1, MagmaseepItem.SETTINGS.ventExplosionDamage), this.getDamageSources().inFire())
                .knockback(getPos(), MagmaseepItem.SETTINGS.ventExplosionKnockback);

        standingOn.addVelocity(0, 1.2f, 0);

        AudioVisualUtils.particleAroundEntity(this, ParticleTypes.EXPLOSION, 1, 0, 0);
        AudioVisualUtils.playSound(world, this.getPos(), new Sound(SoundEvents.ENTITY_GENERIC_EXPLODE.value()).setPitch(0.8f));
        AudioVisualUtils.applyScreenshake(world, this.getPos(), owner, 15, 3f, 10);
    }

    @Override
    public int getOutroTicks() {
        return 20;
    }

    @Override
    public int getIntroTicks() {
        return 20;
    }
}
