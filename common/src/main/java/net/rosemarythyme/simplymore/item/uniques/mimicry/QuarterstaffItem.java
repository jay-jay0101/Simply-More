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

public class QuarterstaffItem extends MimicryItem {
    public QuarterstaffItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 1) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.DOWN_SWING);
        }

        if(ticksUsed == 4) {
            MimicryTimelineUtils.move(entity, 0, 1.15f);
            new TargetList(entity).applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LIGHTWEIGHT), 50, 0);
        }

        if(ticksUsed == 6) {
            MimicryTimelineUtils.slamAttack(entity, 6f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.quarterstaff.damage, entity)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.quarterstaff.effectTime, 2)
                    .addVelocity(0, 0.9f, 0);
        }

        return ticksUsed >= 15;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.quarterstaff.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.quarterstaff.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_QUARTERSTAFF));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
