package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.entity.JetAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class BrassturnItem extends SimplyMoreUniqueSwordItem {
    WeaponAttributesConfig attributes = ConfigWrapper.attributes;

    public BrassturnItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        int oxidisation = getOxidisation(stack) + 1;
        saveOxidisation(stack, oxidisation);

        if (SimplyMoreHelperMethods.chance(attacker, effect.brassturn.chance)) {

            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.PLAYERS, 0.5f, 2);

            attacker.getWorld().spawnEntity(new JetAreaEffectCloudEntity(
                    target.getWorld(),
                    target.getX(),
                    target.getY(),
                    target.getZ(),
                    attacker
            ));
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (getOxidisation(itemStack) <= 0) return TypedActionResult.fail(itemStack);

        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (getOxidisation(stack) <= 0) user.stopUsingItem();

        if (remainingUseTicks % effect.brassturn.scrapeTime == 0 && user.getWorld() instanceof ServerWorld serverWorld) {
            int oxidisation = getOxidisation(stack) - 1;
            saveOxidisation(stack, oxidisation);

            if (SimplyMoreHelperMethods.chance(user, effect.brassturn.sparkChance)) {
                serverWorld.spawnParticles(ParticleTypes.WAX_ON, user.getX(), user.getY(), user.getZ(), 20, 0.5, 1, 0.5, 0.2);
                serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 0.5f, 2);
                int boxSize = 3;
                Box box = new Box(user.getX() - boxSize, user.getY() - 2, user.getZ() - boxSize, user.getX() + boxSize, user.getY() + boxSize, user.getZ() + boxSize);
                List<LivingEntity> livingEntities = user.getWorld().getNonSpectatingEntities(LivingEntity.class, box);

                for (LivingEntity livingEntity : livingEntities) {
                    if (livingEntity == user || SimplyMoreHelperMethods.checkFriendlyFire(livingEntity, user)) {
                        continue;
                    }

                    livingEntity.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.STUNNED), effect.brassturn.stunTime, 0));
                }

            } else {
                serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.PLAYERS, 1f, 1);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 999999;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public static int getOxidisation(ItemStack stack) {
        CounterComponent oxidisation = SimplyMoreHelperMethods.getCounterComponent(stack);

        if(oxidisation == null) {
            return 16;
        }

        else return oxidisation.value();
    }

    public static void saveOxidisation(ItemStack stack, int oxidisation) {
        SimplyMoreHelperMethods.setCounterComponent(stack,
                new CounterComponent(0, 16, 0).set(oxidisation));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, ParticleTypes.ASH);
        applyAttackSpeed(stack);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public void applyAttackSpeed(ItemStack stack) {
        // Calculate modifier
        float oxidisationAmount = getOxidisation(stack) / 16f;
        double minimumModifier = 4 + attributes.uniqueWeaponsSwingSpeed.brassturn_attack_speed - 0.6;
        double attackSpeedModifier = minimumModifier * -oxidisationAmount;

        // Apply
        AttributeModifiersComponent modifiers = stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);

        if(modifiers == null) {
            modifiers = AttributeModifiersComponent.builder().build();
        }

        stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers.with(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(
                        Identifier.of(SimplyMore.ID, "oxidisation"),
                        attackSpeedModifier,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        ));
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip5").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }


    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.BRASSTURN));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedInt.Restrict(min = 0)
        public int stunTime = 15;
        @ValidatedInt.Restrict(min = 1)
        public int scrapeTime = 5;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float sparkChance = 0.25f;
    }
}
