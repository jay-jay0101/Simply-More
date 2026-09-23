package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.DamageTypeRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.EntityUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.rosemarythyme.simplymore.world.ActiveAbilityManager;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class TheVesselBreachItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public static final TheVesselBreachItem.EffectSettings SETTINGS = UNIQUE_CONFIG.the_vessel_breach;

    public TheVesselBreachItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(!isFirstInTick) return;

        float lifesteal = SETTINGS.lifesteal;
        if(ActiveAbilityManager.SERVER.isInAbility(attacker, ActiveAbilityManager.Type.RAGE)) {
            lifesteal = SETTINGS.rageLifesteal;
            new TargetList(target)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), SETTINGS.bleedTime, 0);
        }

        EntityUtils.lifesteal(attacker, target, lifesteal);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        float damage = context.actor().getMaxHealth() * SETTINGS.startupDamage;

        context.actor().damage(DamageTypeRegistry.damageSourceOf(context.world(), DamageTypeRegistry.BLEED), damage);
        ActiveAbilityManager.SERVER.start(context.actor(), ActiveAbilityManager.Type.RAGE, SETTINGS.rageTime);

        AudioVisualUtils.particleAroundEntity(context.actor(), ParticleTypes.CRIMSON_SPORE, 500, 0.5f, 0.25f);
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get()).setPitch(2f));
        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundEvents.ENTITY_RAVAGER_ROAR));

        return false;
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return !ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.RAGE) &&
                context.actor().getHealth() / context.actor().getMaxHealth() > Math.min(1, SETTINGS.startupDamage + 0.15f);
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.CRIMSON_SPORE);
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

        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);
        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.THE_VESSEL_BREACH));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 1000;
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
