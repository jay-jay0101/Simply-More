package net.rosemarythyme.simplymore.mixin;

import net.minecraft.client.gui.DrawContext;
import net.rosemarythyme.simplymore.client.tooltip.motifs.BloodMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.DeathMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.CogMotif;
import net.rosemarythyme.simplymore.registry.TooltipMotifRegistry;
import net.sweenus.simplytooltips.api.TooltipTheme;
import net.sweenus.simplytooltips.client.render.BorderRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BorderRenderer.class)
public class BorderRendererMixin {
    @Inject(at=@At("HEAD"), method = "drawBorderPattern", cancellable = true)
    private static void drawBorderPattern(DrawContext context, int x, int y, int w, int h, TooltipTheme theme, int borderStyle, CallbackInfo ci) {
        switch (borderStyle) {
            case CogMotif.ID -> TooltipMotifRegistry.COG.drawBorderPattern(context, x, y, w, h, theme, borderStyle);
            case DeathMotif.ID -> TooltipMotifRegistry.DEATH.drawBorderPattern(context, x, y, w, h, theme, borderStyle);
            case BloodMotif.ID -> TooltipMotifRegistry.BLOOD.drawBorderPattern(context, x, y, w, h, theme, borderStyle);
            default -> {
                return;
            }
        }

        ci.cancel();
    }
}
