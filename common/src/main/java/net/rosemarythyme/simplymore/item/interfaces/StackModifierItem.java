package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.ItemStack;

public interface StackModifierItem {
    AttributeModifiersComponent getModifier(ItemStack stack, AttributeModifiersComponent base);

    static void applyStackModifier(ItemStack stack, StackModifierItem item) {
        AttributeModifiersComponent modifiers = stack.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());

        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, item.getModifier(stack, modifiers));
    }
}
