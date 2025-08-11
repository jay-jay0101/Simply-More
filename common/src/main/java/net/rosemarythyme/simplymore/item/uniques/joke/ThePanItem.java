package net.rosemarythyme.simplymore.item.uniques.joke;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.item.SimplyMoreSwordItem;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class ThePanItem extends SimplyMoreSwordItem {

    protected static UniqueEffectConfig effect = ConfigWrapper.unique;

    public ThePanItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
        this.repairIngredient = new String[]{"minecraft:iron_ingot"};
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) {
            return super.postHit(stack, target, attacker);
        }

        if (!SimplyMoreHelperMethods.chance(attacker, effect.the_pan.chance)) {
            return super.postHit(stack, target, attacker);
        }

        Vec3d targetPosition = target.getPos();
        Vec3d attackerPosition = attacker.getPos();

        double deltaX = targetPosition.getX() - attackerPosition.getX();
        double deltaZ = targetPosition.getZ() - attackerPosition.getZ();

        double distance = Math.hypot(deltaX, deltaZ);

        if (distance == 0) {
            return super.postHit(stack, target, attacker);
        }

        float knockbackStrength = effect.the_pan.knockbackStrength;

        double normalizedDeltaX = deltaX / distance;
        double normalizedDeltaZ = deltaZ / distance;

        target.setVelocity(normalizedDeltaX * knockbackStrength, 0.2, normalizedDeltaZ * knockbackStrength);
        target.velocityModified = true;

        attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1, 1);

        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.the_pan.tooltip2").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.THE_PAN));
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
