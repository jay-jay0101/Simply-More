package net.rosemarythyme.simplymore.forge;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.rosemarythyme.simplymore.config.ModConfigs;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.uniques.BrassturnItem;

import java.util.UUID;

public class EventHandler {
    protected static WrapperConfig config = ModConfigs.safeGetConfig();
    protected static WeaponAttributesConfig attributes = config.weaponAttributes;

    public static void onItemAttributeModifier(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if(stack.getItem() instanceof BrassturnItem && event.getSlotType() == EquipmentSlot.MAINHAND) {

            double attackSpeedModifier = BrassturnItem.getOxidisation(stack) * ((3.4f + attributes.getBrassturnMaxSwingSpeed()) / -16f);

            event.addModifier(
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    new EntityAttributeModifier(UUID.fromString("ebdfffcf-7f0d-4502-96ec-e4f6b995fe2f"), "Weapon modifier", attackSpeedModifier, EntityAttributeModifier.Operation.ADDITION)
            );
        }
    }
}
