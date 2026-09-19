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
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class HalberdItem extends MimicryItem implements TwoHandedWeapon {
    public HalberdItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 5) {
            MimicryTimelineUtils.startAnimation(entity, 8, MimicryVisualEntity.Animation.SPIN);
        }

        if(ticksUsed == 8) {
            MimicryTimelineUtils.spinAttack(entity, 5.5f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.halberd.damage, entity)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED), MIMICRY_CONFIG.halberd.effectTime, 1);
        }

        if((ticksUsed >= 14 && 19 >= ticksUsed) || (ticksUsed >= 26 && 31 >= ticksUsed)) {
            MimicryTimelineUtils.move(entity, 1f, 0f);
        }

        if(ticksUsed == 21 || ticksUsed == 33) {
            MimicryVisualEntity.Animation anim = ticksUsed == 33 ? MimicryVisualEntity.Animation.REVERSE_SWING : MimicryVisualEntity.Animation.SWING;
            MimicryTimelineUtils.startAnimation(entity, 6, anim);
        }

        if(ticksUsed == 23 || ticksUsed == 35) {
            MimicryTimelineUtils.sweepAttack(entity, 3.2f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.halberd.damage, entity);

        }

        return ticksUsed >= 40;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.halberd.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.halberd.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_HALBERD));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 4f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 180;
    }
}
