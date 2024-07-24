package net.rosemarythmye.simplymore.entity;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.sweenus.simplyswords.item.custom.EnigmaSwordItem;

import java.util.List;

public class VipersCallProjectile extends AreaEffectCloudEntity {
    public VipersCallProjectile(World world, double x, double y, double z, LivingEntity owner) {
        super(world, x, y, z);
        this.setParticleType(ParticleTypes.ASH);
        this.setRadius(3.3f);
        this.setRadiusGrowth(0);
        this.setRadiusOnUse(0);
        this.setOwner(owner);
        this.setDuration(360);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity livingEntity = this.getOwner();
        List<LivingEntity> entities = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());
        for (LivingEntity target : entities) {
            if (target.isAlive() && !target.isInvulnerable() && target != livingEntity) {
                if (livingEntity == null) {
                    target.addStatusEffect(new StatusEffectInstance(ModEffects.VENOM,60,0));
                } else {
                    if (livingEntity.isTeammate(target)) {
                        return;
                    }
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,60,0));
                    target.damage(this.getDamageSources().magic(),1.2f);
                }
            }
        }
        float radius = this.getRadius();
        for(int i = 0; i < 30; i++) {
            double xPos = this.getX() + (Math.sin(Math.toRadians((double) 360 /30 * i))*radius);
            double zPos = this.getZ() + (Math.cos(Math.toRadians((double) 360 /30 * i))*radius);

            ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.COMPOSTER,xPos,this.getY(),zPos,3,0.1,0.1,0.1,0.1);
        }
    }

}
