package net.rosemarythyme.simplymore.item.uniques.joke;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class ThePanItem extends SimplyMoreSwordItem {

    protected static final UniqueEffectConfig UNIQUE_EFFECT = ConfigWrapper.unique;

    public ThePanItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed, new Item.Settings().fireproof().rarity(Rarity.COMMON));
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem() == Items.IRON_INGOT || super.canRepair(stack, ingredient);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient) return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_EFFECT.the_pan.chance)) {
            AttackUtils.knockback(attacker, target, UNIQUE_EFFECT.the_pan.knockbackStrength);
            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundEvents.BLOCK_ANVIL_PLACE));
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip2").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.THE_PAN));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.3f;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockbackStrength = 20f;
    }

    protected void generateDynamicTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        SimplySwordsClientAPI.generateDynamicTooltip(itemStack, tooltipContext, tooltip, type, "simplymore", "oracle_index:books/simplymore/weapon_types", "oracle_index:books/simplymore/unique_weapons", "", getConfigPath());
    }

    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect.the_pan");
    }
}
