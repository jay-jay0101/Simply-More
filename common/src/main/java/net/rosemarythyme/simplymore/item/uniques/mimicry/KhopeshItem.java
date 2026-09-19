package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.entity.MimicryVisualEntity;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.MimicryTimelineUtils;
import net.rosemarythyme.simplymore.util.PredicateUtils;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class KhopeshItem extends MimicryItem {
    public KhopeshItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 3) {
            MimicryTimelineUtils.move(entity, 2.6f, 0.4f);
        }

        if(ticksUsed == 12) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.SWING);
        }

        if(ticksUsed == 14) {
            TargetList targets = MimicryTimelineUtils.sweepAttack(entity, 1.2f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.khopesh.damage, entity);

            if(targets.isPopulated()) {
                new TargetList(entity).applyEffect(StatusEffects.SPEED, MIMICRY_CONFIG.khopesh.effectTime, 2);
            }
        }

        return ticksUsed >= 20;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.khopesh.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.khopesh.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_KHOPESH));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
