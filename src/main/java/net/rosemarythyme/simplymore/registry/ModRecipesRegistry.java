package net.rosemarythyme.simplymore.registry;

import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.recipes.MatterbaneRecolorRecipe;

public class ModRecipesRegistry {
    public static void registerModRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(SimplyMore.ID,"matterbane_recolor"), new SpecialRecipeSerializer<>(MatterbaneRecolorRecipe::new));
    }
}