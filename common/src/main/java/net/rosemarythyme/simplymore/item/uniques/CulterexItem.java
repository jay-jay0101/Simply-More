package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class CulterexItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.culterex.cooldown;

    public CulterexItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient)
            return super.use(world, user, hand);

        Entity entity = HelperMethods.getTargetedEntity(user, effect.culterex.range);

        if(entity instanceof LivingEntity target) {
            if(!AttackUtils.canHitTarget(target, user)) return super.use(world, user, hand);

            user.getItemCooldownManager().set(this, skillCooldown);
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.GLOWING,
                    10
            ));

            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SOUL, entity.getX(), entity.getY(), entity.getZ(), 100, 0, 0, 0, 0.25f);

            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, SoundCategory.PLAYERS, 1,0.65f);
            user.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, SoundCategory.PLAYERS, 1,0.65f);

            if(target.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX))) {
                int amplifier = target.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX)).getAmplifier() + 1;
                target.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX));

                user.addStatusEffect(
                        new StatusEffectInstance(
                                ModEffectsRegistry.getReference(ModEffectsRegistry.SOUL_HEALTH),
                                amplifier * 100,
                                amplifier - 1
                        )
                );

                user.heal(amplifier*2);


            } else {
                List<StatusEffectInstance> statusEffects = target.getStatusEffects().stream()
                        .filter(statusEffectInstance -> statusEffectInstance.getEffectType().value().isBeneficial()).toList();
                int duration = effect.culterex.baseDuration;

                for(StatusEffectInstance statusEffect : statusEffects) {
                    int amplifier = statusEffect.getAmplifier() + 1;
                    duration += amplifier * effect.culterex.durationPerEffectLevel;
                }

                target.addStatusEffect(
                        new StatusEffectInstance(
                                ModEffectsRegistry.getReference(ModEffectsRegistry.HEX),
                                duration,
                                0
                        )
                );
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient())
            return super.postHit(stack, target, attacker);

        if(target.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX))
                && MathUtils.chance(attacker, effect.culterex.chance)) {
            int duration = target.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX)).getDuration();
            int amplifier = target.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.HEX)).getAmplifier();
            duration += effect.culterex.extraDuration;

            target.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.getReference(ModEffectsRegistry.HEX),
                            duration,
                            amplifier
                    )
            );


            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_ALLAY_HURT, SoundCategory.PLAYERS, 1,0.65f);
        }

        return super.postHit(stack, target, attacker);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.ENCHANT);
        super.inventoryTick(stack, world, entity, slot, selected);
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
            super(new ItemStackTooltipAppender(ModItemsRegistry.CULTEREX));
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
    }

}
