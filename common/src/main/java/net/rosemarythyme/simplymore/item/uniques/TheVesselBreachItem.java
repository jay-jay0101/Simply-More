package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
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
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class TheVesselBreachItem extends SimplyMoreUniqueSwordItem {
    int skillCooldown = effect.the_vessel_breach.cooldown;

    public TheVesselBreachItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (!(target instanceof ArmorStandEntity)) {
                    if (!attacker.hasStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.RAGE))) {
                        attacker.heal((float) HelperMethods.getEntityAttackDamage(attacker) * effect.the_vessel_breach.rageLifesteal);
                    } else {
                        attacker.heal((float) HelperMethods.getEntityAttackDamage(attacker) * effect.the_vessel_breach.lifesteal);
                        target.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.BLEED), effect.the_vessel_breach.bleedTime,0));
                    }
                }
            }
        return super.postHit(stack, target, attacker);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            user.damage(user.getDamageSources().genericKill(), user.getMaxHealth()*effect.the_vessel_breach.startupDamage);
            user.addStatusEffect(new StatusEffectInstance(ModEffectsRegistry.getReference(ModEffectsRegistry.RAGE), effect.the_vessel_breach.rageTime, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,12,4));
            user.getItemCooldownManager().set(this, skillCooldown);
            ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.CRIMSON_SPORE, user.getX(), user.getY() + 0.5, user.getZ(), 500, 0.5, 0.5, 0.5, 0.25);
            user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get(), user.getSoundCategory(), 2F, 0F);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        SimplyMoreHelperMethods.simplyMore$footfallsHelper(entity, stack, world, ParticleTypes.LANDING_LAVA, ParticleTypes.LANDING_LAVA, ParticleTypes.CRIMSON_SPORE);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip2",
                SimplyMoreHelperMethods.toPercentage(effect.the_vessel_breach.lifesteal)).setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.the_vessel_breach.tooltip3",
                SimplyMoreHelperMethods.toPercentage(effect.the_vessel_breach.startupDamage),
                SimplyMoreHelperMethods.toPercentage(effect.the_vessel_breach.rageLifesteal)).setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.THE_VESSEL_BREACH));
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
