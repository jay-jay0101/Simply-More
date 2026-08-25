package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
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
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.item.interfaces.StackModifierItem;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.EffectRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class RuyiJinguBangItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility, StoppableAbilityItem, StackModifierItem, HudOverlayItem {
    public static final RuyiJinguBangItem.EffectSettings SETTINGS = UNIQUE_CONFIG.ruyi_jingu_bang;

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, SETTINGS.maxGrowth);
    }

    public RuyiJinguBangItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(isFirstInTick && MathUtils.chance(attacker, SETTINGS.chance)) {
            grow(stack, attacker);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) return false;

        ItemStack stack = context.actor().getStackInHand(context.hand());
        float size = MathUtils.getCounterComponentProgress(stack);
        if(size < 0.5f) {
            for(int i = 0; i < SETTINGS.maxGrowth / 2f; i++) {
                grow(stack, context.actor());
            }
        } else {
            stop(context.actor().getStackInHand(context.hand()), context.world(), context.actor(), 0);
        }

        return true;
    }

    @Override
    public void stop(ItemStack stack, ServerWorld world, LivingEntity user, int remainingDuration) {
        float size = MathUtils.getCounterComponentProgress(stack);
        if(AttackUtils.getUseTicksFromInfiniteDuration(remainingDuration) < 19) return;
        if(size < 0.5f) return;

        double range = (size * (SETTINGS.maxSlamRange - 5)) + 5;
        double width = (size * (SETTINGS.maxSlamWidth - 0.25)) + 0.25;
        float damage = (size * (SETTINGS.maxSlamDamage - 5)) + 5;
        int sunderedArmor = Math.round(size * SETTINGS.maxSlamSundereredArmor);

        AttackUtils.lineAttack(user, user.getEyePos(), user.getYaw(), user.getPitch(), range, width, AttackUtils.AttackTarget.ENEMIES)
                .damage(damage, AttackUtils.getHitSource(user))
                .addVelocity(0, -2, 0)
                .incrementEffect(EffectRegistry.getReference(EffectRegistry.SUNDERED_ARMOR), SETTINGS.slamSunderedArmorDuration, sunderedArmor, 100);

        AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_02.get()));
        AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_03.get()));

        AudioVisualUtils.applyScreenshake(world, user.getPos(), user, range * 2, size * 4, 20);

        AudioVisualUtils.particleLine(world, user.getPos(), user.getYaw(), user.getPitch(), range, ParticleTypes.EXPLOSION, 1, 5, 0.25f, 0);
        AudioVisualUtils.explosionBlocksInLine(world, user.getPos(), user.getYaw(), user.getPitch(), range,3, 1, 3, 2, 0.5f, user.getRandom());

        EntityUtils.cooldown(user, this, SETTINGS.cooldown, true);
        MathUtils.setCounterComponentValue(stack, 0);
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }


    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks % 20 == 0) {
            grow(stack, user);
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    public static void grow(ItemStack stack, LivingEntity user) {
        CounterComponent component = MathUtils.getCounterComponent(stack);
        if(component == null) return;
        if(component.value() >= component.max()) return;

        if(component.value() == component.max() - 1) {
            AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundRegistry.MAGIC_SWORD_ATTACK_01.get()));
        } else {
            float pitch = 0.7f + (user.getRandom().nextFloat() * 0.3f * 2);
            AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundRegistry.MAGIC_SWORD_PARRY_02.get()).setPitch(pitch));
        }

        AudioVisualUtils.particleAroundEntity(user, ParticleTypes.WAX_ON, 20, 0.25f, 0.1f);

        AudioVisualUtils.playSound(user.getWorld(), user.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_03.get()));
        MathUtils.addToCounterComponent(stack, 1);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return AttackUtils.PSEUDOINFINITE_DURATION;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WAX_ON);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclickheld").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.ruyi_jingu_bang.tooltip4").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    @Override
    public AttributeModifiersComponent getModifier(ItemStack stack, AttributeModifiersComponent base) {
        float size = MathUtils.getCounterComponentProgress(stack);

        return base.with(
                EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                new EntityAttributeModifier(
                        SimplyMore.identifier("ruyi_range"),
                        SETTINGS.maxExtraRange * size,
                        EntityAttributeModifier.Operation.ADD_VALUE
                ),
                AttributeModifierSlot.MAINHAND
        );
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.RUYI_JINGU_BANG));
        }

        @ValidatedFloat.Restrict(min = 0)
        public float chance = 0.3f;
        @ValidatedInt.Restrict(min = 0)
        public int maxGrowth = 10;
        @ValidatedDouble.Restrict(min = 0)
        public double maxExtraRange = 5;
        @ValidatedDouble.Restrict(min = 5)
        public double maxSlamRange = 40;
        @ValidatedDouble.Restrict(min = 0.25)
        public double maxSlamWidth = 5;
        @ValidatedFloat.Restrict(min = 5)
        public float maxSlamDamage = 48;
        @ValidatedInt.Restrict(min = 0)
        public int maxSlamSundereredArmor = 80;
        @ValidatedInt.Restrict(min = 0)
        public int slamSunderedArmorDuration = 200;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
    }
}
