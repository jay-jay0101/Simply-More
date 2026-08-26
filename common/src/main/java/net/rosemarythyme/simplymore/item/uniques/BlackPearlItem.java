package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.SimplyMore;
import net.rosemarythyme.simplymore.entity.CannonballEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.SoundEventRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.ConfigUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class BlackPearlItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public static final BlackPearlItem.EffectSettings SETTINGS = UNIQUE_CONFIG.black_pearl;

    public BlackPearlItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if (MathUtils.chance(attacker, SETTINGS.chance)) {
            List<StatusEffectInstance> possibleEffects = target.getStatusEffects().stream()
                    .filter(effect -> effect.getEffectType().value().getCategory() == StatusEffectCategory.BENEFICIAL)
                    .filter(effect -> !effect.getEffectType().value().isInstant())
                    .filter(effect -> !ConfigUtils.isEffectBlacklisted(effect.getEffectType().value(), SETTINGS.blacklist, SETTINGS.includeGlobalBlacklist))
                    .toList();

            if (!possibleEffects.isEmpty()) {
                StatusEffectInstance plunderedEffect = possibleEffects.get(attacker.getRandom().nextInt(possibleEffects.size()));

                int amplifier = Math.min(plunderedEffect.getAmplifier(), SETTINGS.maxLevel - 1);
                int duration = Math.min(plunderedEffect.getDuration(), SETTINGS.maxDuration);

                if (plunderedEffect.getDuration() == StatusEffectInstance.INFINITE) {
                    duration = SETTINGS.maxDuration;
                }

                StatusEffectInstance newEffect = new StatusEffectInstance(plunderedEffect.getEffectType(), duration, amplifier);

                attacker.addStatusEffect(newEffect);
                target.removeStatusEffect(plunderedEffect.getEffectType());

                AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundEventRegistry.COINS.get()));
                AudioVisualUtils.particleAroundEntity(target, ParticleTypes.LANDING_HONEY, 30, 0.3, 0);
            }
        }
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        Vec3d velocity = MathUtils.getDirectionalVector(context.actor().getYaw(), context.actor().getPitch());

        context.actor().addVelocity(MathUtils.getDirectionalVector(context.actor().getYaw(), context.actor().getPitch()).multiply(-SETTINGS.cannonballRecoil));
        context.actor().velocityModified = true;

        AudioVisualUtils.applyScreenshake(context.world(), context.origin(), context.actor(), 0.5f, 1.5f, 10);
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundEvents.ENTITY_TNT_PRIMED).setPitch(1.5f));
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST.value()).setPitch(0.3F));
        AttackUtils.spawnProjectile(new CannonballEntity(context.actor(), context.actor().getEyePos().offset(Direction.DOWN, 4/16f), velocity.multiply(2)), context.actor());
        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.LANDING_HONEY, ParticleTypes.LANDING_HONEY, ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.black_pearl.tooltip3").setStyle(textStyle));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.BLACK_PEARL));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 120;
        @ValidatedInt.Restrict(min = 1)
        public int maxLevel = 5;
        @ValidatedInt.Restrict(min = 1)
        public int maxDuration = 600;
        @ValidatedFloat.Restrict(min = 0)
        public float cannonballDamage = 15;
        @ValidatedDouble.Restrict(min = 0)
        public double cannonballKnockback = 1.2;
        @ValidatedDouble.Restrict(min = 0)
        public double cannonballRecoil = 1.5;
        public boolean includeGlobalBlacklist = true;
        public ValidatedSet<Identifier> blacklist = ConfigUtils.createEffectList(
                SimplyMore.identifier("blessing")
        );
    }
}
