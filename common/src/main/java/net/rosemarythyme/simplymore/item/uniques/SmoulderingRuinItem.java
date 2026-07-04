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
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class SmoulderingRuinItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.smouldering_ruin.cooldown;

    public SmoulderingRuinItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MathUtils.chance(attacker, effect.smouldering_ruin.chance)) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, effect.smouldering_ruin.witherTime, 0), attacker);
            StatusEffectInstance targetWitheringFateStatus = target.getStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.WITHERING_FATE));
            if (targetWitheringFateStatus != null) {
                target.addStatusEffect(
                        new StatusEffectInstance(
                                ModEffectsRegistry.getReference(ModEffectsRegistry.WITHERING_FATE),
                                targetWitheringFateStatus.getDuration(),
                                targetWitheringFateStatus.getAmplifier() + 1
                        ), attacker);
            }
        }

        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            double userYawRadians = Math.toRadians(user.getYaw());
            double velocityX = Math.sin(userYawRadians);
            double velocityZ = Math.cos(userYawRadians);

            user.setVelocity(velocityX, 1, -velocityZ);
            user.velocityModified = true;

            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundRegistry.ELEMENTAL_BOW_FIRE_SHOOT_FLYBY_01.get(), SoundCategory.PLAYERS, 1, 1);
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.MISTIFIED),10000,0));
            user.getItemCooldownManager().set(this, skillCooldown);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.CRIMSON_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip5").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.smouldering_ruin.tooltip9").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.SMOULDERING_RUIN));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int witherTime = 100;
    }
}
