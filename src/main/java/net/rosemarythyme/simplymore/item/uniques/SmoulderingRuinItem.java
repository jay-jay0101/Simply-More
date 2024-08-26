package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class SmoulderingRuinItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 800;

    public SmoulderingRuinItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextBetween(1, 100) <= 25) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 0), attacker);
                if(target.hasStatusEffect(ModEffectsRegistry.WITHERING_FATE)) {
                    target.addStatusEffect(
                            new StatusEffectInstance(
                                    ModEffectsRegistry.WITHERING_FATE,
                                    target.getStatusEffect(ModEffectsRegistry.WITHERING_FATE).getDuration(),
                                    target.getStatusEffect(ModEffectsRegistry.WITHERING_FATE).getAmplifier() + 1
                            ), attacker);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            float yaw = (float) Math.toRadians(user.getYaw());
            double dZ = Math.cos(yaw);
            double dX = Math.sin(yaw);
            user.setVelocity(dX,1,-dZ);
            user.velocityModified = true;
            user.getWorld().playSound(null,user.getX(),user.getY(),user.getZ(), SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_FLYBY_01.get(), SoundCategory.PLAYERS,1,1);
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.MISTIFIED,10000,0));
            user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        }
        return super.use(world, user, hand);
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style RIGHTCLICK = HelperMethods.getStyle("rightclick");
        Style ABILITY = HelperMethods.getStyle("ability");
        Style TEXT = HelperMethods.getStyle("text");
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip1").setStyle(ABILITY));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip2").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip3").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip4").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(RIGHTCLICK));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip5").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip6").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip7").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip8").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip9").setStyle(TEXT));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip10").setStyle(TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip11").setStyle(TEXT));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }


    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.CRIMSON_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
