package net.rosemarythyme.simplymore.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class RemovedItem extends Item {
    private final Supplier<ItemStack> newStack;
    public RemovedItem(Supplier<ItemStack> newStack) {
        super(new Settings());
        this.newStack = newStack;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity playerEntity) {
            playerEntity.getInventory().setStack(slot, newStack.get());
        }
    }
}
