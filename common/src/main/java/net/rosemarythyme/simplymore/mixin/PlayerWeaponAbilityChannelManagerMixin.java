package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;
import net.sweenus.simplyswords.world.PlayerWeaponAbilityChannelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(PlayerWeaponAbilityChannelManager.class)
public class PlayerWeaponAbilityChannelManagerMixin {
    @WrapOperation(method = "tickPlayer", at = @At(value = "INVOKE", target = "Lnet/sweenus/simplyswords/world/PlayerWeaponAbilityChannelManager;isValid(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/sweenus/simplyswords/world/PlayerWeaponAbilityChannelManager$ActiveChannel;)Z"))
    private static boolean simplymore$stop(ServerPlayerEntity player, @Coerce Object channel, Operation<Boolean> original) {
        boolean o = original.call(player, channel);
        if(o) return true;

        ItemStack stack = ((PlayerWeaponAbilityChannelManagerDuck) channel).simplymore$getStack();
        if(stack.getItem() instanceof StoppableAbilityItem item) {
            item.stop(stack, player.getServerWorld(), player);
        }

        return false;
    }
}
