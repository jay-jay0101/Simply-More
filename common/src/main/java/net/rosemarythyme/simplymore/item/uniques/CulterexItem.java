package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class CulterexItem extends SimplyMoreUniqueSwordItem {
    public CulterexItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient) return super.use(world, user, hand);

        LivingEntity target = AttackUtils.getTargetedEntity(user, UNIQUE_CONFIG.culterex.range, AttackUtils.AttackTarget.ENEMIES);
        if(target == null) return super.use(world, user, hand);

        user.getItemCooldownManager().set(this, UNIQUE_CONFIG.culterex.cooldown);
        AudioVisualUtils.targetIndicator(target);
        AudioVisualUtils.particleAroundEntity(target, ParticleTypes.SOUL, 100, 0, 0.25f);

        Sound sound = new Sound(SoundEvents.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM).setPitch(0.65f);
        AudioVisualUtils.playSound(world, user.getPos(), sound);
        AudioVisualUtils.playSound(world, target.getPos(), sound);

        RegistryEntry<StatusEffect> hex = StatusEffectRegistry.getReference(StatusEffectRegistry.HEX);
        if (target.hasStatusEffect(hex)) {
            int amplifier = target.getStatusEffect(hex).getAmplifier();
            target.removeStatusEffect(hex);

            user.addStatusEffect(new StatusEffectInstance(
                StatusEffectRegistry.getReference(StatusEffectRegistry.SOUL_HEALTH),
                amplifier * 100,
                amplifier
            ));

            user.heal(amplifier * 2f);
        } else {
            List<StatusEffectInstance> effects = target.getStatusEffects().stream()
                    .filter((effect) -> effect.getEffectType().value().isBeneficial())
                    .toList();

            int duration = UNIQUE_CONFIG.culterex.baseDuration;
            for (StatusEffectInstance effect : effects) {
                duration += effect.getAmplifier() + 1;
            }

            target.addStatusEffect(new StatusEffectInstance(hex, duration, 0));
        }

        return super.use(world, user, hand);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if (attacker.getWorld().isClient()) return;

        RegistryEntry<StatusEffect> hex = StatusEffectRegistry.getReference(StatusEffectRegistry.HEX);
        if(!target.hasStatusEffect(hex)) return;

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.culterex.chance)) {
            int duration = target.getStatusEffect(hex).getDuration() + UNIQUE_CONFIG.culterex.extraDuration;

            EntityUtils.reapplyAndIncrementEffect(target,
                    hex, duration, 1, 20);

            AudioVisualUtils.playSound(attacker.getWorld(), attacker.getPos(), new Sound(SoundEvents.ENTITY_ALLAY_HURT).setPitch(0.65f));
        }
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ENCHANT);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip3").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip7").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.CULTEREX));
        }


        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 400;
        @ValidatedInt.Restrict(min = 0)
        public int baseDuration = 180;
        @ValidatedInt.Restrict(min = 0)
        public int extraDuration = 60;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.35f;
        @ValidatedInt.Restrict(min = 0)
        public int durationPerEffectLevel = 60;
        @ValidatedInt.Restrict(min = 0)
        public int range = 30;
        public boolean includeGlobalBlacklist = true;
        public ValidatedSet<Identifier> blacklist = ConfigUtils.createEffectList();
    }
}
