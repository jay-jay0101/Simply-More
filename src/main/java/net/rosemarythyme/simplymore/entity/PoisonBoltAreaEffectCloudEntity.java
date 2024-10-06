package net.rosemarythyme.simplymore.entity;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.joml.Vector3f;

public class PoisonBoltAreaEffectCloudEntity extends AreaEffectCloudEntity {

    int version;
    int time = 0;
    static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;
    final DustParticleEffect particleEffect = new DustParticleEffect(new Vector3f(0f, 0.6f, 0.2f), 1);
    LivingEntity target;
    double distance;

    public PoisonBoltAreaEffectCloudEntity(World world, double x, double y, double z, LivingEntity owner, int version) {
        super(world, x, y, z);
        SimplyMoreHelperMethods.simplyMore$setAreaEffectCloudParameters(this, ParticleTypes.ASH, 0.25f, 0, 0, owner, 21);
        this.version = version;
    }

    @Override
    public void tick() {
        super.tick();

        time++;

        // Check if it's time to find a new target
        if (time >= 20) {
            judder();
        }

        // Spawn particles at the current position
        ((ServerWorld) this.getWorld()).spawnParticles(particleEffect, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
    }

    public void judder() {
        // Get the owner of the entity
        LivingEntity owner = this.getOwner();
        if (owner == null) {
            return;
        }

        // Create a box to search for entities
        Box box = new Box(this.getX() - 50, this.getY() - 50, this.getZ() - 50, this.getX() + 50, this.getY() + 50, this.getZ() + 50);
        target = null;
        distance = 50;

        // Find the closest entity in the box
        for (LivingEntity livingEntity : this.getWorld().getNonSpectatingEntities(LivingEntity.class, box)) {
            if (livingEntity == owner || livingEntity.isTeammate(owner)) {
                continue;
            }

            double entityDistance = livingEntity.distanceTo(this);
            if (entityDistance > distance) {
                continue;
            }

            target = livingEntity;
            distance = entityDistance;
        }

        // Check if the version is less than 0
        if (version < 0) {
            target = null;
        }

        // Check if a target was found
        if (target == null) {
            // Create a new entity to replace this one
            PoisonBoltAreaEffectCloudEntity replacement = new PoisonBoltAreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), owner, this.version + 1);
            this.getWorld().spawnEntity(replacement);
            this.discard();
            return;
        }

        // Check if the version is greater than or equal to 10 and the distance is greater than 3
        if (version >= effect.getPoisonBoltLifespan() && distance > 3) {
            return;
        }

        // Check if the distance is greater than 3
        if (distance > 3) {
            // Look at the target
            this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, target.getPos());

            // Calculate the velocity of the entity
            float VelocityPower = 3;
            float yaw = (float) Math.toRadians(this.getYaw() + 90);
            float pitch = (float) Math.toRadians(this.getPitch());

            float horizontalVelocity = (float) (Math.cos(yaw) * Math.cos(pitch)) * VelocityPower;
            float verticalVelocity = (float) Math.sin(pitch) * -VelocityPower;
            float depthVelocity = (float) (Math.sin(yaw) * Math.cos(pitch)) * VelocityPower;

            // Spawn particles along the trajectory
            for (int i = 1; i < 7; i++) {
                ((ServerWorld) this.getWorld()).spawnParticles(particleEffect, this.getX() + (horizontalVelocity / i), this.getY() + (verticalVelocity / i), this.getZ() + (depthVelocity / i), 1, 0, 0, 0, 0);
            }

            // Create a new entity to replace this one
            PoisonBoltAreaEffectCloudEntity replacement = new PoisonBoltAreaEffectCloudEntity(this.getWorld(), this.getX() + horizontalVelocity, this.getY() + verticalVelocity, this.getZ() + depthVelocity, owner, this.version + 1);

            this.getWorld().spawnEntity(replacement);
            this.discard();
            this.getWorld().playSound(null, this.getBlockPos(), SoundRegistry.DARK_SWORD_ATTACK_03.get(), SoundCategory.PLAYERS, 0.25f, 1);
        } else {
            // Damage the target
            target.damage(owner.getDamageSources().magic(), effect.getPoisonBoltDamage());
            target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.VENOM, effect.getPoisonVenomTime()));
            this.discard();
            this.getWorld().playSound(null, this.getBlockPos(), SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_01.get(), SoundCategory.PLAYERS, 0.4f, 1);
        }
    }
}
