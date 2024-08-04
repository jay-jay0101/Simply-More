package net.rosemarythmye.simplymore.item.uniques;

import dev.architectury.platform.Mod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.entity.BlackPearlFireball;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.item.custom.StealSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SoulForeseer extends UniqueSword {
    int skillCooldown = 100;
    public SoulForeseer(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if(attacker.getRandom().nextInt(100) <= 30 && !target.hasStatusEffect(ModEffects.FORESEEN)) {
                attacker.getWorld().playSound(null,attacker.getBlockPos(),SoundRegistry.MAGIC_SHAMANIC_NORDIC_27.get(),SoundCategory.PLAYERS);
                target.addStatusEffect(new StatusEffectInstance(ModEffects.FORESEEN,160,0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING,10,0));
                ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.SOUL_FIRE_FLAME,attacker.getX(),attacker.getY()+1,attacker.getZ(),50,0.25,0.5,0.25,0.1);


            }
        }

        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            boolean use = false;
            LivingEntity target = null;
            for (LivingEntity livingEntity : user.getWorld().getNonSpectatingEntities(LivingEntity.class, new Box(user.getX() - 20, user.getY() - 20, user.getZ() - 20, user.getX() + 20, user.getY() + 20, user.getZ() + 20))) {
                if(livingEntity == user || livingEntity.isTeammate(user) || !livingEntity.hasStatusEffect(ModEffects.FORESEEN)) continue;
                livingEntity.removeStatusEffect(ModEffects.FORESEEN);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS,80,3));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS,80,0));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,80,1));
                livingEntity.teleport(user.getX(),user.getY(),user.getZ());
                livingEntity.velocityModified = true;
                use = true;
            }

            if(use) {
                user.getWorld().playSound(null,user.getBlockPos(),SoundRegistry.MAGIC_SHAMANIC_NORDIC_22.get(),SoundCategory.PLAYERS);
                user.getItemCooldownManager().set(this.getDefaultStack().getItem(),skillCooldown);
            }
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip4").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip5").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.soul_foreseer.tooltip7").setStyle(TEXT));

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

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.SOUL, ParticleTypes.SCULK_SOUL, ParticleTypes.WARPED_SPORE, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
