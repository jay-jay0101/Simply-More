//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.rosemarythyme.simplymore.recipes;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.uniques.MatterbaneItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;

public class MatterbaneRecolorRecipe extends SpecialCraftingRecipe {
    public MatterbaneRecolorRecipe(Identifier identifier, CraftingRecipeCategory craftingRecipeCategory) {
        super(identifier, craftingRecipeCategory);
    }
    private static final String[] colours = {
            "white",
            "orange",
            "magenta",
            "light_blue",
            "yellow",
            "lime",
            "pink",
            "gray",
            "light_gray",
            "cyan",
            "purple",
            "blue",
            "brown",
            "green",
            "red",
            "black"
    };
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        int i = 0;
        int j = 0;

        for(int k = 0; k < recipeInputInventory.size(); ++k) {
            ItemStack itemStack = recipeInputInventory.getStack(k);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof MatterbaneItem) {
                    ++i;
                } else {
                    if (!(itemStack.getItem() instanceof DyeItem)) {
                        return false;
                    }

                    ++j;
                }

                if (j > 1 || i > 1) {
                    return false;
                }
            }
        }

        return i == 1 && j == 1;
    }

    // TODO: Check the code here and clarify variable names
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack matterbaneItemStack = ItemStack.EMPTY;
        DyeItem dyeItem = (DyeItem) Items.WHITE_DYE;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack inputStack = inventory.getStack(i);
            if (!inputStack.isEmpty()) {
                Item inputItem = inputStack.getItem();
                if (inputItem instanceof MatterbaneItem) {
                    matterbaneItemStack = inputStack;
                } else if (inputItem instanceof DyeItem) {
                    dyeItem = (DyeItem) inputItem;
                }
            }
        }

        ItemStack recoloredMatterbane = new ItemStack(ModItemsRegistry.MATTERBANE);
        if (matterbaneItemStack.hasNbt() && matterbaneItemStack.getNbt() != null) {
            recoloredMatterbane.setNbt(matterbaneItemStack.getNbt().copy());
        }

        recoloredMatterbane.getOrCreateNbt().putInt("simplymore:color",getIndex(colours,dyeItem.getColor().getName()));

        return recoloredMatterbane;
    }

    public static int getIndex(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return 14;
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public Identifier getId() {
        return new Identifier(SimplyMore.ID, "matterbane_recolor");
    }

    public RecipeSerializer<?> getSerializer() {return RecipeSerializer.ARMOR_DYE;}
}
