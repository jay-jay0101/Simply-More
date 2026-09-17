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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.config.ConfigWrapper;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CogRotationComponent;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class BrassturnItem extends SimplyMoreUniqueSwordItem implements StackModifierItem, UniqueWeaponActiveAbility, HudOverlayItem {
    public static final BrassturnItem.EffectSettings SETTINGS = UNIQUE_CONFIG.brassturn;
    private static final float GEAR_SPEED = 12f;

    public BrassturnItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, 16, 16);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(isFirstInTick) {
            changeOxidation(stack, 1, attacker);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) return false;
        changeOxidation(context.actor().getStackInHand(context.hand()), -5, context.actor());

        return true;
    }

    public void changeOxidation(ItemStack stack, int change, LivingEntity entity) {
        float oxidation = MathUtils.getCounterComponentProgress(stack);
        MathUtils.addToCounterComponent(stack, change);

        CogRotationComponent rot = stack.getOrDefault(ItemComponentRegistry.COG_ROTATION.get(), CogRotationComponent.DEFAULT);
        stack.set(ItemComponentRegistry.COG_ROTATION.get(), rot.update(entity.getWorld().getTime(), oxidation, entity.isUsingItem()));
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() &&
                MathUtils.getCounterComponentProgress(context.actor().getStackInHand(context.hand())) > 0;
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));

        ItemStack stack = user.getStackInHand(hand);
        long time = user.getWorld().getTime();

        stack.set(ItemComponentRegistry.COG_ROTATION.get(), getCogRotation(stack).update(time, MathUtils.getCounterComponentProgress(stack), 1, true));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return 100;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(world.isClient()) return;

        if (MathUtils.getCounterComponent(stack).value() <= 0) {
            user.stopUsingItem();
            return;
        }

        if (remainingUseTicks % UNIQUE_CONFIG.brassturn.scrapeTime == 0) {
            changeOxidation(stack, -1, user);
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundEvents.ITEM_AXE_SCRAPE));
        }

        int useTime = AttackUtils.getUseTicksFromInfiniteDuration(remainingUseTicks);
        float extraSpeedMult = MathUtils.clampedLerp(useTime, 0, SETTINGS.scrapeTime * 16, 1f, GEAR_SPEED);
        stack.set(ItemComponentRegistry.COG_ROTATION.get(), getCogRotation(stack).update(world.getTime(), MathUtils.getCounterComponentProgress(stack), extraSpeedMult, true));

        if(useTime > 10) {
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.SWING_WOOSH.get()).setPitch(user.getRandom().nextBetween(8, 12) / 10f));
            AttackUtils.cuboidAttack(user, user.getEyePos(), 1.75f, 1.5f, AttackUtils.AttackTarget.ENEMIES)
                    .damage(MathUtils.clampedLerp(extraSpeedMult, 0, GEAR_SPEED, SETTINGS.minDamage, SETTINGS.maxDamage), AttackUtils.getHitSource(user))
                    .knockback(user, MathUtils.clampedLerp(extraSpeedMult, 0, GEAR_SPEED, SETTINGS.minKnockback, SETTINGS.maxKnockback));
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        stack.set(ItemComponentRegistry.COG_ROTATION.get(), getCogRotation(stack).update(world.getTime(), MathUtils.getCounterComponentProgress(stack), 1, false));
    }

    private CogRotationComponent getCogRotation(ItemStack stack) {
        return stack.getOrDefault(ItemComponentRegistry.COG_ROTATION.get(), CogRotationComponent.DEFAULT);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return AttackUtils.PSEUDOINFINITE_DURATION;
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
    public AttributeModifiersComponent getModifier(LivingEntity entity, ItemStack stack, AttributeModifiersComponent base) {
        float oxidisationAmount = MathUtils.getCounterComponentProgress(stack);
        float stackAttackSpeed = (4 + ConfigWrapper.ATTRIBUTES.uniqueWeaponsSwingSpeed.brassturn_attack_speed) * AwakeningApi.getAttackSpeedMultiplier(stack);
        float maximumModifier = stackAttackSpeed - 0.6f;

        return base.with(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(
                        SimplyMore.identifier("oxidisation"),
                        maximumModifier * -oxidisationAmount,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        );

    }

    public static boolean isSecondaryEffectUnlocked(ItemStack stack) {
        return !AwakeningApi.isAwakeningSystemEnabled() || AwakeningApi.getLevel(stack) >= 4;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        boolean isEmpowered = isSecondaryEffectUnlocked(itemStack);

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));

        if(isEmpowered) {
            tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip3").setStyle(Styles.TEXT));
        } else {
            tooltip.add(Text.translatable("item.simplymore.brassturn.unempowered_tooltip").setStyle(Styles.TEXT));
        }

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip4").setStyle(Styles.TEXT));

        if(isEmpowered) {
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("item.simplymore.brassturn.tooltip5").setStyle(Styles.TEXT));
        }

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.BRASSTURN));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedInt.Restrict(min = 1)
        public int scrapeTime = 5;
        @ValidatedFloat.Restrict(min = 0)
        public float minKnockback = 0.4f;
        @ValidatedFloat.Restrict(min = 0)
        public float maxKnockback = 1.4f;

        @ValidatedFloat.Restrict(min = 0)
        public float minDamage = 5;
        @ValidatedFloat.Restrict(min = 0)
        public float maxDamage = 12;
    }
}
