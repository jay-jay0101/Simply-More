package net.rosemarythyme.simplymore.item.uniques;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
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
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class CulterexItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.getCulterexCooldown();

    public CulterexItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient)
            return super.use(world, user, hand);

        Entity entity = HelperMethods.getTargetedEntity(user, effect.getCulterexRightClickRange());

        if(entity instanceof LivingEntity target) {
            if(target == user || target.isTeammate(user) || target.isDead()) {
                return super.use(world, user, hand);
            }

            user.getItemCooldownManager().set(this, skillCooldown);
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.GLOWING,
                    10
            ));

            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SOUL, entity.getX(), entity.getY(), entity.getZ(), 100, 0, 0, 0, 0.25f);

            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, SoundCategory.PLAYERS, 1,0.65f);
            user.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, SoundCategory.PLAYERS, 1,0.65f);

            if(target.hasStatusEffect(ModEffectsRegistry.HEX.get())) {
                int amplifier = target.getStatusEffect(ModEffectsRegistry.HEX.get()).getAmplifier() + 1;
                target.removeStatusEffect(ModEffectsRegistry.HEX.get());

                user.addStatusEffect(
                        new StatusEffectInstance(
                                ModEffectsRegistry.SOUL_HEALTH.get(),
                                amplifier * 100,
                                amplifier - 1
                        )
                );

                user.heal(amplifier*2);


            } else {
                List<StatusEffectInstance> statusEffects = target.getStatusEffects().stream()
                        .filter(statusEffectInstance -> statusEffectInstance.getEffectType().isBeneficial()).toList();
                int duration = effect.getCulterexBaseDuration();

                for(StatusEffectInstance statusEffect : statusEffects) {
                    int amplifier = statusEffect.getAmplifier() + 1;
                    duration += amplifier * effect.getCulterexDurationPerEffectLevel();
                }

                target.addStatusEffect(
                        new StatusEffectInstance(
                                ModEffectsRegistry.HEX.get(),
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

        if(target.hasStatusEffect(ModEffectsRegistry.HEX.get())
                && attacker.getRandom().nextBetween(1, 100) <= effect.getCulterexExtraDurationChance()) {
            int duration = target.getStatusEffect(ModEffectsRegistry.HEX.get()).getDuration();
            int amplifier = target.getStatusEffect(ModEffectsRegistry.HEX.get()).getAmplifier();
            duration += effect.getCulterexExtraDuration();

            target.addStatusEffect(
                    new StatusEffectInstance(
                            ModEffectsRegistry.HEX.get(),
                            duration,
                            amplifier
                    )
            );


            attacker.getWorld().playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENTITY_ALLAY_HURT, SoundCategory.PLAYERS, 1,0.65f);
        }

        return super.postHit(stack, target, attacker);
    }


    int stepMod = 0;
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        stepMod = SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, stepMod, ParticleTypes.ENCHANT);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        Style rightClickStyle = Styles.RIGHT_CLICK;
        Style abilityStyle = Styles.ABILITY;
        Style textStyle = Styles.TEXT;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip3").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip4").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip5").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip6").setStyle(textStyle));
        tooltip.add(Text.literal(" "));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip7").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip8").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.culterex.tooltip9").setStyle(textStyle));

        super.appendTooltip(itemStack, world, tooltip, tooltipContext);
    }

}
