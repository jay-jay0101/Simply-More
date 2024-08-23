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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.UniqueSwordItem;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.List;

public class GlimmerstepItem extends UniqueSwordItem {
    int skillCooldown = 1200;

    public GlimmerstepItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }



    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        int chance = attacker.getVehicle() instanceof LivingEntity ? 30 : 5;
        if (attacker.getRandom().nextBetween(1, 100) <= chance) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 0), attacker);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    // This *should* be, functionally the same as what you had. This is just a bit more efficient. PLEASE TEST
    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user.getWorld().isClient || !(user instanceof PlayerEntity)) {
            super.usageTick(world, user, stack, remainingUseTicks);
            return;
        }

        ServerWorld serverWorld = ((ServerWorld) world);
        int ticksUntilUseEnd = this.getMaxUseTime(stack) - remainingUseTicks;

        if (ticksUntilUseEnd == 1) {
            user.dismountVehicle();
            teleportAndPlayEffect(serverWorld, user);
        } else if (ticksUntilUseEnd == 0) {
            serverWorld.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, user.getSoundCategory(), 1.0f, 0.8f);
            serverWorld.spawnParticles(ParticleTypes.GLOW, user.getX(), user.getY() + 1, user.getZ(), (int) Math.floor((60 - remainingUseTicks) / 2.0), 0.2, 0.4, 0.2, 0.5);
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    private void teleportAndPlayEffect(ServerWorld serverWorld, LivingEntity user) {
        Position blockPos = user.raycast(31, 0, false).getPos();
        user.teleport(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        serverWorld.playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SHAMANIC_NORDIC_21.get(), user.getSoundCategory(), 0.1f, 1f);
        serverWorld.spawnParticles(ParticleTypes.WAX_OFF, user.getX(), user.getY() + 1, user.getZ(), 60, 0.2, 0.4, 0.2, 0.5);

        ((PlayerEntity) user).getItemCooldownManager().set(this.getDefaultStack().getItem(), skillCooldown);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 60;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
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

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int stepMod = 0;
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.ELECTRIC_SPARK, ParticleTypes.FIREWORK);
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
