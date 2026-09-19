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

public class WarglaiveItem extends MimicryItem {
    public WarglaiveItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 6 || ticksUsed == 28) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.SPIN);
        }

        if(ticksUsed == 8 || ticksUsed == 30) {
            float damage = ticksUsed == 30 ? MIMICRY_CONFIG.warglaive.secondDamage : MIMICRY_CONFIG.warglaive.firstDamage;
            MimicryTimelineUtils.spinAttack(entity, 4f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(damage, entity)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.warglaive.effectTime, 0)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.warglaive.effectTime, 0);
        }

        if(ticksUsed == 16) {
            MimicryTimelineUtils.move(entity, 3.5f, 0.4f);
        }

        return ticksUsed >= 36;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.warglaive.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.warglaive.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_WARGLAIVE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float firstDamage = 8f;
        @ValidatedFloat.Restrict(min = 0f)
        public float secondDamage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
