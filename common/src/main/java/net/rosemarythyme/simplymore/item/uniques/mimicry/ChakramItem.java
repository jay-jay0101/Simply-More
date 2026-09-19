package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.entity.MimicryVisualEntity;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.MimicryTimelineUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class ChakramItem extends MimicryItem {
    public ChakramItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed < 15) {
            MimicryTimelineUtils.move(entity, MathUtils.clampedLerp(ticksUsed, 0, 15, 1.25f, 0.25f), 0f);

            if(ticksUsed % 4 == 1) {
                MimicryTimelineUtils.startAnimation(entity, 4, MimicryVisualEntity.Animation.SPIN);
            }

            if(ticksUsed % 3 == 0) {
                TargetList targets = MimicryTimelineUtils.spinAttack(entity, 2f)
                        .filter(PredicateUtils.IS_NOT_BLOCKING)
                        .damageWithEnchants(MIMICRY_CONFIG.chakram.damage, entity)
                        .knockback(entity, MIMICRY_CONFIG.chakram.knockback);

                if(targets.isPopulated()) {
                    new TargetList(entity).applyEffect(StatusEffects.SPEED, MIMICRY_CONFIG.chakram.effectTime, 1)
                            .applyEffect(StatusEffects.HASTE, MIMICRY_CONFIG.chakram.effectTime, 0);
                }
            }
        }

        return ticksUsed >= 25;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.chakram.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.chakram.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_CHAKRAM));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 10f;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.5f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 60;
    }
}
