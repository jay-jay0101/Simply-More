package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
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

public class LongswordItem extends MimicryItem {
    public LongswordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 1) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.SWING);
        }

        if(ticksUsed == 3) {
            TargetList targets = MimicryTimelineUtils.sweepAttack(entity, 1.6f);

            float damage = MIMICRY_CONFIG.longsword.damage + (targets.size() * MIMICRY_CONFIG.longsword.extraDamage);
            targets.filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(damage, entity)
                    .knockback(entity, MIMICRY_CONFIG.longsword.knockback);
        }

        return ticksUsed >= 13;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.longsword.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.longsword.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_LONGSWORD));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 6f;
        @ValidatedFloat.Restrict(min = 0f)
        public float extraDamage = 2f;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.2f;
    }
}
