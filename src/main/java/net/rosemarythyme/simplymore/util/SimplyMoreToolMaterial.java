package net.rosemarythyme.simplymore.util;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public enum SimplyMoreToolMaterial implements ToolMaterial {
    SIMPLY_MORE_UNIQUE(4, 3270, 15.0F, 5.0F, 30, "simplyswords:runic_tablet"),
    SIMPLY_MORE_RUNIC(4, 2031, 9.0F, 5.0F, 25, "minecraft:netherite_ingot"),
    SIMPLY_MORE_JOKE_UNIQUE(4, 59, 15.0F, 5.0F, 5,"minecraft:air");

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final String repairIngredient;

    SimplyMoreToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, String repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Registries.ITEM.getOrEmpty(new Identifier(repairIngredient)).orElse(Items.DIAMOND));
    }
}
