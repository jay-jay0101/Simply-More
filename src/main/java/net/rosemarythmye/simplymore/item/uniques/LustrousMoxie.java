package net.rosemarythmye.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class LustrousMoxie extends UniqueSword {
    int skillCooldown = 400;

    public LustrousMoxie(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if(target.hasStatusEffect(ModEffects.RADIANT_MARK)) {
                    target.damage(attacker.getDamageSources().magic(),target.getStatusEffect(ModEffects.RADIANT_MARK).getAmplifier()+1);
                }


                if (attacker.getRandom().nextInt(100) <= 20) {
                    if(target.hasStatusEffect(ModEffects.RADIANT_MARK)) {
                        int amplifier = target.getStatusEffect(ModEffects.RADIANT_MARK).getAmplifier()+1;
                        int duration = 240 - (amplifier*40);
                        target.addStatusEffect(new StatusEffectInstance(ModEffects.RADIANT_MARK, duration, amplifier), attacker);
                    } else {
                        target.addStatusEffect(new StatusEffectInstance(ModEffects.RADIANT_MARK, 200, 0), attacker);
                    }
                }
            }
        return super.postHit(stack, target, attacker);
    }



    public void attack(World world, PlayerEntity user) {
        if (!user.getWorld().isClient()) {
            boolean use = false;
            LivingEntity target = null;
            for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(user.getX()-20,user.getY()-20,user.getZ()-20,user.getX()+20,user.getY()+20,user.getZ()+20))) {
                if(livingEntity == user || livingEntity.isTeammate(user) || use) continue;
                if (!livingEntity.hasStatusEffect(ModEffects.RADIANT_MARK)) continue;
                target = livingEntity;
                target.removeStatusEffect(ModEffects.RADIANT_MARK);
                use = true;
                livingEntity.damage(user.getDamageSources().playerAttack(user),6);

                double xVelocity = livingEntity.getX()-user.getX();
                double zVelocity = livingEntity.getZ()-user.getZ();
                double ratioMax = Math.abs(xVelocity)+ Math.abs(zVelocity);
                float strength = 2.5f;

                xVelocity *= strength/ratioMax;
                zVelocity *= strength/ratioMax;

                livingEntity.setVelocity(xVelocity,0.2,zVelocity);

            }

            if(use) {
                user.teleport(target.getX(),target.getY(),target.getZ());
                ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.WAX_OFF,user.getX(),user.getY()+2,user.getZ(),500,3,3,3,0);


                for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(user.getX()-4,user.getY()-4,user.getZ()-4,user.getX()+4,user.getY()+4,user.getZ()+4))) {
                    if(livingEntity == user || livingEntity.isTeammate(user) || livingEntity == target) continue;
                    livingEntity.damage(user.getDamageSources().playerAttack(user),6);

                    double xVelocity = livingEntity.getX()-user.getX();
                    double zVelocity = livingEntity.getZ()-user.getZ();
                    double ratioMax = Math.abs(xVelocity)+ Math.abs(zVelocity);
                    float strength = 2.5f;

                    xVelocity *= strength/ratioMax;
                    zVelocity *= strength/ratioMax;

                    livingEntity.setVelocity(xVelocity,0.2,zVelocity);
                }

                user.addStatusEffect(new StatusEffectInstance(ModEffects.STUNNED,30,0));
                user.getWorld().playSound(null,user.getBlockPos(),SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_01.get(), SoundCategory.PLAYERS);

                user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            }
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }


    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!user.getWorld().isClient && user instanceof PlayerEntity player) {
             if(remainingUseTicks==1) attack(world, player);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 15;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip6").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip8").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip9").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.lustrous_moxie.tooltip10").setStyle(TEXT));

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.WAX_OFF, ParticleTypes.WAX_OFF, ParticleTypes.WAX_OFF, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
