package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.BiActiveUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class RevvengineItem extends BiActiveUniqueSwordItem implements TwoHandedWeapon, StackModifierItem, HudOverlayItem, UniqueWeaponActiveAbility {
    public static final RevvengineItem.EffectSettings SETTINGS = UNIQUE_CONFIG.revvengine;

    public RevvengineItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, SETTINGS.maxRevs);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        return onPress(context.actor().getStackInHand(context.hand()), context.world(), context.actor());
    }

    @Override
    public boolean onPress(ItemStack stack, ServerWorld world, LivingEntity user) {
        if(MathUtils.getCounterComponentProgress(stack) >= 1f) return false;

        MathUtils.addToCounterComponent(stack, 1);
        new TargetList(user).applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN), 10, 0);

        AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.MAGIC_BOW_PULL_BACK_LONG_VERSION_02.get()));
        AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.MAGIC_BOW_PULL_BACK_LONG_VERSION_02.get()).setPitch(0.5f));
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.SMOKE, 30, 0.3f, 0.3);
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.FLAME, 30, 0.3f, 0.3);

        return true;
    }

    @Override
    public int getCooldownWhenHeld(ItemStack stack, ServerWorld world, LivingEntity user) {
        return SETTINGS.cooldown;
    }

    @Override
    public int getCooldownWhenPressed(ItemStack stack, ServerWorld world, LivingEntity user) {
        return SETTINGS.revCooldown;
    }

    @Override
    public void whileHeld(ItemStack stack, ServerWorld world, LivingEntity user, int ticksUsed) {
        if(ticksUsed % SETTINGS.timeBetweenRevUses == 1) {
            if(consumeRev(user, stack, world)) return;
        }

        Vec3d position = user.getEyePos().add(MathUtils.getNormalised2dVector(user.getYaw()).multiply(SETTINGS.range));
        TargetList targets = AttackUtils.cubeAttack(user, position, SETTINGS.range, AttackUtils.AttackTarget.ENEMIES)
                .filter(PredicateUtils.IS_NOT_BLOCKING);

        if(ticksUsed % 5 == 0) {
            attack(user, stack, world, position, targets);
        }

        EntityUtils.StepUpResult result = EntityUtils.dash(user, targets.isEmpty() ? SETTINGS.velocity : SETTINGS.velocityWhileCaught, 1);
        if (result == EntityUtils.StepUpResult.TOO_TALL || result == EntityUtils.StepUpResult.NOT_ON_FLOOR) {
            user.stopUsingItem();
        }
    }

    private void attack(LivingEntity user, ItemStack stack, ServerWorld world, Vec3d position, TargetList targets) {
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.SMOKE, 3, 0.2f, 0);
        AudioVisualUtils.particleCube(world, position, ParticleTypes.SWEEP_ATTACK, 1, 0, 0);

        int revs = MathUtils.getCounterComponent(stack).value();
        targets.forceDamageWithEnchants(SETTINGS.damage, user)
                .applyEffect(StatusEffects.SLOWNESS, SETTINGS.slownessTime, 1)
                .pull(position, SETTINGS.pullStrength)
                .onEach((target) -> applyExtraHitEffects(user, target, revs, SETTINGS.damage));

        if(targets.isPopulated()) {
            AudioVisualUtils.particleAroundEntity(user, ParticleTypes.SMOKE, 10, 0.3f, 0.2f);
            AudioVisualUtils.particleAroundEntity(user, ParticleTypes.FLAME, 10, 0.3f, 0.2f);
            AudioVisualUtils.applyScreenshake(world, user.getPos(), user, 4, 2, 15);
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.MAGIC_BOW_PULL_BACK_SHORT_VERSION_03.get()).setPitch(0f));
        } else {
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.MAGIC_BOW_PULL_BACK_SHORT_VERSION_02.get()));
        }
    }

    private boolean consumeRev(LivingEntity user, ItemStack stack, ServerWorld world) {
        if(MathUtils.getCounterComponentProgress(stack) <= 0) {
            user.stopUsingItem();
            return true;
        }

        AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.MAGIC_BOW_PULL_BACK_SHORT_VERSION_02.get()));
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.SMOKE, 30, 0.3f, 0.3);
        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.FLAME, 30, 0.3f, 0.3);

        MathUtils.addToCounterComponent(stack, -1);
        return false;
    }

    @Override
    public void beforeConfirmation(ItemStack stack, ServerWorld world, LivingEntity user, int ticksUntilHeld) {
        if(ticksUntilHeld % 2 == 0) {
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.MAGIC_BOW_PULL_BACK_SHORT_VERSION_02.get()).setPitch(0.5f));
            AudioVisualUtils.particleAroundEntity(user, ParticleTypes.SMOKE, 3, 0.2f, 0);
        }
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        applyExtraHitEffects(attacker, target, MathUtils.getCounterComponent(stack).value(), (float) HelperMethods.getEntityAttackDamage(attacker));
    }

    public void applyExtraHitEffects(LivingEntity attacker, LivingEntity target, int revs, float damage) {
        float revPercentages = revs * SETTINGS.healthPercentagePerRev;
        float hpPercentage = EntityUtils.getHealthPercentage(attacker) - revPercentages;

        if (hpPercentage <= SETTINGS.inflictWoundedHealthPercentage) {
            new TargetList(target)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), UNIQUE_CONFIG.revvengine.woundedTime, 0);
        }

        if (hpPercentage <= SETTINGS.doubleDamageHealthPercentage && MathUtils.chance(attacker, SETTINGS.doubleDamageChance)) {
            AttackUtils.applyExtraDamage(target, damage, AttackUtils.getHitSource(attacker));
        }
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
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip2", MathUtils.toPercentage(SETTINGS.inflictWoundedHealthPercentage), MathUtils.toPercentage(SETTINGS.doubleDamageHealthPercentage)).setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip4", SETTINGS.maxRevs).setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.revCooldown);
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.revvengine.tooltip5").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    @Override
    public AttributeModifiersComponent getModifier(LivingEntity entity, ItemStack stack, AttributeModifiersComponent base) {
        return base.with(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(
                        SimplyMore.identifier("kickback_damage"),
                        MathHelper.lerp(1 - EntityUtils.getHealthPercentage(entity), 0, SETTINGS.maxDamageBonus),
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ),
                AttributeModifierSlot.MAINHAND
        );
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.REVVENGINE));
        }

        @ValidatedFloat.Restrict(min = 0f)
        public float maxDamageBonus = 0.4f;
        @ValidatedInt.Restrict(min = 0)
        public int woundedTime = 100;
        @ValidatedFloat.Restrict(min = 0f)
        public float doubleDamageChance = 0.15f;
        @ValidatedInt.Restrict(min = 0)
        public int maxRevs = 3;

        @ValidatedFloat.Restrict(min = 0f)
        public float inflictWoundedHealthPercentage = 0.5f;
        @ValidatedFloat.Restrict(min = 0f)
        public float doubleDamageHealthPercentage = 0.3f;
        @ValidatedFloat.Restrict(min = 0f)
        public float healthPercentagePerRev = 0.1f;

        @ValidatedInt.Restrict(min = 0)
        public int revCooldown = 200;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 40;

        @ValidatedFloat.Restrict(min = 0f)
        public float velocity = 1.2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float velocityWhileCaught = 0.6f;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 4f;
        @ValidatedFloat.Restrict(min = 0f)
        public float range = 3f;
        @ValidatedInt.Restrict(min = 0)
        public int slownessTime = 100;
        @ValidatedFloat.Restrict(min = 0f)
        public float pullStrength = 0.6f;

        @ValidatedInt.Restrict(min = 0)
        public int timeBetweenRevUses = 40;
    }
}
