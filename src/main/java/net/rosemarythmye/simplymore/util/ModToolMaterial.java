package net.rosemarythmye.simplymore.util;

import com.google.common.base.Suppliers;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.sweenus.simplyswords.registry.ItemsRegistry;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    UNIQUE(4, 3270, 15.0F, 5.0F, 30, new Item[]{(Item) Items.AIR}),
    RUNIC(4, 2031, 9.0F, 5.0F, 25, new Item[]{Items.NETHERITE_INGOT});

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    private ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Item... repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = Suppliers.memoize(() -> {
            return Ingredient.ofItems(repairIngredient);
        });
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
        return (Ingredient)this.repairIngredient.get();
    }

}
