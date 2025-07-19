package net.rosemarythyme.simplymore.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.Weapon;
import net.rosemarythyme.simplymore.item.uniques.idols.DarksentItem;
import net.rosemarythyme.simplymore.item.uniques.idols.HolylightItem;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.Styles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SimplyMoreUniqueSwordItem extends UniqueSwordItem implements Weapon {
    public CounterComponent getDefaultComponent() {
        return new CounterComponent(0, 0);
    }

    String[] repairIngredient;
    final SwordTypes swordType;

    protected static UniqueEffectConfig effect = ConfigWrapper.unique;

    public SimplyMoreUniqueSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordTypes swordType, Settings settings) {
        super(toolMaterial, settings.fireproof().attributeModifiers(
                SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));

        this.swordType = swordType;
        this.repairIngredient = new String[]{"simplyswords:runic_tablet"};
    }

    @Override
    public SwordTypes swordType() {
        return swordType;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        List<Item> potentialIngredients = new ArrayList<>(List.of());
        Arrays.stream(this.repairIngredient).toList().forEach(
                (repIngredient) -> potentialIngredients.add(
                        Registries.ITEM.get(Identifier.of(repIngredient))));
        return potentialIngredients.contains(ingredient.getItem());
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.getItem() instanceof HolylightItem || stack.getItem() instanceof DarksentItem) {
            return Text.translatable(this.getTranslationKey(stack)).setStyle(Styles.LEGENDARY);
        } else {
            return Text.translatable(this.getTranslationKey(stack)).setStyle(Styles.UNIQUE);
        }
    }

    protected void generateDynamicTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        SimplySwordsClientAPI.generateDynamicTooltip(itemStack, tooltipContext, tooltip, type, "simplymore", "oracle_index:books/simplymore/weapon_types", "oracle_index:books/simplymore/unique_weapons", "", getConfigPath());
    }

    @Override
    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect." + this.asItem().getRegistryEntry().registryKey().getValue().getPath());
    }

}
