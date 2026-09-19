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
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class ClaymoreItem extends MimicryItem implements TwoHandedWeapon {
    public ClaymoreItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 2) {
            MimicryTimelineUtils.move(entity, 3.2f,0.7f);
            new TargetList(entity).applyEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.LIGHTWEIGHT), 20, 0);
        }

        if(ticksUsed == 18) {
            MimicryTimelineUtils.startAnimation(entity, 10, MimicryVisualEntity.Animation.WIDE_DOWN_SWING);
        }

        if(ticksUsed == 22) {
            MimicryTimelineUtils.slamAttack(entity, 5f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING)
                    .damageWithEnchants(MIMICRY_CONFIG.claymore.damage, entity)
                    .knockback(entity, MIMICRY_CONFIG.claymore.knockback)
                    .addVelocity(0, 0.4f, 0f)
                    .applyEffect(StatusEffects.WEAKNESS, MIMICRY_CONFIG.claymore.effectTime, 0)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.claymore.effectTime, 1);
        }

        return ticksUsed >= 28;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.claymore.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.claymore.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_CLAYMORE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 14f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.6f;
    }
}
