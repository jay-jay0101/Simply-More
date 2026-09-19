package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.entity.MimicryVisualEntity;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.MimicryTimelineUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class SpearItem extends MimicryItem {
    public SpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.spear.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.spear.tooltip1").setStyle(Styles.TEXT));
    }

    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 4 || ticksUsed == 8 || ticksUsed == 18) {
            MimicryTimelineUtils.startAnimation(entity, 4, MimicryVisualEntity.Animation.STAB);
        }

        if(ticksUsed == 6 || ticksUsed == 10) {
            MimicryTimelineUtils.stabAttack(entity, 5f, 0.4f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .forceDamageWithEnchants(MIMICRY_CONFIG.spear.damage, entity)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.spear.effectTime, 0)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.sai.effectTime, 0);
        }

        if(ticksUsed == 20) {
            MimicryTimelineUtils.stabAttack(entity, 5f, 0.4f)
                    .breakShield()
                    .forceDamageWithEnchants(MIMICRY_CONFIG.spear.finalDamage, entity)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.spear.effectTime, 0)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.sai.effectTime, 0);
        }

        return ticksUsed >= 28;
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_SPEAR));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedFloat.Restrict(min = 0f)
        public float finalDamage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
