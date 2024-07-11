//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.rosemarythmye.simplymore.item.itemclasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sweenus.simplyswords.api.SimplySwordsAPI;
import net.sweenus.simplyswords.util.HelperMethods;

public class UniqueSword extends SwordItem {
    String iRarity = "UNIQUE";
    String[] repairIngredient;

    public UniqueSword(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.fireproof());
        this.repairIngredient = new String[]{"simplyswords:runic_tablet"};
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        SimplySwordsAPI.inventoryTickGemSocketLogic(stack, world, entity, 50, 50);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        List<Item> potentialIngredients = new ArrayList(List.of());
        Arrays.stream(this.repairIngredient).toList().forEach((repIngredient) -> {
            potentialIngredients.add((Item) Registries.ITEM.get(new Identifier(repIngredient)));
        });
        return potentialIngredients.contains(ingredient.getItem());
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        SimplySwordsAPI.onClickedGemSocketLogic(stack, otherStack, player);
        return false;
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {
            HelperMethods.playHitSounds(attacker, target);
            SimplySwordsAPI.postHitGemSocketLogic(stack, target, attacker);
        }

        return super.postHit(stack, target, attacker);
    }

    public Text getName(ItemStack stack) {
        Style COMMON = HelperMethods.getStyle("common");
        Style UNIQUE = HelperMethods.getStyle("unique");
        Style LEGENDARY = HelperMethods.getStyle("legendary");
        if (true) {
            return this.iRarity.equals("UNIQUE") ? Text.translatable(this.getTranslationKey(stack)).setStyle(UNIQUE) : Text.translatable(this.getTranslationKey(stack)).setStyle(COMMON);
        } else {
            this.iRarity = "LEGENDARY";
            return Text.translatable(this.getTranslationKey(stack)).setStyle(LEGENDARY);
        }
    }

    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        SimplySwordsAPI.appendTooltipGemSocketLogic(itemStack, tooltip);
    }
}
