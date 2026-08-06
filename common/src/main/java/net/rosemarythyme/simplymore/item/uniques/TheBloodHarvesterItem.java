package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
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
import net.sweenus.simplyswords.util.HelperMethods;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class TheBloodHarvesterItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    TheBloodHarvesterItem.EffectSettings SETTINGS = UNIQUE_CONFIG.the_blood_harvester;

    public TheBloodHarvesterItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getWorld().isClient) return super.postHit(stack, target, attacker);
        if(target instanceof ArmorStandEntity) return super.postHit(stack, target, attacker);

        if(ActiveAbilityManager.SERVER.isInAbility(attacker, ActiveAbilityManager.Type.HARVEST)) {
            attacker.heal((float) HelperMethods.getEntityAttackDamage(attacker) * UNIQUE_CONFIG.the_blood_harvester.harvestLifesteal);
            new TargetList(target)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), UNIQUE_CONFIG.the_blood_harvester.bleedTime, 0);
        } else {
            attacker.heal((float) HelperMethods.getEntityAttackDamage(attacker) * UNIQUE_CONFIG.the_blood_harvester.lifesteal);
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return !ActiveAbilityManager.SERVER.isInAbility(context.actor(), ActiveAbilityManager.Type.HARVEST);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        ActiveAbilityManager.SERVER.start(context.actor(), ActiveAbilityManager.Type.HARVEST, SETTINGS.harvestTime);

        AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_04.get()).setPitch(0f));
        AudioVisualUtils.particleAroundEntity(context.actor(), ParticleTypes.CRIMSON_SPORE, 500, 0.5f, 0.25f);

        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.LANDING_LAVA, ParticleTypes.LANDING_LAVA, ParticleTypes.CRIMSON_SPORE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip2",
                MathUtils.toPercentage(SETTINGS.lifesteal), MathUtils.toPercentage(SETTINGS.harvestLifesteal)).setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.the_blood_harvester.tooltip3",
                MathUtils.translateTicks(UNIQUE_CONFIG.the_blood_harvester.harvestTime)
        ).setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.THE_BLOOD_HARVESTER));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
        @ValidatedInt.Restrict(min = 0)
        public int harvestTime = 300;
        @ValidatedInt.Restrict(min = 0)
        public int bleedTime = 150;
        @ValidatedFloat.Restrict(min = 0f)
        public float lifesteal = 0.1f;
        @ValidatedFloat.Restrict(min = 0f)
        public float harvestLifesteal = 0.2f;
    }
}
