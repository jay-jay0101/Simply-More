package net.rosemarythyme.simplymore.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.sweenus.simplyswords.world.PlayerWeaponAbilityChannelManager$ActiveChannel")
public interface PlayerWeaponAbilityChannelManagerDuck {
    @Accessor("stack")
    ItemStack simplymore$getStack();
}
