package net.rosemarythmye.simplymore.item.itemclasses.normal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.sweenus.simplyswords.util.HelperMethods;
public class Sword extends SwordItem {
    String[] repairIngredient;

    public Sword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, String... repairIngredient) {
        super(toolMaterial, attackDamage, attackSpeed, settings);

        this.repairIngredient = repairIngredient;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        List<Item> potentialIngredients = new ArrayList(List.of());
        Arrays.stream(this.repairIngredient).toList().forEach((repIngredient) -> {
            potentialIngredients.add((Item) Registries.ITEM.get(new Identifier(repIngredient)));
        });
        return potentialIngredients.contains(ingredient.getItem());
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            HelperMethods.playHitSounds(attacker, target);
        }

        return super.postHit(stack, target, attacker);
    }
}
