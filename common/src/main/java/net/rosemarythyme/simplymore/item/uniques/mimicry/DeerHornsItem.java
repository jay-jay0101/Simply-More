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

public class DeerHornsItem extends MimicryItem {
    public DeerHornsItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 3) {
            MimicryTimelineUtils.move(entity,2f,1f);
            new TargetList(entity).applyEffect(StatusEffects.SLOW_FALLING, 60, 0);
        }

        if(ticksUsed > 6) {
            if(entity.isOnGround()) return true;

            if(ticksUsed % 6 == 1) {
                MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.SPIN);
            }

            if(ticksUsed % 2 == 0) {
                TargetList targets = MimicryTimelineUtils.spinAttack(entity, 2f)
                        .filter(PredicateUtils.IS_NOT_BLOCKING)
                        .forceDamageWithEnchants(MIMICRY_CONFIG.deer_horns.damage * MIMICRY_CONFIG.deer_horns.damageMultiplier, entity)
                        .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.deer_horns.effectTime, 0);

                if (targets.isPopulated()) {
                    new TargetList(entity).applyEffect(StatusEffects.SPEED, MIMICRY_CONFIG.deer_horns.effectTime, 1);
                }
            }
        }

        return ticksUsed >= 60;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.deer_horns.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.deer_horns.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_DEER_HORNS));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 5f;
        @ValidatedFloat.Restrict(min = 0f)
        public float damageMultiplier = 0.4f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
