package net.rosemarythmye.simplymore.item.itemclasses.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.item.itemclasses.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

import static java.lang.Math.max;

public class Glimmerstep extends UniqueSword {
    int skillCooldown = 1200;


    public Glimmerstep(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (attacker.getVehicle() instanceof LivingEntity) {
                    if (attacker.getRandom().nextInt(100) <= 30) {
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 0), attacker);
                    }
                } else {
                    if (attacker.getRandom().nextInt(100) <= 5) {
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 0), attacker);
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
            ServerWorld serverWorld = ((ServerWorld) world);
            if(remainingUseTicks==this.getMaxUseTime(null)-1) serverWorld.playSound(null,user.getBlockPos(),SoundEvents.BLOCK_BEACON_ACTIVATE,user.getSoundCategory(),1.0f,0.8f);
            serverWorld.spawnParticles(ParticleTypes.GLOW,user.getX(),user.getY()+1,user.getZ(), ((int) Math.floor((double) (60 - remainingUseTicks) / 2)),0.2,0.4,0.2,0.5);
            if(remainingUseTicks==1) {
                user.dismountVehicle();
                serverWorld.playSound(null,user.getBlockPos(),SoundRegistry.MAGIC_SHAMANIC_NORDIC_21.get(),user.getSoundCategory(),0.1f,1f);
                serverWorld.spawnParticles(ParticleTypes.WAX_OFF,user.getX(),user.getY()+1,user.getZ(), 60,0.2,0.4,0.2,0.5);

                Position blockPos = user.raycast(31, 0, false).getPos();
                user.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ());

                serverWorld.playSound(null,user.getBlockPos(),SoundRegistry.MAGIC_SHAMANIC_NORDIC_21.get(),user.getSoundCategory(),0.1f,1f);
                serverWorld.spawnParticles(ParticleTypes.WAX_OFF,user.getX(),user.getY()+1,user.getZ(), 60,0.2,0.4,0.2,0.5);
                ((PlayerEntity) user).getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 60;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.glimmerstep.tooltip5").setStyle(TEXT));

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.FIREWORK, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
