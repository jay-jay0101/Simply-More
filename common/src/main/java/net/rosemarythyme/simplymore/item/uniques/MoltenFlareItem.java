package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.EruptionAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class MoltenFlareItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = UNIQUE_CONFIG.molten_flare.cooldown;

    public MoltenFlareItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.GRANDSWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.getWorld().isClient()) return super.postHit(stack, target, attacker);

        if (MathUtils.chance(attacker, UNIQUE_CONFIG.molten_flare.chance) || attacker.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MOLTEN_FLARE))) {
            eruption(attacker.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MOLTEN_FLARE)) ?
                    UNIQUE_CONFIG.molten_flare.radiusEmpowered:
                    UNIQUE_CONFIG.molten_flare.radius, attacker);
            attacker.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MOLTEN_FLARE));
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.MOLTEN_FLARE),100));
            user.getItemCooldownManager().set(this, skillCooldown);
        }
        return super.use(world, user, hand);
    }

    private void eruption(int radius, LivingEntity attacker) {
        ((ServerWorld) attacker.getWorld()).spawnParticles(ParticleTypes.LAVA, attacker.getX(), attacker.getY(), attacker.getZ(), 100, 0, 0, 0, 0);
        attacker.getWorld().spawnEntity(new EruptionAreaEffectCloudEntity(attacker.getWorld(),attacker.getX(),attacker.getY(),attacker.getZ(),radius,attacker));
        attacker.getWorld().playSound(null, attacker.getBlockPos(), SoundRegistry.SPELL_FIRE.get(), attacker.getSoundCategory(), 2F, 0.3F);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.LAVA, ParticleTypes.LAVA, ParticleTypes.SMOKE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.molten_flare.tooltip3").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MOLTEN_FLARE));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.20f;
        @ValidatedInt.Restrict(min = 0)
        public int radius = 4;
        @ValidatedInt.Restrict(min = 0)
        public int radiusEmpowered = 7;
        @ValidatedFloat.Restrict(min = 0)
        @RequiresAction(action = Action.RESTART)
        public float activeAttackSpeedBonus = 0.6f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 300;
    }
}
