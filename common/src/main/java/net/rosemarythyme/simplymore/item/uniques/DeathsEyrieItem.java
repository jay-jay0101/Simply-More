package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.CrowEmitterEntity;
import net.rosemarythyme.simplymore.entity.CrowEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.item.interfaces.StoppableAbilityItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class DeathsEyrieItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, HudOverlayItem, UniqueWeaponActiveAbility, StoppableAbilityItem {
    public static final DeathsEyrieItem.EffectSettings SETTINGS = UNIQUE_CONFIG.deaths_eyrie;
    public static final int CROW_CHARGE_TIME = 8;

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, SETTINGS.maxCrows, 1);
    }

    public DeathsEyrieItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(!isFirstInTick) return;
        SummonUtils.getOwnedEntities(attacker, CrowEntity.class).forEach(c -> c.startAttack(target));

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.deaths_eyrie.chance) && ItemStackUtils.getCounterComponentProgress(stack) < 1f) {
            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundRegistry.DARK_SWORD_ENCHANT.get()));
            ItemStackUtils.addToCounterComponent(stack, 1);
        }
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && ItemStackUtils.getCounterComponentProgress(context.actor().getStackInHand(context.hand())) > 0f;
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        spawnAbility(context.actor(), context.actor().getStackInHand(context.hand()), 1);
        return true;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingTicks) {
        if(world.isClient) return;

        int maxUseTicks = ItemStackUtils.getCounterComponent(stack).value() * CROW_CHARGE_TIME + 1;
        int minRemainingTicks = MathUtils.PSEUDOINFINITE_DURATION - maxUseTicks;
        if (remainingTicks % CROW_CHARGE_TIME == 0 && remainingTicks >= minRemainingTicks) {
            AudioVisualUtils.playSound(world, user.getPos(), new Sound(SoundRegistry.DARK_SWORD_UNFOLD.get()).setVolume(0.5f).randomisePitch(0.4f, 1.6f, user.getRandom()));
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    protected int getUniqueWeaponMaxUseTime(ItemStack stack, LivingEntity user) {
        return MathUtils.PSEUDOINFINITE_DURATION;
    }

    private void spawnAbility(LivingEntity owner, ItemStack stack, int crows) {
        float yaw = owner.getYaw();
        Vec3d pos = EntityUtils.rangeAroundPoint(owner.getEyePos(), owner, yaw + 180f, 2f);

        ItemStackUtils.addToCounterComponent(stack, -crows);
        SummonUtils.spawnAbility(new CrowEmitterEntity(owner, pos, yaw, crows), owner);

        AudioVisualUtils.playSound(owner.getWorld(), pos, new Sound(SoundRegistry.DARK_SWORD_BREAKS.get()));
    }

    @Override
    public void stop(ItemStack stack, ServerWorld world, LivingEntity user, int remainingDuration) {
        if(stack == null) return;

        int crows = Math.min(MathUtils.getUseTicksFromInfiniteDuration(remainingDuration) / CROW_CHARGE_TIME, ItemStackUtils.getCounterComponent(stack).value());
        spawnAbility(user, stack, crows);

        EntityUtils.cooldown(user, this, SETTINGS.cooldown, true);
    }

    @Override
    public TypedActionResult<ItemStack> startPlayerAbility(World world, PlayerEntity user, Hand hand) {
        if(!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(user.getStackInHand(hand));
        return AttackUtils.holdToUse(serverWorld, user, hand);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (world.isClient()) return;
        if (!(entity instanceof LivingEntity owner)) return;
        if (!InventoryUtils.isHoldingInMainHand(owner, stack) || !ItemStackUtils.isStackAwakened(stack)) return;

        SummonUtils.ensureEnumeratedEntities(owner, CrowEntity.class, ItemStackUtils.getCounterComponent(stack).value());
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.WARPED_SPORE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip3", SETTINGS.maxCrows).setStyle(Styles.TEXT));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.deaths_eyrie.tooltip4").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.DEATHS_EYRIE));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedInt.Restrict(min = 0)
        public int maxCrows = 5;
        @ValidatedFloat.Restrict(min = 0f)
        public float crowDamage = 1f;
        @ValidatedInt.Restrict(min = 0)
        public int woundedDuration = 10;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 120;
        @ValidatedInt.Restrict(min = 0)
        public int durationPerCrow = 30;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
