package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.AbstractPlanetaryEntity;
import net.rosemarythyme.simplymore.entity.MoonEntity;
import net.rosemarythyme.simplymore.entity.SunEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.components.DayTimeComponent;
import net.rosemarythyme.simplymore.registry.item.ItemComponentRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class TimekeeperItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public static final EffectSettings SETTINGS = UNIQUE_CONFIG.timekeeper;

    public TimekeeperItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(isFirstInTick && MathUtils.chance(attacker, SETTINGS.chance)) {
            AttackUtils.getOwnedAbilities(attacker, AbstractPlanetaryEntity.class)
                    .forEach((planet) -> planet.setAge(planet.getAge() - SETTINGS.orbitDurationBonus));

            AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundRegistry.ACTIVATE_PLINTH_03.get()).setVolume(0.5f).randomisePitch(1.8f, 2f, attacker.getRandom()));

            switch (getForm(stack)) {
                case DAY -> {
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.WAX_ON, 10, 0.2f, 0.5f);
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.FLASH, 1, 0f, 0f);
                    AttackUtils.cubeAttack(attacker, attacker.getPos(), 35, AttackUtils.AttackTarget.ENEMIES)
                            .applyEffect(StatusEffects.GLOWING, SETTINGS.effectTime, 0);
                }
                case NIGHT -> {
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.WAX_OFF, 10, 0.2f, 0.5f);
                    AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.FLASH, 1, 0f, 0f);
                    AttackUtils.cubeAttack(attacker, attacker.getPos(), 35, AttackUtils.AttackTarget.ENEMIES)
                            .applyEffect(StatusEffects.BLINDNESS, SETTINGS.effectTime, 0)
                            .applyEffect(StatusEffects.DARKNESS, SETTINGS.effectTime, 0);
                }
                case TIMELESS -> AudioVisualUtils.particleAroundEntity(attacker, ParticleTypes.SQUID_INK, 10, 0.2f, 0.5f);
            }
        }
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive() && AttackUtils.getOwnedEntities(context.actor(), AbstractPlanetaryEntity.class).isEmpty();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.ELEMENTAL_BOW_SCIFI_SHOOT_IMPACT_02.get()));
        AudioVisualUtils.applyScreenshake(context.world(), context.origin(), context.actor(), 12, 1.3f, 20);
        switch (getForm(context.actor().getStackInHand(context.hand()))) {
            case DAY -> AttackUtils.spawnAbility(new SunEntity(context.actor(), true), context.actor());
            case NIGHT -> AttackUtils.spawnAbility(new MoonEntity(context.actor(), true), context.actor());
            case TIMELESS -> {
                AttackUtils.spawnAbility(new SunEntity(context.actor(), false), context.actor());
                AttackUtils.spawnAbility(new MoonEntity(context.actor(), false), context.actor());
            }
        }

        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        DayTimeComponent.DayTimeForm form = getForm(itemStack);

        switch (form) {
            case DAY -> appendTimedTooltip(tooltip, "day", true);
            case NIGHT -> appendTimedTooltip(tooltip, "night", true);
            case TIMELESS -> appendTimedTooltip(tooltip, "timeless", false);
        }

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    private void appendTimedTooltip(List<Text> tooltip, String key, boolean hasExtraLine) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_" + key + ".tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_" + key + ".tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_" + key + ".tooltip3").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.timekeeper_" + key + ".tooltip4").setStyle(Styles.TEXT));

        if(hasExtraLine) {
            tooltip.add(Text.literal(""));
            tooltip.add(Text.translatable("item.simplymore.timekeeper_" + key + ".tooltip5").setStyle(Styles.TEXT));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if(world.isClient()) return;
        stack.set(ItemComponentRegistry.DAYTIME.get(), new DayTimeComponent(DayTimeComponent.DayTimeForm.getFromWorld(world)));
    }

    public DayTimeComponent.DayTimeForm getForm(ItemStack stack) {
        DayTimeComponent component = stack.get(ItemComponentRegistry.DAYTIME.get());
        if(component == null) return DayTimeComponent.DayTimeForm.TIMELESS;

        return component.form();
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.TIMEKEEPER));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int orbitDuration = 300;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.35f;
        @ValidatedInt.Restrict(min = 0)
        public int orbitDurationBonus = 40;
        @ValidatedFloat.Restrict(min = 0f)
        public float orbitDamage = 15f;
        @ValidatedFloat.Restrict(min = 0f)
        public float orbitKnockback = 1.2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float unempoweredOrbitDamage = 10f;
        @ValidatedFloat.Restrict(min = 0f)
        public float orbitAuraDamage = 9f;
        @ValidatedFloat.Restrict(min = 0f)
        public float orbitAuraRange = 2.5f;
        @ValidatedInt.Restrict(min = 0)
        public int orbitEffectTime = 160;
        @ValidatedFloat.Restrict(min = 0)
        public float orbitSpeed = 1.8f;
        @ValidatedFloat.Restrict(min = 0)
        public float orbitRange = 5f;
    }
}
