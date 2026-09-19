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

public class LanceItem extends MimicryItem {
    public LanceItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 2) {
            MimicryTimelineUtils.move(entity, 3.2f, 0.4f);
            new TargetList(entity).applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LIGHTWEIGHT), 20, 0);
        }

        if(ticksUsed == 17 || ticksUsed == 25) {
            MimicryTimelineUtils.startAnimation(entity, 4, MimicryVisualEntity.Animation.STAB);
        }

        if(ticksUsed == 19 || ticksUsed == 27) {
            TargetList targets = MimicryTimelineUtils.stabAttack(entity, 4f, 0.8f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING);

            if(ticksUsed == 19) {
                targets.damageWithEnchants(MIMICRY_CONFIG.lance.firstDamage, entity)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.lance.effectTime, 2);
            } else {
                targets.forceDamageWithEnchants(MIMICRY_CONFIG.lance.secondDamage, entity)
                        .knockback(entity, MIMICRY_CONFIG.lance.knockback);
            }
        }

        return ticksUsed >= 36;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.lance.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.lance.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_LANCE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float firstDamage = 8f;
        @ValidatedFloat.Restrict(min = 0f)
        public float secondDamage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.5f;
    }
}
