package net.rosemarythyme.simplymore.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.world.SwingCacheManager;
import net.sweenus.simplyswords.api.SimplySwordsAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimplySwordsAPI.class)
public class SimplySwordsAPIMixin {
    @Inject(method="onWeaponSwing", at=@At("TAIL"))
    private static void simplymore$onWeaponSwing(ItemStack stack, ServerWorld world, LivingEntity user, Hand hand, CallbackInfo ci) {
        if(!(stack.getItem() instanceof SimplyMoreUniqueSwordItem unique)) return;

        long time = SwingCacheManager.getCache(user);
        if(world.getTime() > time + 10) {
            unique.onSwing(stack, world, user);
            SwingCacheManager.putInCache(user, world.getTime());
        }
    }
}
