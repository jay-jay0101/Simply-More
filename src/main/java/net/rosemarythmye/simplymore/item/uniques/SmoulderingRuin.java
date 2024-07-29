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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.effect.ModEffects;
import net.rosemarythmye.simplymore.effect.ModStatusEffect;
import net.rosemarythmye.simplymore.entity.GreatSlitherFang;
import net.rosemarythmye.simplymore.item.UniqueSword;
import net.sweenus.simplyswords.util.HelperMethods;
import org.joml.Vector2d;

import java.util.List;


public class SmoulderingRuin extends UniqueSword {
    int skillCooldown = 800;

    public SmoulderingRuin(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextInt(100) <= 10) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 100, 0), attacker);
                if(target.hasStatusEffect(ModEffects.WITHERING_FATE)) {
                    target.addStatusEffect(new StatusEffectInstance(ModEffects.WITHERING_FATE, target.getStatusEffect(ModEffects.WITHERING_FATE).getDuration(), target.getStatusEffect(ModEffects.WITHERING_FATE).getAmplifier()+1), attacker);
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
            user.addStatusEffect(new StatusEffectInstance(ModEffects.MISTIFIED,10000,0));
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

    private static int stepMod = 0;

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stepMod > 0) {
            --stepMod;
        }

        if (stepMod <= 0) {
            stepMod = 7;
        }

        HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.CRIMSON_SPORE, ParticleTypes.CRIMSON_SPORE, ParticleTypes.CRIMSON_SPORE, true);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
