
package net.rosemarythyme.simplymore.recipes;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.rosemarythyme.simplymore.SimplyMore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class UpgradeUniqueRecipe extends ShapedRecipe {
    private final int upgradableItemSlot;
    private final ItemStack output;

    public UpgradeUniqueRecipe(Identifier id, String group, CraftingRecipeCategory category, int width, int height, DefaultedList<Ingredient> input, ItemStack output, int upgradableItemSlot) {
        super(id, group, category, width, height, input, output);
        this.upgradableItemSlot = upgradableItemSlot;
        this.output = output;
    }

    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack output = this.output.copy();
        output.setNbt(inventory.getStack(upgradableItemSlot).getOrCreateNbt().copy());

        return output;
    }

    static Pair<DefaultedList<Ingredient>, Integer> createPatternMatrix(String[] pattern, Map<String, Pair<Ingredient, Boolean>> symbols, int width, int height) {
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(symbols.keySet());
        set.remove(" ");

        int slot = -1;

        for(int i = 0; i < pattern.length; ++i) {
            for(int j = 0; j < pattern[i].length(); ++j) {
                String string = pattern[i].substring(j, j + 1);
                Ingredient ingredient = symbols.get(string).getLeft();
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + string + "' but it's not defined in the key");
                }

                if (symbols.get(string).getRight()) {
                    if(slot < 0) {
                        slot = j + (i * pattern[i].length());
                    } else {
                        throw new JsonSyntaxException("Pattern attempted to define slot #" + j + " as upgradable, but slot #" + slot + " is already upgradable");
                    }
                }

                set.remove(string);
                defaultedList.set(j + width * i, ingredient);
            }
        }

        if (slot < 0) {
            throw new JsonSyntaxException("Pattern does not define a slot as upgradable");
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return new Pair<>(defaultedList, slot);
        }
    }

    static Map<String, Pair<Ingredient, Boolean>> readSymbols(JsonObject json) {
        Map<String, Pair<Ingredient, Boolean>> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if ((entry.getKey()).length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            boolean isSlot = JsonHelper.getBoolean(entry.getValue().getAsJsonObject(), "upgradable", false);

            map.put(entry.getKey(), new Pair<>(Ingredient.fromJson(entry.getValue()), isSlot));
        }

        map.put(" ", new Pair<>(Ingredient.EMPTY, false));
        return map;
    }

    static String[] getPattern(JsonArray json) {
        // Clamp Rows
        String[] strings = new String[json.size()];
        if (strings.length > 3) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
        } else if (strings.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }


        // Clamp Columns
        for(int i = 0; i < strings.length; ++i) {
                String string = JsonHelper.asString(json.get(i), "pattern[" + i + "]");
                if (string.length() > 3) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
                }

                if (i > 0 && strings[0].length() != string.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                strings[i] = string;
        }

        // Find Box Size
        /// Note that these values are initially corresponding to their opposing corner. We start from a negatively sized box and work outwards
        int a = strings[0].length(); // x1
        int b = strings.length; // y1
        int c = 0; // x2
        int d = 0; // y2

        for(int y = 0; y < strings.length; y++) {
            for(int x = 0; x < strings[0].length(); x++) {
                if (strings[y].charAt(x) == ' ') continue;

                if(x < a) a = x;
                if(x > c) c = x;

                if(y < b) b = y;
                if(y > d) d = y;
            }
        }

        // Reconstruct with excess whitespace stripped
        List<String> strippedStrings = new ArrayList<>();
        for(int y = 0; y < strings.length; y++) {
            if(y < b || y > d) continue;
            strippedStrings.add(strings[y].substring(a, c+1));
        }

        return strippedStrings.toArray(new String[0]);
    }

    public RecipeSerializer<?> getSerializer() {return RecipeSerializer.ARMOR_DYE;}

    public static class Serializer implements RecipeSerializer<UpgradeUniqueRecipe> {
        public UpgradeUniqueRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            CraftingRecipeCategory craftingRecipeCategory = CraftingRecipeCategory.CODEC.byId(JsonHelper.getString(jsonObject, "category", null), CraftingRecipeCategory.MISC);
            Map<String, Pair<Ingredient, Boolean>> map = UpgradeUniqueRecipe.readSymbols(JsonHelper.getObject(jsonObject, "key"));
            String[] strings = UpgradeUniqueRecipe.getPattern(JsonHelper.getArray(jsonObject, "pattern"));

            int i = strings[0].length();
            int j = strings.length;
            Pair<DefaultedList<Ingredient>, Integer> patternMatrix = UpgradeUniqueRecipe.createPatternMatrix(strings, map, i, j);
            DefaultedList<Ingredient> defaultedList = patternMatrix.getLeft();

            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new UpgradeUniqueRecipe(identifier, string, craftingRecipeCategory, i, j, defaultedList, itemStack, patternMatrix.getRight());
        }

        public UpgradeUniqueRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int i = packetByteBuf.readVarInt();
            int j = packetByteBuf.readVarInt();
            String string = packetByteBuf.readString();
            CraftingRecipeCategory craftingRecipeCategory = packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i * j, Ingredient.EMPTY);

            defaultedList.replaceAll(ing -> Ingredient.fromPacket(packetByteBuf));

            ItemStack itemStack = packetByteBuf.readItemStack();
            int slot = packetByteBuf.readVarInt();
            return new UpgradeUniqueRecipe(identifier, string, craftingRecipeCategory, i, j, defaultedList, itemStack, slot);
        }

        public void write(PacketByteBuf packetByteBuf, UpgradeUniqueRecipe shapedRecipe) {
            packetByteBuf.writeVarInt(shapedRecipe.getHeight());
            packetByteBuf.writeVarInt(shapedRecipe.getWidth());
            packetByteBuf.writeString(shapedRecipe.getGroup());
            packetByteBuf.writeEnumConstant(shapedRecipe.getCategory());

            for(Ingredient ingredient : shapedRecipe.getIngredients()) {
                ingredient.write(packetByteBuf);
            }

            packetByteBuf.writeItemStack(shapedRecipe.output);
            packetByteBuf.writeVarInt(shapedRecipe.upgradableItemSlot);
        }
    }
}
