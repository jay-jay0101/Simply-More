package net.rosemarythyme.simplymore.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryKeys;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.recipe.MatterbaneClearRecipe;

public class ModRecipeTypeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES =
            DeferredRegister.create(SimplyMore.ID, RegistryKeys.RECIPE_SERIALIZER);

    public static final RegistrySupplier<MatterbaneClearRecipe.Serializer> UNIQUE_UPGRADE =
            RECIPES.register(
                    "matterbane_clean",
                    MatterbaneClearRecipe.Serializer::new
            );

    public static void registerModRecipes() {
        RECIPES.register();
    }
}
