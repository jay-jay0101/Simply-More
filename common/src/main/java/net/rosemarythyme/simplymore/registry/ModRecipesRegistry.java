package net.rosemarythyme.simplymore.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.recipes.MatterbaneRecolorRecipe;
import net.rosemarythyme.simplymore.recipes.UpgradeUniqueRecipe;

public class ModRecipesRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeSerializer<MatterbaneRecolorRecipe>> MATTERBANE_RECOLOR =
            RECIPES.register(
                    new Identifier(SimplyMore.ID,"matterbane_recolor"),
                    () -> new SpecialRecipeSerializer<>(MatterbaneRecolorRecipe::new)
            );

    public static final RegistrySupplier<RecipeSerializer<UpgradeUniqueRecipe>> UNIQUE_UPGRADE =
            RECIPES.register(
                    new Identifier(SimplyMore.ID,"unique_upgrade"),
                    UpgradeUniqueRecipe.Serializer::new
            );

    public static void registerModRecipes() {
        RECIPES.register();
    }
}