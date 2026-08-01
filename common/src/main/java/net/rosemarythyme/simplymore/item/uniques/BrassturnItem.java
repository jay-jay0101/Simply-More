package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.config.WeaponAttributesConfig;
import net.rosemarythyme.simplymore.entity.JetstreamEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class BrassturnItem extends SimplyMoreUniqueSwordItem implements StackModifierItem {
    WeaponAttributesConfig attributes = ConfigWrapper.attributes;

    public BrassturnItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, 16, 16);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        MathUtils.addToCounterComponent(stack, 1);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.brassturn.chance)) {
            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundEvents.ENTITY_ZOMBIE_INFECT, 0.5f, 2f));

            AttackUtils.spawnAbility(new JetstreamEntity(attacker, attacker.getPos()), attacker);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (MathUtils.getCounterComponent(stack).value() <= 0) return TypedActionResult.fail(stack);

        return AttackUtils.holdToUse(user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (MathUtils.getCounterComponent(stack).value() <= 0) {
            user.stopUsingItem();
            return;
        }

        if (remainingUseTicks % UNIQUE_CONFIG.brassturn.scrapeTime == 0) {
            MathUtils.addToCounterComponent(stack, -1);

            if (MathUtils.chance(user, UNIQUE_CONFIG.brassturn.sparkChance)) {
                AudioVisualUtils.particleAroundEntity(user, ParticleTypes.WAX_ON, 20, 0.5, 0.2f);
                AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundEvents.BLOCK_BEACON_POWER_SELECT, 0.5f, 2f));

                AttackUtils.cubeAttack(user, user.getPos(), 3, AttackUtils.AttackTarget.ENEMIES)
                        .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUNNED), UNIQUE_CONFIG.brassturn.stunTime, 0);
            } else {
                AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundEvents.ITEM_AXE_SCRAPE));
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return AttackUtils.INFINITE_DURATION;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public AttributeModifiersComponent getModifier(ItemStack stack, AttributeModifiersComponent base) {
        float oxidisationAmount = MathUtils.getCounterComponent(stack).value() / 16f;
        double minimumModifier = 4 + attributes.uniqueWeaponsSwingSpeed.brassturn_attack_speed - 0.6;

        return base.with(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(
                        SimplyMore.identifier("oxidisation"),
                        minimumModifier * -oxidisationAmount,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        );
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip5").setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.BRASSTURN));
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
