package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
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
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class TwinbladeItem extends MimicryItem implements TwoHandedWeapon {
    public TwinbladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 6 || ticksUsed == 22) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.SPIN);
        }

        if(ticksUsed == 8 || ticksUsed == 24) {
            TargetList targets = MimicryTimelineUtils.spinAttack(entity, 4.5f)
                    .filter(PredicateUtils.IS_NOT_BLOCKING);

            float damage = MimicryItem.MIMICRY_CONFIG.twinblade.firstDamage;
            if(ticksUsed == 24) {
                targets.knockback(entity, MIMICRY_CONFIG.twinblade.knockback);
                damage = MimicryItem.MIMICRY_CONFIG.twinblade.secondDamage;
            }

            targets.damageWithEnchants(damage, entity)
                    .forceDamage(MIMICRY_CONFIG.twinblade.pierceDamage, entity.getDamageSources().indirectMagic(entity, entity));
        }

        return ticksUsed >= 30;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.twinblade.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.twinblade.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_TWINBLADE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float firstDamage = 5f;
        @ValidatedFloat.Restrict(min = 0f)
        public float pierceDamage = 1f;
        @ValidatedFloat.Restrict(min = 0f)
        public float secondDamage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.5f;
    }
}
