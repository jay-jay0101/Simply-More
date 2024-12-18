package net.rosemarythyme.simplymore.item.compat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.normal.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

public class CompatSwordItem extends SimplyMoreSwordItem {
    public boolean isGrandsword;
    public boolean isLance;

    public CompatSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings, boolean grandsword , boolean lance, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings, repairIngredient);
        this.isGrandsword = grandsword;
        this.isLance = lance;
    }

    public boolean getIsGrandsword() {
        return this.isGrandsword;
    }

    public boolean getIsLance() {
        return this.isLance;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (this.isLance
                && entity.getVehicle() instanceof LivingEntity
                && selected
                && ((PlayerEntity) entity).getStackInHand(Hand.OFF_HAND).getItem().getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(EntityAttributes.GENERIC_ATTACK_DAMAGE).isEmpty()) {
            ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.LANCE, 9999999, 0));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }
}