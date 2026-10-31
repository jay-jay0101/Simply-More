package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.FlowerFieldEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.CounterComponent;
import net.rosemarythyme.simplymore.item.interfaces.HudOverlayItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.InventoryUtils;
import net.rosemarythyme.simplymore.util.ItemStackUtils;
import net.rosemarythyme.simplymore.util.SummonUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class PerforiscusItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, HudOverlayItem, UniqueWeaponActiveAbility {
    public static final PerforiscusItem.EffectSettings SETTINGS = UNIQUE_CONFIG.perforiscus;

    public PerforiscusItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public CounterComponent getDefaultCounterComponent() {
        return new CounterComponent(0, 100, 0);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(isFirstInTick && ItemStackUtils.getCounterComponentProgress(stack) < 1) {
            float extraGrowth = SETTINGS.growthPerConsecutiveHit * (consecutiveHits - 1);
            ItemStackUtils.addToCounterComponent(stack, (int) Math.min(SETTINGS.maxGrowth, SETTINGS.minGrowth + extraGrowth));

            AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundEvents.BLOCK_CHORUS_FLOWER_GROW).randomisePitch(0.8f, 1.2f, attacker.getRandom()));
            AudioVisualUtils.particleRing(world, attacker.getEyePos().offset(Direction.DOWN, 0.4f), ParticleTypes.CHERRY_LEAVES, 0.4f, 20);
        }
    }

    @Override
    public void whenHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, float amount, DamageSource source) {
        if(ItemStackUtils.getCounterComponentProgress(stack) > 0f) {
            ItemStackUtils.addToCounterComponent(stack, -PerforiscusItem.SETTINGS.growthLoss);
            AudioVisualUtils.playSound(world, target.getPos(), new Sound(SoundRegistry.ELEMENTAL_BOW_EARTH_SHOOT_IMPACT_02.get()).randomisePitch(0.8f, 1.2f, target.getRandom()));
            AudioVisualUtils.particleRing(world, target.getEyePos().offset(Direction.DOWN, 0.4f), ParticleTypes.DAMAGE_INDICATOR, 0.4f, 20);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(SummonUtils.spawnAbility(new FlowerFieldEntity(context.actor(), context.origin()), context.actor(), true)) {
            AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.ELEMENTAL_BOW_ICE_SHOOT_IMPACT_01.get()));
            AudioVisualUtils.particleAroundEntity(context.actor(), ParticleTypes.SPORE_BLOSSOM_AIR, 40, 0.25, 0.2f);

            ItemStackUtils.addToCounterComponent(context.actor().getStackInHand(context.hand()), -SETTINGS.summonGrowthUsage);
            return true;
        }

        return false;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && ItemStackUtils.getCounterComponent(context.actor().getStackInHand(context.hand())).value() >= SETTINGS.summonGrowthUsage;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.CHERRY_LEAVES);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if(!(entity instanceof LivingEntity livingEntity)) return;
        if(!InventoryUtils.isHoldingInMainHand(livingEntity, stack)) return;

        BloomStatuses.get(ItemStackUtils.getCounterComponentProgress(stack)).apply(livingEntity);
    }

    private record BloomStatuses(int interval, int healthBoostAmplifier, int resistanceAmplifier) {
        private static final int DISABLED = -1;

        private static BloomStatuses get(float bloom) {
            if(bloom >= 0.75f) return new BloomStatuses(25, 4, 1);
            if(bloom >= 0.6f) return new BloomStatuses(25, 3, 0);
            if(bloom >= 0.5f) return new BloomStatuses(50, 2, 0);
            if(bloom >= 0.4f) return new BloomStatuses(50, 1, DISABLED);
            if(bloom >= 0.25f) return new BloomStatuses(100, 0, DISABLED);
            if(bloom > 0f) return new BloomStatuses(100, DISABLED, DISABLED);
            return new BloomStatuses(DISABLED, DISABLED, DISABLED);
        }

        private void apply(LivingEntity entity) {
            TargetList target = new TargetList(entity);

            if(interval > 0 && entity.age % interval == 0) {
                entity.heal(1f);
            }

            if(healthBoostAmplifier > DISABLED) {
                target.applyEffect(StatusEffects.HEALTH_BOOST, 20, healthBoostAmplifier);
            }

            if(resistanceAmplifier > DISABLED) {
                target.applyEffect(StatusEffects.RESISTANCE, 20, resistanceAmplifier);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.perforiscus.tooltip4", SETTINGS.summonGrowthUsage).setStyle(Styles.TEXT));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.PERFORISCUS));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int minGrowth = 5;
        @ValidatedInt.Restrict(min = 0)
        public int maxGrowth = 12;
        @ValidatedFloat.Restrict(min = 0f)
        public float growthPerConsecutiveHit = 1f;
        @ValidatedInt.Restrict(min = 0)
        public int growthLoss = 8;
        @ValidatedInt.Restrict(min = 0)
        public int summonGrowthUsage = 75;
        @ValidatedInt.Restrict(min = 0)
        public int duration = 300;
        @ValidatedFloat.Restrict(min = 0f)
        public float range = 5f;
        @ValidatedInt.Restrict(min = 1)
        public float healInterval = 7;
    }
}
