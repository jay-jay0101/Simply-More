package net.rosemarythyme.simplymore.item.interfaces;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface CooldownOnUnselected {

    String nbt = "simplymore:using";
    String offhand = "simplymore:offhand";
    String previous_offhand = "simplymore:previous_offhand";

    default void cooldown(PlayerEntity user, int time) {
        user.stopUsingItem();
        user.getItemCooldownManager().set((Item) this, time);
    }

    default void startUsing(ItemStack stack, Hand hand) {
        stack.getOrCreateNbt().putBoolean(nbt, true);

        if(getOffhand(stack) != null) {
            stack.getOrCreateNbt().putBoolean(previous_offhand, getOffhand(stack));
        }

        stack.getOrCreateNbt().putBoolean(offhand, hand == Hand.OFF_HAND);
    }

    default Boolean getUsing(ItemStack stack) {
        if(stack.getOrCreateNbt().contains(nbt)) {
            return stack.getOrCreateNbt().getBoolean(nbt);
        } else {
            return null;
        }
    }

    default Boolean getOffhand(ItemStack stack) {
        if(stack.getOrCreateNbt().contains(offhand)) {
            return stack.getOrCreateNbt().getBoolean(offhand);
        } else {
            return null;
        }
    }

    default boolean checkIfHandChanged(ItemStack stack) {
        if(stack.getOrCreateNbt().contains(offhand) && stack.getOrCreateNbt().contains(previous_offhand)) {
            return stack.getOrCreateNbt().getBoolean(previous_offhand) != stack.getOrCreateNbt().getBoolean(offhand);
        } else {
            return false;
        }
    }

    default void endUsing(ItemStack stack) {
        stack.getOrCreateNbt().putBoolean(nbt, false);
        stack.getOrCreateNbt().remove(previous_offhand);
        stack.getOrCreateNbt().remove(offhand);
    }

    default void detectCooldown(PlayerEntity user, boolean selected, ItemStack stack, int time, boolean twoHanded) {

        if(user.getItemCooldownManager().isCoolingDown((Item)this)) {
            endUsing(stack);
            return;
        }

        if(!twoHanded) {
            if(!checkIfHandChanged(stack)) {
                selected = selected || user.getOffHandStack() == stack;
            } else {
                selected = false;
            }
        }

        if(selected || !Boolean.TRUE.equals(getUsing(stack))) {
            return;
        }

        cooldown(user, time);
        endUsing(stack);
    }
}
