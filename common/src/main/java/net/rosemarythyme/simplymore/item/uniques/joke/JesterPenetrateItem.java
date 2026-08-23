package net.rosemarythyme.simplymore.item.uniques.joke;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class JesterPenetrateItem extends SimplyMoreSwordItem {

    public JesterPenetrateItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed, new Item.Settings().fireproof().rarity(Rarity.COMMON));
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isIn(ItemTags.WOOL) || super.canRepair(stack, ingredient);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!(entity instanceof LivingEntity livingEntity)) return;

        if (world.getTime() % 20 == 0 && EntityUtils.isHolding(livingEntity, stack)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, 0));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20, 1));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, 0));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.jester_penetrate.tooltip3").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    protected void generateDynamicTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        SimplySwordsClientAPI.generateDynamicTooltip(itemStack, tooltipContext, tooltip, type, "simplymore", "oracle_index:books/simplymore/weapon_types", "oracle_index:books/simplymore/unique_weapons", "", getConfigPath());
    }

    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect"); // Jester Penetrate has no configs
    }
}
