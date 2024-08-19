package net.rosemarythyme.simplymore.recipes;

import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.Simplymore;

public class ModRecipes {
    public static void registerModRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Simplymore.ID,"matterbane_recolor"), new SpecialRecipeSerializer(MatterbaneRecolor::new));
   }
}