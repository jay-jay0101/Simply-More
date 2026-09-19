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

public class GreatSpearItem extends MimicryItem implements TwoHandedWeapon {
    public GreatSpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {

        if(ticksUsed == 1) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.DOWN_SWING);
        }

        if(ticksUsed == 6) {
            MimicryTimelineUtils.slamAttack(entity, 4f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.great_spear.slamDamage, entity)
                    .applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.STUN), MIMICRY_CONFIG.great_spear.effectTime, 25)
                    .addVelocity(0, 0.65f, 0);
        }

        if(ticksUsed == 16) {
            MimicryTimelineUtils.startAnimation(entity, 4, MimicryVisualEntity.Animation.STAB);
        }

        if(ticksUsed == 18) {
            MimicryTimelineUtils.stabAttack(entity, 5, 0.8f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.great_spear.stabDamage, entity)
                    .knockback(entity, MIMICRY_CONFIG.great_spear.knockback);
        }

        return ticksUsed >= 28;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.great_spear.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_spear.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GREAT_SPEAR));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float slamDamage = 6f;
        @ValidatedFloat.Restrict(min = 0f)
        public float stabDamage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 20;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.5f;
    }
}
