package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
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

public class RapierItem extends MimicryItem {
    public RapierItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.rapier.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.rapier.tooltip1").setStyle(Styles.TEXT));
    }

    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 2 || ticksUsed == 5 || ticksUsed == 8 || ticksUsed == 11 || ticksUsed == 14) {
            MimicryTimelineUtils.startAnimation(entity, 4, MimicryVisualEntity.Animation.STAB);
        }

        if(ticksUsed == 4 || ticksUsed == 7 || ticksUsed == 10 || ticksUsed == 13 || ticksUsed == 16) {
            TargetList targets = MimicryTimelineUtils.stabAttack(entity, 3f, 0.25f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .forceDamageWithEnchants(MIMICRY_CONFIG.rapier.damage, entity);

            if(ticksUsed == 4 || ticksUsed == 16) {
                targets.applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.rapier.effectTime, 0);
            }
        }

        return ticksUsed >= 22;
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_RAPIER));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 3f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 60;
    }
}
