package net.rosemarythyme.simplymore.item.normal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.*;

public class SimplyMoreSwordItem extends SwordItem {
    String[] repairIngredient;

    public SimplyMoreSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.repairIngredient = repairIngredient;
    }


    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        List<Item> potentialIngredients = new ArrayList<>(List.of());

        if(this.repairIngredient[0].equals("tag")) {
            Map<String, TagKey<Item>> tags = new HashMap<>();
            tags.put("wooden", ItemTags.PLANKS);
            tags.put("stone", ItemTags.STONE_TOOL_MATERIALS);

            TagKey<Item> tag = tags.get(this.repairIngredient[1]);
            return ingredient.isIn(tag);
        }

        Arrays.stream(this.repairIngredient).toList().forEach(
                (repIngredient) -> potentialIngredients.add(
                        Registries.ITEM.get(new Identifier(repIngredient))));
        return potentialIngredients.contains(ingredient.getItem());
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            HelperMethods.playHitSounds(attacker, target);
        }

        return super.postHit(stack, target, attacker);
    }
}
