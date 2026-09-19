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

public class DaggerItem extends MimicryItem {
    public DaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 1) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.SWING);
        }

        if(ticksUsed == 3) {
            TargetList targets = MimicryTimelineUtils.sweepAttack(entity, 1.4f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.dagger.damage, entity)
                    .applyEffect(StatusEffects.BLINDNESS, MIMICRY_CONFIG.dagger.effectTime, 0)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.dagger.effectTime, 0);

            if(targets.isPopulated()) new TargetList(entity).applyEffect(StatusEffects.INVISIBILITY, MIMICRY_CONFIG.dagger.invisTime, 0);
        }

        if(ticksUsed == 6) {
            MimicryTimelineUtils.move(entity, -3f, 0.4f);
        }

        return ticksUsed >= 13;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.dagger.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.dagger.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_DAGGER));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 6f;
        @ValidatedInt.Restrict(min = 0)
        public int invisTime = 120;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 60;
    }
}
