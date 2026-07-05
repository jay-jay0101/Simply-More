package net.rosemarythyme.simplymore.item.uniques.idols;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
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
import net.rosemarythyme.simplymore.entity.AuraOfPurityAreaEffectCloudEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.rosemarythyme.simplymore.util.VisualEffectsUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class HolylightItem extends SimplyMoreUniqueSwordItem {

    int skillCooldown = uniqueConfig.holylight.cooldown;

    public HolylightItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        SimplyMoreHelperMethods.simplyMore$IdolHitEffects(
                attacker,
                ParticleTypes.FALLING_WATER,
                300,
                1.0D,
                1.0D,
                1.0D,
                0.0D,
                new AuraOfPurityAreaEffectCloudEntity(
                        attacker.getWorld(),
                        attacker.getX(),
                        attacker.getY(),
                        attacker.getZ(),
                        attacker
                ),
                uniqueConfig.holylight.spreadChance
        );

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        SimplyMoreHelperMethods.simplyMore$IdolUseEffects(
                this,
                user,
                ModEffectsRegistry.getReference(ModEffectsRegistry.BLESSING),
                160,
                SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON,
                2F,
                1.5F,
                ParticleTypes.WAX_OFF,
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
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.holylight.tooltip6",
                uniqueConfig.holylight.blessingHeal/2).setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        VisualEffectsUtils.handleFootfalls(entity, stack, world, ParticleTypes.ASH);
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.HOLYLIGHT));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float spreadChance = 0.15f;
        @ValidatedFloat.Restrict(min = 0f)
        public float blessingHeal = 4f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 800;
    }
}
