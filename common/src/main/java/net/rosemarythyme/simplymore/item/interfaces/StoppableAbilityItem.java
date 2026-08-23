package net.rosemarythyme.simplymore.item.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public interface StoppableAbilityItem {
    void stop(ItemStack stack, ServerWorld world, LivingEntity user, int ticksRemaining);
}
