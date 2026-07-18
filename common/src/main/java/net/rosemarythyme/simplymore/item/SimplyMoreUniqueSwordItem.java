package net.rosemarythyme.simplymore.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.*;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public abstract class SimplyMoreUniqueSwordItem extends UniqueSwordItem implements Weapon {
    protected static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.unique;
    private final SwordType swordType;

    public SimplyMoreUniqueSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, SwordType swordType, Settings settings) {
        super(toolMaterial, settings.fireproof().attributeModifiers(
                SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));

        this.swordType = swordType;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(swordType == SwordType.LANCE) {
            Weapon.tryGrantLanceEffect(attacker, target);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        FootfallParticles footfallParticles = getFootfalls();
        if(footfallParticles.hasParticles()) {
            AudioVisualUtils.handleFootfalls(entity, stack, world, footfallParticles);
        }

        if(stack.getItem() instanceof StackModifierItem modifierItem) {
            modifierItem.applyStackModifier(stack);
        }

        if(!(entity instanceof PlayerEntity player)) return;

        if(stack.getItem() instanceof CooldownOnUnselected unselectableItem) {
            unselectableItem.detectUnselect(player, stack, selected, unselectableItem instanceof TwoHandedWeapon);
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, 0);
    }

    @Override
    public SwordType getSwordType() {
        return swordType;
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.getItem() instanceof LegendaryItem) {
            return Text.translatable(stack.getTranslationKey()).setStyle(Styles.LEGENDARY);
        }

        return Text.translatable(stack.getTranslationKey()).setStyle(Styles.UNIQUE);
    }

    @Override
    protected void generateDynamicTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        SimplySwordsClientAPI.generateDynamicTooltip(itemStack, tooltipContext, tooltip, type, "simplymore", "oracle_index:books/simplymore/weapon_types", "oracle_index:books/simplymore/unique_weapons", "", getConfigPath());
    }

    @Override
    protected Identifier getConfigPath() {
        Identifier id = Registries.ITEM.getEntry(this)
                .getKey().map(RegistryKey::getValue).orElse(SimplyMore.identifier("empty"));

        return Identifier.of("simplymore.unique_effect." + id.getPath());
    }

    public abstract FootfallParticles getFootfalls();
}
