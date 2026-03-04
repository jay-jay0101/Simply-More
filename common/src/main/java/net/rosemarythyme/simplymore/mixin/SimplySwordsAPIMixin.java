package net.rosemarythyme.simplymore.mixin;

import me.fzzyhmstrs.fzzy_config.util.ValidationResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.rosemarythyme.simplymore.item.RuneCarverItem;
import net.sweenus.simplyswords.api.SimplySwordsAPI;
import net.sweenus.simplyswords.config.Config;
import net.sweenus.simplyswords.power.GemPowerComponent;
import net.sweenus.simplyswords.registry.ComponentTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimplySwordsAPI.class)
public class SimplySwordsAPIMixin {
    @Inject(at = @At("HEAD"), method = "onClickedGemSocketLogic", cancellable = true)
    private static void simplymore$onClickedGemSocketLogic(ItemStack stack, ItemStack otherStack, PlayerEntity player, CallbackInfo ci) {
        if (Config.general.enableUniqueGemSockets) {
            if(otherStack.getItem() instanceof RuneCarverItem runeCarverItem) {
                ValidationResult<GemPowerComponent> result = runeCarverItem.fill(otherStack, SimplySwordsAPI.getComponent(stack));
                if(result.isValid()) {
                    stack.set(ComponentTypeRegistry.GEM_POWER.get(), result.get());
                    player.getWorld().playSoundFromEntity(null, player, SoundEvents.BLOCK_ANVIL_USE, player.getSoundCategory(), 1, 1);
                    otherStack.decrement(1);

                    ci.cancel();
                }
            }
        }
    }
}
