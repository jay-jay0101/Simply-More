package net.rosemarythyme.simplymore.mixin;

import net.minecraft.util.Identifier;
import net.sweenus.simplyswords.client.util.OracleIndexUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(OracleIndexUtils.class)
public abstract class OracleIndexUtilsMixin {

	@ModifyVariable(at = @At(value = "HEAD"), method = "openOracleIndex", argsOnly = true, ordinal = 0)
	private static Identifier simplymore$override(Identifier value) {
		if (value.getPath().contains("mimicry_")) {
			return Identifier.of("oracle_index:books/simplymore/unique_weapons/mimicry.mdx");
		}

		if (value.getPath().contains("weapon_types/jester_penetrate")) {
			return Identifier.of("oracle_index:books/simplymore/unique_weapons/jester_penetrate.mdx");
		}

		if (value.getPath().contains("weapon_types/the_pan")) {
			return Identifier.of("oracle_index:books/simplymore/unique_weapons/the_pan.mdx");
		}

		return value;
    }
}