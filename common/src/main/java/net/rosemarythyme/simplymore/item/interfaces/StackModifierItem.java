package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface StackModifierItem {
    AttributeModifiersComponent getModifier(LivingEntity entity, ItemStack stack, AttributeModifiersComponent base);

    static void applyStackModifier(LivingEntity entity, ItemStack stack, StackModifierItem item) {
        AttributeModifiersComponent modifiers = stack.getOrDefault(
                DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder().build());

        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, item.getModifier(entity, stack, modifiers));
    }
}
