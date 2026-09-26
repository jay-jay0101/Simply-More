package net.rosemarythyme.simplymore.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {
    public static void replaceStackInInventory(LivingEntity entity, ItemStack oldStack, ItemStack newStack) {
        if(entity instanceof PlayerEntity player) {
            PlayerInventory inventory = player.getInventory();

            int slot = inventory.getSlotWithStack(oldStack);
            if(slot != -1) {
                inventory.setStack(slot, newStack);
                return;
            }
        }

        if(entity.getStackInHand(Hand.MAIN_HAND) == oldStack) {
            entity.setStackInHand(Hand.MAIN_HAND, newStack);
        } else if (entity.getStackInHand(Hand.OFF_HAND) == oldStack) {
            entity.setStackInHand(Hand.OFF_HAND, newStack);
        }
    }

    public static List<ItemStack> getEntireInventory(PlayerEntity player) {
        PlayerInventory inventory = player.getInventory();

        List<ItemStack> stacks = new ArrayList<>(inventory.main);
        stacks.addAll(inventory.offHand);
        stacks.addAll(inventory.armor);
        return stacks;
    }

    public static ItemStack getItemInEitherHand(Item item, LivingEntity entity) {
        ItemStack stack = entity.getStackInHand(Hand.MAIN_HAND);
        if(stack.getItem() == item) return stack;

        stack = entity.getStackInHand(Hand.OFF_HAND);
        if(stack.getItem() == item) return stack;

        return ItemStack.EMPTY;
    }

    public static ItemStack getActiveItem(LivingEntity entity, Item item) {
        if(entity instanceof PlayerEntity) {
            ItemStack stack = entity.getActiveItem();
            return stack.getItem().equals(item) ? stack : ItemStack.EMPTY;
        }

        return getItemInEitherHand(item, entity);
    }

    public static boolean isHoldingInMainHand(LivingEntity entity, ItemStack stack) {
        return entity.getStackInHand(Hand.MAIN_HAND).equals(stack);
    }

    public static boolean isHolding(LivingEntity entity, Item item) {
        if(entity.getStackInHand(Hand.MAIN_HAND).getItem() == item) return true;
        return !(item instanceof TwoHandedWeapon) && entity.getStackInHand(Hand.OFF_HAND).getItem() == item;
    }

    public static boolean isHoldingAwakenedStack(LivingEntity entity, Item item) {
        return !getHeldAwakenedStack(entity, item).isEmpty();
    }

    public static ItemStack getHeldAwakenedStack(LivingEntity entity, Item item) {
        ItemStack stack = entity.getStackInHand(Hand.MAIN_HAND);
        if(stack.getItem() == item && ItemStackUtils.isStackAwakened(stack)) return stack;

        if(item instanceof TwoHandedWeapon) return ItemStack.EMPTY;

        stack = entity.getStackInHand(Hand.OFF_HAND);
        return stack.getItem() == item && ItemStackUtils.isStackAwakened(stack) ? stack : ItemStack.EMPTY;
    }
}
