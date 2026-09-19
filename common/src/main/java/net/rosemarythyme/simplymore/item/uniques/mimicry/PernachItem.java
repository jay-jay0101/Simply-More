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
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class PernachItem extends MimicryItem {
    public PernachItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 1 || ticksUsed == 10 || ticksUsed == 19) {
            MimicryVisualEntity.Animation anim = ticksUsed == 10 ? MimicryVisualEntity.Animation.REVERSE_SWING : MimicryVisualEntity.Animation.SWING;
            MimicryTimelineUtils.startAnimation(entity, 6, anim);
        }

        if(ticksUsed == 3 || ticksUsed == 12) {
            MimicryTimelineUtils.sweepAttack(entity, 1.4f)
                    .breakShield()
                    .forceDamageWithEnchants(MIMICRY_CONFIG.pernach.damage, entity);
        }

        if(ticksUsed == 21) {
            MimicryTimelineUtils.sweepAttack(entity, 1.6f)
                    .breakShield()
                    .damageWithEnchants(MIMICRY_CONFIG.pernach.damage, entity)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.pernach.effectTime, 1)
                    .applyEffect(StatusEffects.WEAKNESS, MIMICRY_CONFIG.pernach.effectTime, 0)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.pernach.effectTime, 0);
        }

        return ticksUsed >= 25;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.pernach.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.pernach.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_PERNACH));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 5.5f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
