package net.rosemarythyme.simplymore.util;

import net.minecraft.item.ItemStack;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.sweenus.simplyswords.api.AwakeningApi;

public class ItemStackUtils {

    public static CounterComponent getCounterComponent(ItemStack stack) {
        if(!(stack.getItem() instanceof SimplyMoreUniqueSwordItem swordItem))
            throw new IllegalArgumentException("MathUtils#getCounterComponent should not be called on a non-unique.");

        CounterComponent component = stack.getComponents().get(ItemComponentRegistry.COUNTER.get());

        return component == null
                ? setCounterComponent(stack, swordItem.getDefaultCounterComponent())
                : component;
    }

    public static float getCounterComponentProgress(ItemStack stack) {
        CounterComponent component = getCounterComponent(stack);
        return component.value() / (float) component.max();
    }

    public static CounterComponent setCounterComponent(ItemStack stack, CounterComponent component) {
        stack.set(ItemComponentRegistry.COUNTER.get(), component);
        return component;
    }

    public static CounterComponent addToCounterComponent(ItemStack stack, int value) {
        CounterComponent component = getCounterComponent(stack);
        return setCounterComponent(stack, component.add(value));
    }

    public static CounterComponent setCounterComponentValue(ItemStack stack, int value) {
        CounterComponent component = getCounterComponent(stack);
        return setCounterComponent(stack, component.set(value));
    }

    public static boolean isStackAwakened(ItemStack stack) {
        return !AwakeningApi.isAwakeningSystemEnabled() || AwakeningApi.isAbilityUnlocked(stack);
    }
}
