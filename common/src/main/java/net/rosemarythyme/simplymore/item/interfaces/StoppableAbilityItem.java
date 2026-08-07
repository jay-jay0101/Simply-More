package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface StoppableAbilityItem {
    void stop(ItemStack stack, World world, LivingEntity user);
}
