package net.rosemarythmye.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.entity.BlackPearlFireball;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.item.custom.EmberlashSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BoasFang extends UniqueSword {
    int skillCooldown = 300;

    public BoasFang(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if(attacker.getRandom().nextInt(100) <= 20) {
                target.addStatusEffect(new StatusEffectInstance(ModEffects.SUFFOCATION,120));
            }
        }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {

            for (int i = 0; i<10;i++) {
                float yaw = (float) Math.toRadians(user.getYaw()+90 + user.getRandom().nextInt(61) - 30);
                float pitch = (float) Math.toRadians(user.getPitch() + user.getRandom().nextInt(61) - 30);

                float velocityX = (float) (Math.cos(yaw) * Math.cos(pitch)) * 0.5f;
                float velocityZ = (float) (Math.sin(yaw) * Math.cos(pitch)) * 0.5f;
                float velocityY = (float) Math.sin(pitch) * -0.5f;

                for(int j = 0; j<14;j++) {
                    double dX = velocityX * j;
                    double dY = velocityY * j;
                    double dZ = velocityZ * j;

                    double x = user.getX();
                    double y = user.getEyeY();
                    double z = user.getZ();

                    DustParticleEffect particleEffect = new DustParticleEffect(new Vector3f(0.05f,1f,0.1f),1);

                    ((ServerWorld) user.getWorld()).spawnParticles(particleEffect,x+dX,y+dY,z+dZ,1,0,0,0,0);
                    user.getWorld().playSound(null,user.getBlockPos(), SoundEvents.ENTITY_LLAMA_SPIT,SoundCategory.PLAYERS,1f,0f);
                    for (LivingEntity entity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(x-0.25+dX,y-0.25+dY,z-0.25+dZ,x+0.25+dX,y+0.25+dY,z+0.25+dZ)))
                    {
                        if(entity.isTeammate(user) || entity == user || entity.isInvulnerable()) continue;

                        entity.damage(user.getDamageSources().playerAttack(user),2);
                        entity.setVelocity(velocityX/2,velocityY/2,velocityZ/2);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,100,0));
                    }
                }
                user.setVelocity(user.getRotationVector().negate().multiply(2));
                user.setVelocity(user.getVelocity().x, 0.0, user.getVelocity().z);
                user.velocityModified = true;
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,80,1));
            }


            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.boas_fang.tooltip6").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.SPORE_BLOSSOM_AIR, ParticleTypes.SPORE_BLOSSOM_AIR, ParticleTypes.SPORE_BLOSSOM_AIR, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
