package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.ItemStack;

public interface StackModifierItem {
    AttributeModifiersComponent getModifier(ItemStack stack, AttributeModifiersComponent base);

    default void applyStackModifier(ItemStack stack) {
        AttributeModifiersComponent modifiers = stack.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());

        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, getModifier(stack, modifiers));
    }
}
