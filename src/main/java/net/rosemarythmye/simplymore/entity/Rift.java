package net.rosemarythmye.simplymore.entity;

import com.google.common.base.Predicates;
import com.ibm.icu.text.MessagePattern;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.List;

public class Rift extends AreaEffectCloudEntity {

    int color;

    Vector3f[] colours = new Vector3f[] {
            new Vector3f(1,1,1),
            new Vector3f(1,0.667f,0),
            new Vector3f(1,0.333f,1),
            new Vector3f(0.333f,1,1),
            new Vector3f(1,1,0.333f),
            new Vector3f(0.333f,1,0.333f),
            new Vector3f(1,0.75f,0.8f),
            new Vector3f(1,0.333f,0.333f),
            new Vector3f(0.667f,0.667f,0.667f),
            new Vector3f(0,0.667f,0.667f),
            new Vector3f(0.667f,0,0.667f),
            new Vector3f(0.333f,0.333f,1),
            new Vector3f(0.667f,0.333f,0),
            new Vector3f(0,0.667f,0),
            new Vector3f(0.667f,0,0),
            new Vector3f(0,0,0)
    };
    public Rift(World world, double x, double y, double z, LivingEntity owner, int color) {
        super(world, x, y, z);
        this.setParticleType(ParticleTypes.ASH);
        this.setRadius(0.25f);
        this.setRadiusGrowth(0);
        this.setRadiusOnUse(0);
        this.setOwner(owner);
        this.setDuration(300);
        this.color = color;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.getOwner() == null || this.getWorld().getDimension()!=this.getOwner().getWorld().getDimension()) this.discard();

        double dXOwner = this.getOwner().getX()-this.getX();
        double dYOwner = this.getOwner().getY()-this.getY();
        double dZOwner = this.getOwner().getZ()-this.getZ();


        if(Math.pow(dXOwner,2)+Math.pow(dYOwner,2)+Math.pow(dZOwner,2)>225) this.discard();

        DustParticleEffect bigParticle = new DustParticleEffect(colours[this.color],3);
        DustParticleEffect smallParticle = new DustParticleEffect(colours[this.color],1);
        ((ServerWorld) this.getWorld()).spawnParticles(bigParticle,this.getX(),this.getY(),this.getZ(),2,0.2,0.2,0.2,0.3);
        for(LivingEntity entity : this.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(this.getX()-13,this.getY()-13,this.getZ()-13,this.getX()+13,this.getY()+13,this.getZ()+13))) {
            if (entity == this.getOwner() || entity.isTeammate(this.getOwner())) continue;
            double dX = this.getX()-entity.getX();
            double dY = this.getY()-entity.getY();
            double dZ = this.getZ()-entity.getZ();

            if(Math.pow(dX,2)+Math.pow(dY,2)+Math.pow(dZ,2)<100) {
                dX /= -20;
                dY /= -20;
                dZ /= -20;
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,10));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,10,1));

                for(int i = 0; i<20;i++) {
                    ((ServerWorld) this.getWorld()).spawnParticles(smallParticle,this.getX()+(dX*i),this.getY()+(dY*i),this.getZ()+(dZ*i),1,0,0,0,0);
                }
            } else {
                entity.setVelocity(dX/10,dY/10,dZ/10);
                if (entity instanceof PlayerEntity player) player.velocityModified = true;
            }


        }
    }

}
