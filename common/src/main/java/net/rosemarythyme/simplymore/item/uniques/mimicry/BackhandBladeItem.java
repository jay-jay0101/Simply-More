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
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class BackhandBladeItem extends MimicryItem {
    public BackhandBladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed > 2 && 9 >= ticksUsed) {
            if(ticksUsed % 3 == 0) {
                MimicryVisualEntity.Animation anim = ticksUsed % 6 == 0 ? MimicryVisualEntity.Animation.REVERSE_LONG_SWING : MimicryVisualEntity.Animation.LONG_SWING;
                MimicryTimelineUtils.startAnimation(entity, 3, anim);
            }

            MimicryTimelineUtils.move(entity,3f,0f);
            MimicryTimelineUtils.sweepAttack(entity, 2f)
                    .damageWithEnchants(MIMICRY_CONFIG.backhand_blade.damage, entity)
                    .applyEffect(StatusEffects.BLINDNESS, MIMICRY_CONFIG.backhand_blade.effectTime, 0);
        }

        if(ticksUsed > 10 && 16 >= ticksUsed) {
            MimicryTimelineUtils.move(entity,-3f,0f);
        }

        return ticksUsed >= 18;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.backhand_blade.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.backhand_blade.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_BACKHAND_BLADE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
