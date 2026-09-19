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
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class ScytheItem extends MimicryItem implements TwoHandedWeapon {
    public ScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 2 || ticksUsed == 17 || ticksUsed == 32) {
            MimicryTimelineUtils.move(entity, 3f,0.18f);
        }

        if(ticksUsed == 12 || ticksUsed == 27 || ticksUsed == 43) {
            MimicryVisualEntity.Animation anim = ticksUsed == 27 ? MimicryVisualEntity.Animation.REVERSE_LONG_SWING : MimicryVisualEntity.Animation.LONG_SWING;
            MimicryTimelineUtils.startAnimation(entity, 10, anim);
        }

        if(ticksUsed == 15 || ticksUsed == 30 || ticksUsed == 45) {
            MimicryTimelineUtils.sweepAttack(entity, 2.8f)
                    .include(MimicryTimelineUtils.sweepAttack(entity, 2.8f, 25))
                    .include(MimicryTimelineUtils.sweepAttack(entity, 2.8f, -25))
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .forceDamageWithEnchants(MIMICRY_CONFIG.scythe.damage, entity)
                    .incrementEffect(StatusEffects.WITHER, MIMICRY_CONFIG.scythe.effectTime, 0, 3);
        }

        return ticksUsed >= 50;

    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.scythe.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.scythe.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_SCYTHE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
