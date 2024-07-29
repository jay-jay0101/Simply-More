package net.rosemarythmye.simplymore.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.rosemarythmye.simplymore.item.uniques.BladeOfTheGrotesque;
import net.sweenus.simplyswords.registry.SoundRegistry;

import java.util.UUID;

public class MistyEffect extends StatusEffect {


    public MistyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }


    public StatusEffect addAttributeModifier(EntityAttribute attribute, String uuid, double amount, EntityAttributeModifier.Operation operation) {
        EntityAttributeModifier entityAttributeModifier = new EntityAttributeModifier(UUID.fromString(uuid), this::getTranslationKey, amount, operation);
        this.getAttributeModifiers().put(attribute, entityAttributeModifier);
        return this;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int Amplifier) {
        int duration = entity.getStatusEffect(this).getDuration();

        if(duration>=9960 && duration<9990 && !entity.getWorld().isClient && entity instanceof PlayerEntity player) {
            float yaw = (float) Math.toRadians(player.getYaw()+90);
            float pitch = (float) Math.toRadians(player.getPitch());

            float dX = (float) (Math.cos(yaw) * Math.cos(pitch));
            float dZ = (float) (Math.sin(yaw) * Math.cos(pitch));
            float dY = (float) Math.sin(pitch) * -1f;
            LivingEntity teleportTarget = null;
            for(int i = 10;i>0;i--) {
                double x = player.getX() + (dX*i);
                double y = player.getY() + (dY*i);
                double z = player.getZ() + (dZ*i);
                for(LivingEntity target : player.getEntityWorld().getNonSpectatingEntities(LivingEntity.class,new Box(x-1.3,y-1.3,z-1.3,x+1.3,y+1.3,z+1.3))) {
                    if(!player.canSee(target)) continue;
                    if(target==player || target.isTeammate(player)) continue;
                    if(teleportTarget == null) teleportTarget = target;

                    int amplifier = (int) Math.ceil(target.getMaxHealth()/5);
                    if(amplifier>20) amplifier=20;
                    amplifier--;
                    target.addStatusEffect(new StatusEffectInstance(ModEffects.WITHERING_FATE,600,amplifier));
                    target.damage(player.getDamageSources().playerAttack(player),8);
                }
            }

            if(teleportTarget != null) {
                player.removeStatusEffect(this);
                double dXYZ = Math.sqrt(Math.pow(player.getX()-teleportTarget.getX(),2)+Math.pow(player.getY()-teleportTarget.getY(),2)+Math.pow(player.getZ()-teleportTarget.getZ(),2));
                dXYZ /= 10;
                player.sendMessage(Text.of(String.valueOf(dXYZ)));
                for(int i = 0;i<10;i++) {
                    double x = player.getX() + (dX*i*dXYZ);
                    double y = player.getY() + (dY*i*dXYZ);
                    double z = player.getZ() + (dZ*i*dXYZ);
                    ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK,x,y,z,10,0.2,0.2,0.2,0.2);
                    player.getWorld().playSound(null,x,y,z, SoundRegistry.DARK_SWORD_ATTACK_01.get(), SoundCategory.PLAYERS,1,1);
                }
                player.teleport(teleportTarget.getX(),teleportTarget.getY(),teleportTarget.getPos().getZ());
            }

        }

        if(entity.isOnGround() && duration<9980) entity.removeStatusEffect(this);

        if(!entity.getWorld().isClient) ((ServerWorld) entity.getWorld()).spawnParticles(ParticleTypes.LARGE_SMOKE,entity.getX(),entity.getY()+0.5,entity.getZ(),5,0.5,0.5,0.5,0);


        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,5));
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY,5));
        super.applyUpdateEffect(entity, Amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
