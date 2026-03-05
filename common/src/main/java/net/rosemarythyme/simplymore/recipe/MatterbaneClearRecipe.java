package net.rosemarythyme.simplymore.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;

public class MatterbaneClearRecipe extends ShapelessRecipe {
    private final String _group;
    private final CraftingRecipeCategory _category;
    private final ItemStack _result;
    private final DefaultedList<Ingredient> _ingredients;

    public MatterbaneClearRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients) {
        super(group, category, result, ingredients);
        _category = category;
        _group = group;
        _result = result;
        _ingredients = ingredients;
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack input = craftingRecipeInput.getStacks().getFirst();
        ItemStack output = input.copyComponentsToNewStack(input.getItem(), 1);

        output.remove(DataComponentTypes.DYED_COLOR);
        return output;
    }

    public static class Serializer implements RecipeSerializer<MatterbaneClearRecipe> {
        private static final MapCodec<MatterbaneClearRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter((recipe) -> recipe._group), CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter((recipe) -> recipe._category), ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter((recipe) -> recipe._result), Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap((ingredients) -> {
            Ingredient[] ingredients2 = ingredients.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
            if (ingredients2.length == 0) {
                return DataResult.error(() -> "No ingredients for shapeless recipe");
            } else {
                return ingredients2.length > 9 ? DataResult.error(() -> "Too many ingredients for shapeless recipe") : DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients2));
            }
        }, DataResult::success).forGetter((recipe) -> recipe._ingredients)).apply(instance, MatterbaneClearRecipe::new));
        public static final PacketCodec<RegistryByteBuf, MatterbaneClearRecipe> PACKET_CODEC = PacketCodec.ofStatic(MatterbaneClearRecipe.Serializer::write, MatterbaneClearRecipe.Serializer::read);

        public Serializer() {
        }

        public MapCodec<MatterbaneClearRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, MatterbaneClearRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static MatterbaneClearRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = buf.readEnumConstant(CraftingRecipeCategory.class);

            int i = buf.readVarInt();

            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll((ignored) -> Ingredient.PACKET_CODEC.decode(buf));

            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new MatterbaneClearRecipe(string, craftingRecipeCategory, itemStack, defaultedList);
        }

        private static void write(RegistryByteBuf buf, MatterbaneClearRecipe recipe) {
            buf.writeString(recipe._group);
            buf.writeEnumConstant(recipe._category);
            buf.writeVarInt(recipe._ingredients.size());

            for(Ingredient ingredient : recipe._ingredients) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe._result);
        }
    }
}
