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
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class SaiItem extends MimicryItem {
    public SaiItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.sai.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.sai.tooltip1").setStyle(Styles.TEXT));
    }

    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 2 || ticksUsed == 5 || ticksUsed == 8) {
            MimicryTimelineUtils.startAnimation(entity, 4, MimicryVisualEntity.Animation.STAB);
        }

        if(ticksUsed == 4 || ticksUsed == 7 || ticksUsed == 10) {
            TargetList targets = MimicryTimelineUtils.stabAttack(entity, 2f, 0.25f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .forceDamageWithEnchants(MIMICRY_CONFIG.sai.damage, entity)
                    .applyEffect(StatusEffects.BLINDNESS, MIMICRY_CONFIG.sai.effectTime, 0)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.sai.effectTime, 0);

            if(targets.isPopulated())  {
                new TargetList(entity).applyEffect(StatusEffects.SPEED, MIMICRY_CONFIG.sai.speedTime, 1);
            }
        }

        return ticksUsed >= 15;

    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_SAI));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 5.5f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
        @ValidatedInt.Restrict(min = 0)
        public int speedTime = 30;
    }
}
