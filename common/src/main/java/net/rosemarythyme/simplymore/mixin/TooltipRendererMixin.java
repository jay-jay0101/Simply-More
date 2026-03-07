package net.rosemarythyme.simplymore.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.rosemarythyme.simplymore.client.tooltip.motifs.BloodMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.CogMotif;
import net.rosemarythyme.simplymore.client.tooltip.motifs.DeathMotif;
import net.sweenus.simplytooltips.client.render.TooltipRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TooltipRenderer.class)
public abstract class TooltipRendererMixin {
	@ModifyReturnValue(method = "borderStyleFor", at = @At("RETURN"), remap = false)
	private static int simplymore$borderStyle(int original, String motif) {
		if (original == 0) {
			return switch (motif) {
				case "cog" -> CogMotif.ID;
				case "death" -> DeathMotif.ID;
				case "blood" -> BloodMotif.ID;
				default -> 0;
			};
		}
		return original;
	}
}