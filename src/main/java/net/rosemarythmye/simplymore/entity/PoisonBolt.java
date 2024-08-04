package net.rosemarythmye.simplymore.entity;

import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.sweenus.simplyswords.registry.SoundRegistry;
import org.joml.Vector3f;

public class PoisonBolt extends AreaEffectCloudEntity {

    private final int version;
    private int time = 0;
    private final DustParticleEffect particleEffect = new DustParticleEffect(new Vector3f(0f,0.6f,0.2f),1);

    public PoisonBolt(World world, double x, double y, double z, LivingEntity owner, int version) {
        super(world, x, y, z);
        this.setParticleType(ParticleTypes.ASH);
        this.setRadius(0.25f);
        this.setRadiusGrowth(0);
        this.setRadiusOnUse(0);
        this.setOwner(owner);
        this.setDuration(21);
        this.version = version;
    }

    @Override
    public void tick() {
        super.tick();

        time++;

        if(time>=20) jutter();

        ((ServerWorld) this.getWorld()).spawnParticles(particleEffect,this.getX(),this.getY(),this.getZ(),1,0,0,0,0);
    }

    public void jutter() {

        LivingEntity target = null;
        double distance = 50;

        for (LivingEntity livingEntity : this.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(this.getX()-50,this.getY()-50,this.getZ()-50,this.getX()+50,this.getY()+50,this.getZ()+50))) {
            if(this.getOwner() == null || livingEntity == this.getOwner() || livingEntity.isTeammate(this.getOwner())) continue;
            if(livingEntity.distanceTo(this) > distance) continue;

            target = livingEntity;
            distance = livingEntity.distanceTo(this);
        }

        if(this.version>=10 && distance>3) {
            return;
        }

        if(this.version<0) target = null;

        if(target==null) {
            PoisonBolt replacement = new PoisonBolt(this.getWorld(),this.getX(),this.getY(),this.getZ(),this.getOwner(),this.version+1);
            this.getWorld().spawnEntity(replacement);
            this.discard();
            return;
        };


        if (distance>3) {


            this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET,target.getPos());

            float VelocityPower = 3;
            float yaw = (float) Math.toRadians(this.getYaw()+90);
            float pitch = (float) Math.toRadians(this.getPitch());

            float xVelocity = (float) (Math.cos(yaw) * Math.cos(pitch)) * VelocityPower;
            float zVelocity = (float) (Math.sin(yaw) * Math.cos(pitch)) * VelocityPower;
            float yVelocity = (float) Math.sin(pitch) * -VelocityPower;


            for(int i = 1; i<7; i++) {
                ((ServerWorld) this.getWorld()).spawnParticles(particleEffect,this.getX() + (xVelocity/i),this.getY() + (yVelocity/i),this.getZ() + (zVelocity/i),1,0,0,0,0);
            }

            PoisonBolt replacement = new PoisonBolt(this.getWorld(),this.getX()+xVelocity,this.getY()+yVelocity,this.getZ()+zVelocity,this.getOwner(),this.version+1);

            this.getWorld().spawnEntity(replacement);
            this.discard();
            this.getWorld().playSound(null,this.getBlockPos(), SoundRegistry.DARK_SWORD_ATTACK_03.get(), SoundCategory.PLAYERS,0.25f,1);

        } else {
            target.damage(this.getOwner().getDamageSources().magic(),2);
            target.addStatusEffect(new StatusEffectInstance(ModEffects.VENOM,160));
            this.discard();
            this.getWorld().playSound(null,this.getBlockPos(), SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_01.get(), SoundCategory.PLAYERS,0.4f,1);
        }
    }

}
