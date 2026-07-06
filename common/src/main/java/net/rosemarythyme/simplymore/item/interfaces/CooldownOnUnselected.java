package net.rosemarythyme.simplymore.item.interfaces;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.components.UsageComponent;
import net.rosemarythyme.simplymore.registry.ItemComponentRegistry;

// TODO: remove when updating Cindergorge
public interface CooldownOnUnselected {

    default void beginCooldown(PlayerEntity user, int time) {
        user.stopUsingItem();
        user.getItemCooldownManager().set((Item) this, time);
    }

    static void updateComponent(ItemStack stack, Boolean using, Boolean offhand, Boolean previousOffhand) {
        UsageComponent component = getComponent(stack);
        using = using == null ? component.using() : using;
        offhand = offhand == null ? component.offhand() : offhand;
        previousOffhand = previousOffhand == null ? component.offhandPrevious() : previousOffhand;

        stack.set(ItemComponentRegistry.USAGE.get(), new UsageComponent(using, offhand, previousOffhand));
    }

    static UsageComponent getComponent(ItemStack stack) {
        return stack.getOrDefault(ItemComponentRegistry.USAGE.get(), new UsageComponent(false, false, false));
    }

    default void startUsing(ItemStack stack, Hand hand) {
        updateComponent(stack, true, null, null);

        if(getOffhand(stack) != null) {
            updateComponent(stack, null, null, getOffhand(stack));
        }

        updateComponent(stack, null, hand == Hand.OFF_HAND, null);
    }

    default Boolean getUsing(ItemStack stack) {
        return getComponent(stack).using();
    }

    default Boolean getOffhand(ItemStack stack) {
        return getComponent(stack).offhand();
    }

    default Boolean getOffhandPrevious(ItemStack stack) {
        return getComponent(stack).offhandPrevious();
    }

    default boolean checkIfHandChanged(ItemStack stack) {
        return getOffhand(stack) != getOffhandPrevious(stack);
    }

    default void endUsing(ItemStack stack) {
        updateComponent(stack, false, false, false);
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

        beginCooldown(user, time);
        endUsing(stack);
    }
}
