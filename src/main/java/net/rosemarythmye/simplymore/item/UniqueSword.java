//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.rosemarythmye.simplymore.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.HelperMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UniqueSword extends UniqueSwordItem {
    String iRarity = "UNIQUE";
    String[] repairIngredient;

    public UniqueSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.fireproof());
        this.repairIngredient = new String[]{"simplyswords:runic_tablet"};
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        List<Item> potentialIngredients = new ArrayList<>(List.of());
        Arrays.stream(this.repairIngredient).toList().forEach(
                (repIngredient) -> potentialIngredients.add(
                        Registries.ITEM.get(new Identifier(repIngredient))));
        return potentialIngredients.contains(ingredient.getItem());
    }


    public Text getName(ItemStack stack) {
        Style COMMON = HelperMethods.getStyle("common");
        Style UNIQUE = HelperMethods.getStyle("unique");
        Style LEGENDARY = HelperMethods.getStyle("legendary");
        if (false) {
            this.iRarity = "LEGENDARY";
            return Text.translatable(this.getTranslationKey(stack)).setStyle(LEGENDARY);
        } else {
            return Text.translatable(this.getTranslationKey(stack)).setStyle(UNIQUE);
        }
    }
}
