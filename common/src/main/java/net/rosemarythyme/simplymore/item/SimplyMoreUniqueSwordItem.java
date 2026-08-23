package net.rosemarythyme.simplymore.item;

import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.UniqueEffectConfig;
import net.rosemarythyme.simplymore.item.components.ConsecutiveHitsComponent;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.client.api.SimplySwordsClientAPI;
import net.sweenus.simplyswords.item.UniqueSwordItem;

import java.util.List;

public abstract class SimplyMoreUniqueSwordItem extends UniqueSwordItem {
    protected static final UniqueEffectConfig UNIQUE_CONFIG = ConfigWrapper.UNIQUE;

    public SimplyMoreUniqueSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, new Item.Settings().fireproof().rarity(Rarity.EPIC)
                .attributeModifiers(SwordItem.createAttributeModifiers(toolMaterial, attackDamage, attackSpeed)));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        FootfallParticles footfallParticles = getFootfalls();
        if(footfallParticles.hasParticles()) {
            AudioVisualUtils.handleFootfalls(entity, stack, world, footfallParticles);
        }

        if(stack.getItem() instanceof StackModifierItem modifierItem) {
            StackModifierItem.applyStackModifier(stack, modifierItem);
        }

        ComponentType<ConsecutiveHitsComponent> hits = ItemComponentRegistry.CONSECUTIVE_HITS.get();
        ConsecutiveHitsComponent component = stack.get(hits);
        if(component != null) {
            if(component.swungThisFrame() && !component.hitThisFrame()) {
                stack.remove(hits);
            } else if (component.swungThisFrame()) {
                stack.set(hits, new ConsecutiveHitsComponent(component.num(), false, false));
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void onSwing(ItemStack stack, ServerWorld world, LivingEntity user) {
        ComponentType<ConsecutiveHitsComponent> hits = ItemComponentRegistry.CONSECUTIVE_HITS.get();
        ConsecutiveHitsComponent component = stack.get(hits);

        if(component == null) component = ConsecutiveHitsComponent.DEFAULT;
        stack.set(hits, new ConsecutiveHitsComponent(component.num(), true, component.hitThisFrame()));
    }

    @Override
    public final boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(!(attacker.getWorld() instanceof ServerWorld world)) return super.postHit(stack, target, attacker);
        if(AwakeningApi.isAwakeningSystemEnabled() && !AwakeningApi.isAbilityUnlocked(stack)) return super.postHit(stack, target, attacker);

        ComponentType<ConsecutiveHitsComponent> hits = ItemComponentRegistry.CONSECUTIVE_HITS.get();
        ConsecutiveHitsComponent component = stack.get(hits);
        if(component == null) component = ConsecutiveHitsComponent.DEFAULT;

        if(!component.hitThisFrame()) {
            int newNum = component.num() + 1;
            stack.set(hits, new ConsecutiveHitsComponent(newNum, true, true));

            onHit(stack, target, attacker, world, newNum, true);
        } else {
            onHit(stack, target, attacker, world, component.num(), false);
        }

        return super.postHit(stack, target, attacker);
    }

    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {}

    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, 0);
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
