package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.entity.SpiritualGuardianEntity;
import net.rosemarythyme.simplymore.entity.SpiritualTormentorEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.SoundEventRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.AwakeningProfileRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.AwakeningApi;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;
import java.util.Optional;


public class IdolItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public static EffectSettings.HolylightSettings HOLYLIGHT = UNIQUE_CONFIG.idols.holylight;
    public static EffectSettings.DarksentSettings DARKSENT = UNIQUE_CONFIG.idols.darksent;

    private enum IdolPath {
        NONE,
        DARKSENT,
        HOLYLIGHT
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        Vec3d spawnPos = EntityUtils.rangeAroundPoint(context.origin(), context.actor(), context.actor().getYaw(), 2);

        ItemStack stack = context.actor().getStackInHand(context.hand());
        IdolTier tier = getTier(stack);

        AudioVisualUtils.playSound(context.world(), spawnPos, new Sound(SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN).setPitch(0.5f));
        AudioVisualUtils.playSound(context.world(), spawnPos, new Sound(SoundEventRegistry.SUMMON_GUARDIAN.get()));

        AudioVisualUtils.applyScreenshake(context.world(), spawnPos, context.actor(), 10, 1, 22);

        if(tier.path == IdolPath.HOLYLIGHT) {
            AudioVisualUtils.playSound(context.world(), spawnPos, new Sound(SoundRegistry.ELEMENTAL_SWORD_HOLY_ATTACK_01.get()));
            context.world().spawnEntity(new SpiritualGuardianEntity(context.actor(), spawnPos));
        } else if (tier.path == IdolPath.DARKSENT) {
            AudioVisualUtils.playSound(context.world(), spawnPos, new Sound(SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_IMPACT_01.get()).setPitch(0.5f));
            context.world().spawnEntity(new SpiritualTormentorEntity(context.actor(), spawnPos));
        }

        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return switch (getTier(stack).path) {
            case HOLYLIGHT -> HOLYLIGHT.cooldown;
            case DARKSENT -> DARKSENT.cooldown;
            default -> 0;
        };
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        ItemStack stack = context.actor().getStackInHand(context.hand());
        return stack != null && getTier(stack).hasActive && context.actor().isAlive();
    }

    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        IdolTier tier = getTier(stack);
        if(!tier.hasPassive) return;

        if(isFirstInTick) {
            if(tier.path == IdolPath.HOLYLIGHT) {
                if(MathUtils.chance(attacker, HOLYLIGHT.chance)) {
                    AttackUtils.cubeAttack(attacker, attacker.getPos(), HOLYLIGHT.blessingRange, AttackUtils.AttackTarget.ALLIES_AND_USER)
                            .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.BLESSING), HOLYLIGHT.blessingDuration, 0);

                    AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_HOLY_ATTACK_03.get()));
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.WAX_OFF, 10, 0.2f, 10);
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.RAIN, 100, 0.2f, 1);
                }
            } else if (tier.path == IdolPath.DARKSENT) {
                if(MathUtils.chance(attacker, DARKSENT.chance)) {
                    AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundRegistry.DARK_SWORD_SPELL.get()));
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.FLAME, 10, 0.2f, 0.2f);
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.FALLING_OBSIDIAN_TEAR, 100, 0.4f, 0f);

                    AttackUtils.cubeAttack(attacker, attacker.getPos(), DARKSENT.curseRange, AttackUtils.AttackTarget.ENEMIES)
                            .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.FRAGILE), DARKSENT.curseDuration, DARKSENT.curseLevel - 1);
                }
            }
        }
    }

    private enum IdolTier {
        RUPTURED_IDOL(IdolPath.NONE, false, false),
        ASCENDED_IDOL(IdolPath.HOLYLIGHT, true, false),
        TARNISHED_IDOL(IdolPath.DARKSENT, true, false),
        HOLYLIGHT(IdolPath.HOLYLIGHT, true, true),
        DARKSENT(IdolPath.DARKSENT, true, true);

        private final IdolPath path;
        private final boolean hasPassive;
        private final boolean hasActive;

        IdolTier(IdolPath path, boolean hasPassive, boolean hasActive) {
            this.path = path;
            this.hasPassive = hasPassive;
            this.hasActive = hasActive;
        }
    }

    private static IdolTier getTier(ItemStack stack) {
        if(!AwakeningApi.isAwakeningSystemEnabled()) return IdolTier.RUPTURED_IDOL;
        int level = AwakeningApi.getLevel(stack);

        if(level < 4) return IdolTier.RUPTURED_IDOL;

        Optional<Identifier> route = AwakeningApi.getFormRoute(stack);
        if(route.isEmpty()) return IdolTier.RUPTURED_IDOL;

        if(route.get().equals(AwakeningProfileRegistry.HOLYLIGHT)) {
            return level >= 8 ? IdolTier.HOLYLIGHT : IdolTier.ASCENDED_IDOL;
        } else if (route.get().equals(AwakeningProfileRegistry.DARKSENT)) {
            return level >= 8 ? IdolTier.DARKSENT : IdolTier.TARNISHED_IDOL;
        }

        return IdolTier.RUPTURED_IDOL;
    }

    public IdolItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return FootfallParticles.none();
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        IdolTier tier = getTier(itemStack);
        tooltip.add(Text.literal(""));

        if(!tier.hasPassive) {
            tooltip.add(Text.translatable("item.simplymore.ruptured_idol.tooltip1").setStyle(Styles.TEXT));
        } else {
            switch (tier.path) {
                case HOLYLIGHT -> appendHolylightTooltip(tooltip, itemStack, tier.hasActive);
                case DARKSENT -> appendDarksentTooltip(tooltip, itemStack, tier.hasActive);
            }
        }

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static void appendHolylightTooltip(List<Text> tooltip, ItemStack stack, boolean hasActive) {
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));

        if(hasActive) {
            tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
            tooltip.add(Text.translatable("item.simplymore.holylight.tooltip4").setStyle(Styles.TEXT));
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("item.simplymore.holylight.tooltip5").setStyle(Styles.TEXT));
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("item.simplymore.holylight.tooltip6").setStyle(Styles.TEXT));
            appendAbilityCooldownTooltip(tooltip, stack, HOLYLIGHT.cooldown);
        } else {
            tooltip.add(Text.translatable("item.simplymore.holylight.tooltip3").setStyle(Styles.TEXT));
        }
    }

    public static void appendDarksentTooltip(List<Text> tooltip, ItemStack stack, boolean hasActive) {
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));

        if(hasActive) {
            tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
            tooltip.add(Text.translatable("item.simplymore.darksent.tooltip4").setStyle(Styles.TEXT));
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("item.simplymore.darksent.tooltip5").setStyle(Styles.TEXT));
            appendAbilityCooldownTooltip(tooltip, stack, DARKSENT.cooldown);
        } else {
            tooltip.add(Text.translatable("item.simplymore.darksent.tooltip3").setStyle(Styles.TEXT));
        }
    }

    @Override
    protected Identifier getConfigPath() {
        return Identifier.of("simplymore.unique_effect.idols");
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {}

        public HolylightSettings holylight = new HolylightSettings();
        public DarksentSettings darksent = new DarksentSettings();

        public static class HolylightSettings extends TooltipSettings {
            @ValidatedFloat.Restrict(min = 0f, max = 1f)
            public float chance = 0.2f;
            @ValidatedInt.Restrict(min = 0)
            public int cooldown = 800;
            @ValidatedDouble.Restrict(min = 0)
            public double blessingRange = 8;
            @ValidatedInt.Restrict(min = 0)
            public int blessingDuration = 80;

            @ValidatedDouble.Restrict(min = 0)
            public double baseAuraRange = 8;
            @ValidatedDouble.Restrict(min = 0)
            public double maxAuraRange = 12;
            @ValidatedInt.Restrict(min = 0)
            public int maxStrength = 8;
            @ValidatedInt.Restrict(min = 0)
            public int guardianDuration = 600;

            @ValidatedInt.Restrict(min = 0)
            public int attackCooldown = 30;
            @ValidatedFloat.Restrict(min = 0)
            public float baseReflect = 0.4f;
            @ValidatedFloat.Restrict(min = 0)
            public float maxReflect = 0.6f;
            @ValidatedFloat.Restrict(min = 0)
            public float maxReflectDamage = 50;

            @ValidatedInt.Restrict(min = 0)
            public int effectDrainRate = 8;
            public boolean includeGlobalBlacklist = true;
            public ValidatedSet<Identifier> blacklist = ConfigUtils.createEffectList();
        }

        public static class DarksentSettings extends TooltipSettings {
            @ValidatedFloat.Restrict(min = 0f, max = 1f)
            public float chance = 0.2f;
            @ValidatedInt.Restrict(min = 0)
            public int cooldown = 800;
            @ValidatedDouble.Restrict(min = 0)
            public double curseRange = 8;
            @ValidatedInt.Restrict(min = 0)
            public int curseDuration = 100;
            @ValidatedInt.Restrict(min = 0)
            public int curseLevel = 2;


            @ValidatedDouble.Restrict(min = 0)
            public double baseAuraRange = 8;
            @ValidatedDouble.Restrict(min = 0)
            public double maxAuraRange = 12;
            @ValidatedInt.Restrict(min = 0)
            public int maxStrength = 8;
            @ValidatedInt.Restrict(min = 0)
            public int tormentorDuration = 600;
            @ValidatedInt.Restrict(min = 0)
            public int attackCooldown = 50;
            @ValidatedFloat.Restrict(min = 0)
            public float baseDamage = 12f;
            @ValidatedFloat.Restrict(min = 0)
            public float maxDamage = 24f;
            @ValidatedFloat.Restrict(min = 0)
            public float minDashSpeed = 1f;
            @ValidatedFloat.Restrict(min = 0)
            public float maxDashSpeed = 1.6f;
            @ValidatedFloat.Restrict(min = 0)
            public float knockUpHeight = 1f;
            @ValidatedFloat.Restrict(min = 0)
            public float drainDamage = 4f;
            @ValidatedInt.Restrict(min = 0)
            public int timeBeforeCharge = 20;

            @ValidatedInt.Restrict(min = 0)
            public int effectDrainRate = 8;
            public boolean includeGlobalBlacklist = true;
            public ValidatedSet<Identifier> blacklist = ConfigUtils.createEffectList();
        }
    }
}
