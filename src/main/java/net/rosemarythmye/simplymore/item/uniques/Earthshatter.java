package net.rosemarythmye.simplymore.item.uniques;

import dev.architectury.platform.Mod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.effect.RadiantEffect;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class Earthshatter extends UniqueSword {
    int skillCooldown = 600;

    public Earthshatter(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (attacker.getRandom().nextInt(100) <= 15) {
                    if(target.hasStatusEffect(ModEffects.ARMOUR_CRUNCH)) {
                        int amplifier = target.getStatusEffect(ModEffects.ARMOUR_CRUNCH).getAmplifier()+1;
                        target.addStatusEffect(new StatusEffectInstance(ModEffects.ARMOUR_CRUNCH, 200, amplifier), attacker);
                    } else {
                        target.addStatusEffect(new StatusEffectInstance(ModEffects.ARMOUR_CRUNCH, 200, 0), attacker);
                    }
                }
            }
        return super.postHit(stack, target, attacker);
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
            if(remainingUseTicks==this.getMaxUseTime(null)-1) user.getWorld().playSound(null,user.getBlockPos(),SoundRegistry.DARK_SWORD_ENCHANT.get(), user.getSoundCategory(),1.0f,1.2f);
            if(remainingUseTicks==1) attack(world, player);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    private void attack(World world, PlayerEntity player) {
        BlockStateParticleEffect particleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState());
        ((ServerWorld) player.getWorld()).spawnParticles(particleEffect,player.getX(),player.getY()+1,player.getZ(),500,3,1,3,0);
        player.getWorld().playSound(null,player.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_01.get(),SoundCategory.PLAYERS,1,0);
        player.getWorld().playSound(null,player.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_02.get(),SoundCategory.PLAYERS,1,0);
        player.getWorld().playSound(null,player.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_FIRE_ATTACK_03.get(),SoundCategory.PLAYERS,1,0);
        player.getItemCooldownManager().set(this.getDefaultStack().getItem(),skillCooldown);

        for (LivingEntity livingEntity : player.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(player.getX()-4,player.getY()-2,player.getZ()-4,player.getX()+4,player.getY()+5,player.getZ()+4))) {
            if(livingEntity == player || livingEntity.isTeammate(player)) continue;
            livingEntity.damage(player.getDamageSources().playerAttack(player),15);
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.ARMOUR_CRUNCH,160,2));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,160,1));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,160,1));
            livingEntity.setVelocity(0,1.2,0);
            livingEntity.velocityModified = true;
        }
        
    }

    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip2").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip5").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.earthshatter.tooltip8").setStyle(TEXT));

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.ASH, ParticleTypes.ASH, ParticleTypes.ASH, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
