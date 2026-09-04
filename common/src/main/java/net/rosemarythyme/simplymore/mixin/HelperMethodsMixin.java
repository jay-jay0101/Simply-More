package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.sweenus.simplyswords.util.HelperMethods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HelperMethods.class)
public class HelperMethodsMixin {
    @ModifyReturnValue(method = "checkEntityBlacklist", at=@At("RETURN"))
    private static boolean simplymore$checkBlacklist(boolean original, LivingEntity target) {
        if(EntityUtils.isUntargetable(target)) return false;
        return original;
    }
}
