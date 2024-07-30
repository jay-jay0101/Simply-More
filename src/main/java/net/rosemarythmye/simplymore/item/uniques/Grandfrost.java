package net.rosemarythmye.simplymore.item.uniques;

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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class Grandfrost extends UniqueSword {
    int skillCooldown = 500;

    public Grandfrost(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (attacker.getRandom().nextInt(100) <= 25 || target.isBlocking()) {
                    target.addStatusEffect(new StatusEffectInstance(ModEffects.CHILL, 140, 0), attacker);
                }
            }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            boolean use = false;
            for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class,new Box(user.getX()-5,user.getY()-2,user.getZ()-5,user.getX()+5,user.getY()+5,user.getZ()+5))) {
                if(livingEntity == user || livingEntity.isTeammate(user)) continue;
                use = true;
                double xVelocity = livingEntity.getX()-user.getX();
                double zVelocity = livingEntity.getZ()-user.getZ();
                double ratioMax = Math.abs(xVelocity)+ Math.abs(zVelocity);
                float strength = 3.5f;

                xVelocity *= strength/ratioMax;
                zVelocity *= strength/ratioMax;

                livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.CHILL,200,0));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,200,3));
                livingEntity.setVelocity(xVelocity,0.4,zVelocity);
            }

            if(use) {
                user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
                if(!user.getWorld().isClient()) ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SNOWFLAKE,user.getX(),user.getY()+3,user.getZ(),1000,3,0,3,0.25);
                user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_03.get(), user.getSoundCategory(), 2F, 0.3F);

            }
        }
        return super.use(world, user, hand);
    }


    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip3").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip5").setStyle(TEXT));

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.ITEM_SNOWBALL, ParticleTypes.ITEM_SNOWBALL, ParticleTypes.SNOWFLAKE, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
