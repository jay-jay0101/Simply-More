package net.rosemarythyme.simplymore.item;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.config.WrapperConfig;
import net.rosemarythyme.simplymore.item.uniques.idols.DarksentItem;
import net.rosemarythyme.simplymore.item.uniques.idols.HolyLightItem;
import net.sweenus.simplyswords.item.LegacyWeaponAttributes;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.Styles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SimplyMoreUniqueSwordItem extends UniqueSwordItem {
    String iRarity = "UNIQUE";
    String[] repairIngredient;

    protected static WrapperConfig config = AutoConfig.getConfigHolder(WrapperConfig.class).getConfig();
    protected static UniqueEffectConfig effect = config.uniqueEffects;

    public SimplyMoreUniqueSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, LegacyWeaponAttributes.configure(settings, attackDamage, attackSpeed));
        this.repairIngredient = new String[]{"simplyswords:runic_tablet"};
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        List<Item> potentialIngredients = new ArrayList<>(List.of());
        Arrays.stream(this.repairIngredient).toList().forEach(
                (repIngredient) -> potentialIngredients.add(
                        Registries.ITEM.get(new Identifier(repIngredient))));
        return potentialIngredients.contains(ingredient.getItem());
    }

    @Override
    public Text getName(ItemStack stack) {
        Style UNIQUE = Styles.UNIQUE;
        Style LEGENDARY = Styles.LEGENDARY;
        if (stack.getItem() instanceof HolyLightItem || stack.getItem() instanceof DarksentItem) {
            this.iRarity = "LEGENDARY";
            return Text.translatable(this.getTranslationKey(stack)).setStyle(LEGENDARY);
        } else {
            return Text.translatable(this.getTranslationKey(stack)).setStyle(UNIQUE);
        }
    }
}
