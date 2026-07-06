package net.rosemarythyme.simplymore.item.uniques.idols;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.AuraOfCorruptionAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.item.interfaces.LegendaryItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class DarksentItem extends SimplyMoreUniqueSwordItem implements LegendaryItem {

    int skillCooldown = UNIQUE_CONFIG.darksent.cooldown;

    public DarksentItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        SimplyMoreHelperMethods.simplyMore$IdolHitEffects(
                attacker,
                ParticleTypes.FALLING_OBSIDIAN_TEAR,
                300,
                1.0D,
                1.0D,
                1.0D,
                0.0D,
                new AuraOfCorruptionAreaEffectCloudEntity(
                        attacker.getWorld(),
                        attacker.getX(),
                        attacker.getY(),
                        attacker.getZ(),
                        attacker
                ),
                UNIQUE_CONFIG.darksent.chance
        );

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        SimplyMoreHelperMethods.simplyMore$IdolUseEffects(
                this,
                user,
                StatusEffectRegistry.getReference(StatusEffectRegistry.CURSE),
                160,
                SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK,
                2F,
                1.5F,
                ParticleTypes.SCULK_SOUL,
                50,
                0.25D,
                0.5D,
                0.25D,
                0.1D,
                skillCooldown
        );
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip6").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.darksent.tooltip9",
                MathUtils.toPercentage(UNIQUE_CONFIG.darksent.curseDamageMultiplier)).setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.DARKSENT));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.15f;
        @ValidatedFloat.Restrict(min = 0f)
        public float curseDamageMultiplier = 1.5f;
        @ValidatedInt.Restrict(min = 0)
        public int curseWeakenTime = 100;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
    }
}
