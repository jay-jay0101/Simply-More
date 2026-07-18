package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.components.UsageComponent;
import net.rosemarythyme.simplymore.registry.ItemComponentRegistry;

public interface CooldownOnUnselected {
    default void detectUnselect(PlayerEntity player, ItemStack stack, boolean selected, boolean twoHanded) {
        if(player.getItemCooldownManager().isCoolingDown(stack.getItem())) stopUsing(stack);
        UsageComponent component = getComponent(stack);

        if(!twoHanded) {
            if(component.offhandPrevious() != component.offhand()) {
                selected = selected || player.getOffHandStack() == stack;
            } else {
                selected = false;
            }
        }

        if(!selected && !component.using()) {
            player.stopUsingItem();
            player.getItemCooldownManager().set(stack.getItem(), getCooldown());
            stopUsing(stack);
        }
    }

    int getCooldown();

    default void startUsing(ItemStack stack, Hand hand) {
        UsageComponent component = getComponent(stack);
        setComponent(stack, new UsageComponent(true, hand == Hand.OFF_HAND, component.offhand()));
    }

    static UsageComponent getComponent(ItemStack stack) {
        return stack.getOrDefault(ItemComponentRegistry.USAGE.get(), new UsageComponent(false, false, false));
    }

    static void setComponent(ItemStack stack, UsageComponent component) {
        stack.set(ItemComponentRegistry.USAGE.get(), component);
    }

    static void stopUsing(ItemStack stack) {
        setComponent(stack, new UsageComponent(false, false, false));
    }
}
