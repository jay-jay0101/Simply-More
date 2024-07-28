//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.rosemarythmye.simplymore.recipes;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rosemarythmye.simplymore.Simplymore;
import net.rosemarythmye.simplymore.item.ModItems;
import net.rosemarythmye.simplymore.item.uniques.Matterbane;

public class MatterbaneRecolor extends SpecialCraftingRecipe {
    public MatterbaneRecolor(Identifier identifier, CraftingRecipeCategory craftingRecipeCategory) {
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
                if (itemStack.getItem() instanceof Matterbane) {
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

    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager) {
        ItemStack itemStack = ItemStack.EMPTY;
        DyeItem dyeItem = (DyeItem)Items.WHITE_DYE;

        for(int i = 0; i < recipeInputInventory.size(); ++i) {
            ItemStack itemStack2 = recipeInputInventory.getStack(i);
            if (!itemStack2.isEmpty()) {
                Item item = itemStack2.getItem();
                if (item instanceof Matterbane) {
                    itemStack = itemStack2;
                } else if (item instanceof DyeItem) {
                    dyeItem = (DyeItem)item;
                }
            }
        }

        ItemStack itemStack3 = new ItemStack(ModItems.MATTERBANE);
        if (itemStack.hasNbt()) {
            itemStack3.setNbt(itemStack.getNbt().copy());
        }

        itemStack3.getOrCreateNbt().putInt("simplymore:color",getIndex(colours,dyeItem.getColor().getName()));

        return itemStack3;
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
        return new Identifier(Simplymore.ID, "matterbane_recolor");
    }

    public RecipeSerializer<?> getSerializer() {return RecipeSerializer.ARMOR_DYE;}
}
