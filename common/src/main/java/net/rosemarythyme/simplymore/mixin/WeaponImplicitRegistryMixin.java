package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.SimplyMore;
import net.sweenus.simplyswords.api.WeaponImplicitDefinition;
import net.sweenus.simplyswords.api.WeaponImplicitRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WeaponImplicitRegistry.class)
public class WeaponImplicitRegistryMixin {

    @ModifyReturnValue(method = "formatRangePreview", at = @At("RETURN"))
    private static Text simplymore$formatRangePreview(Text original, WeaponImplicitDefinition definition) {
        if(definition.id().getNamespace().equals(SimplyMore.ID)) {
            String id = definition.id().getPath();
            String key = "null";

            if(id.equals("grandsword_sunder")) {
                key = "grandsword";
            }

            if(id.equals("friendship")) {
                key = "friendship";
            }

            if(id.equals("disarm")) {
                key = "disarm";
            }

            if(id.equals("stun")) {
                key = "stun";
            }

            return Text.translatable("tooltip.simplymore.implicit." + key, definition.minValue() + "-" + definition.maxValue());
        }

        return original;
    }
}
