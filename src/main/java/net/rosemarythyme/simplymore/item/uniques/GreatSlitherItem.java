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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.GreatSlitherFangEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;


public class GreatSlitherItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = 200;

    public GreatSlitherItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            if (attacker.getRandom().nextBetween(1, 100) <= 25) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 90, 0), attacker);
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient()) {
            return super.use(world, user, hand);
        }

        double yawAngle = Math.toRadians(user.getYaw());
        double cosYaw = Math.cos(yawAngle);
        double sinYaw = Math.sin(yawAngle);

        for (int distanceMultiplier = 1; distanceMultiplier < 7; distanceMultiplier++) {
            double offsetX = -distanceMultiplier * sinYaw;
            double offsetZ = distanceMultiplier * cosYaw;

            double spawnX = user.getX() + 1.2 * offsetX;
            double spawnZ = user.getZ() + 1.2 * offsetZ;

            world.spawnEntity(new GreatSlitherFangEntity(world, spawnX, user.getY(), spawnZ, user.getYaw(), 0, user));
        }

        user.getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.SNEEZE, ParticleTypes.SNEEZE, ParticleTypes.SPORE_BLOSSOM_AIR);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = HelperMethods.getStyle("rightclick");
        Style abilityStyle = HelperMethods.getStyle("ability");
        Style textStyle = HelperMethods.getStyle("text");

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.great_slither.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }
}
