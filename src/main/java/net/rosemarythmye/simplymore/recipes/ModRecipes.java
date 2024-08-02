package net.rosemarythmye.simplymore.recipes;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.recipe.FireworkStarFadeRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.rosemarythmye.simplymore.Simplymore;

public class ModRecipes {
    public static void registerModRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Simplymore.ID,"matterbane_recolor"), new SpecialRecipeSerializer(MatterbaneRecolor::new));
   }
}