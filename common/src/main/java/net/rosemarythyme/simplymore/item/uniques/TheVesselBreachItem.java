package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class TheVesselBreachItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = UNIQUE_CONFIG.the_vessel_breach.cooldown;

    public TheVesselBreachItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
            if (!attacker.getWorld().isClient()) {
                if (!(target instanceof ArmorStandEntity)) {
                    if (!attacker.hasStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.RAGE))) {
                        attacker.heal((float) HelperMethods.getEntityAttackDamage(attacker) * UNIQUE_CONFIG.the_vessel_breach.rageLifesteal);
                    } else {
                        attacker.heal((float) HelperMethods.getEntityAttackDamage(attacker) * UNIQUE_CONFIG.the_vessel_breach.lifesteal);
                        target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), UNIQUE_CONFIG.the_vessel_breach.bleedTime,0));
                    }
                }
            }
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            user.damage(user.getDamageSources().genericKill(), user.getMaxHealth()* UNIQUE_CONFIG.the_vessel_breach.startupDamage);
            user.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.RAGE), UNIQUE_CONFIG.the_vessel_breach.rageTime, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,12,4));
            user.getItemCooldownManager().set(this, skillCooldown);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CRIMSON_SPORE, user.getX(), user.getY() + 0.5, user.getZ(), 500, 0.5, 0.5, 0.5, 0.25);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get(), user.getSoundCategory(), 2F, 0F);
        }
        return super.use(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.LANDING_LAVA, ParticleTypes.LANDING_LAVA, ParticleTypes.CRIMSON_SPORE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip2",
                MathUtils.toPercentage(UNIQUE_CONFIG.the_vessel_breach.lifesteal)).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip3",
                MathUtils.toPercentage(UNIQUE_CONFIG.the_vessel_breach.startupDamage),
                MathUtils.toPercentage(UNIQUE_CONFIG.the_vessel_breach.rageLifesteal)).setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.THE_VESSEL_BREACH));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 1800;
        @ValidatedInt.Restrict(min = 0)
        public int bleedTime = 100;
        @ValidatedFloat.Restrict(min = 0f)
        public float lifesteal = 0.1f;
        @ValidatedFloat.Restrict(min = 0f)
        public float rageLifesteal = 0.16f;
        @ValidatedInt.Restrict(min = 0)
        public int rageTime = 200;
        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float startupDamage = 0.3f;
    }
}
