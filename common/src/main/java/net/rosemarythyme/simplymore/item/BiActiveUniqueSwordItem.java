package net.rosemarythyme.simplymore.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.world.WeaponAbilityCooldownManager;

public abstract class BiActiveUniqueSwordItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    private static final int PRESS_HOLD_CUTOFF_TICKS = 8;

    public BiActiveUniqueSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    protected int getUniqueWeaponMaxUseTime(ItemStack stack, LivingEntity user) {
        return AttackUtils.PSEUDOINFINITE_DURATION;
    }

    @Override
    public final boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public final void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(!(world instanceof ServerWorld serverWorld)) return;
        if(!user.isUsingItem()) return;

        int ticksUsed = AttackUtils.getUseTicksFromInfiniteDuration(remainingUseTicks);

        int cooldown = 0;
        if(ticksUsed <= PRESS_HOLD_CUTOFF_TICKS) {
            if (onPress(stack, serverWorld, user)) cooldown = getCooldownWhenPressed(stack, serverWorld, user);
        } else {
            if (onUnheld(stack, serverWorld, user)) cooldown = getCooldownWhenHeld(stack, serverWorld, user);
        }

        EntityUtils.cooldown(user, stack.getItem(), cooldown, false);
    }

    @Override
    public final void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(!(world instanceof ServerWorld serverWorld)) return;
        if(!user.isUsingItem()) return;

        if(WeaponAbilityCooldownManager.isCoolingDown(serverWorld, user, stack)) return;
        int ticksUsed = AttackUtils.getUseTicksFromInfiniteDuration(remainingUseTicks);

        if(ticksUsed > PRESS_HOLD_CUTOFF_TICKS) {
            whileHeld(stack, serverWorld, user, ticksUsed - PRESS_HOLD_CUTOFF_TICKS);
        } else {
            beforeConfirmation(stack, serverWorld, user, PRESS_HOLD_CUTOFF_TICKS - ticksUsed);
        }
    }

    public boolean onPress(ItemStack stack, ServerWorld world, LivingEntity user) {
        return true;
    }

    public boolean onUnheld(ItemStack stack, ServerWorld world, LivingEntity user) {
        return true;
    }

    public void whileHeld(ItemStack stack, ServerWorld world, LivingEntity user, int ticksUsed) {}
    public void beforeConfirmation(ItemStack stack, ServerWorld world, LivingEntity user, int ticksUntilHeld) {}

    @Override
    public final int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return UniqueWeaponActiveAbility.super.getActivationCooldownTicks(stack, context);
    }

    public int getCooldownWhenPressed(ItemStack stack, ServerWorld world, LivingEntity user) {
        return 0;
    }

    public int getCooldownWhenHeld(ItemStack stack, ServerWorld world, LivingEntity user) {
        return 0;
    }
}
