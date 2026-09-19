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
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GreathammerItem extends MimicryItem implements TwoHandedWeapon {
    public GreathammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 1 || ticksUsed == 13) {
            MimicryTimelineUtils.startAnimation(entity, 8, MimicryVisualEntity.Animation.DOWN_SWING);
        }

        if(ticksUsed == 6 || ticksUsed == 18) {
            MimicryTimelineUtils.slamAttack(entity, 5f)
                    .breakShield()
                    .damageWithEnchants(MIMICRY_CONFIG.greathammer.damage, entity)
                    .addVelocity(0, 1f, 0f)
                    .applyEffect(StatusEffects.WEAKNESS, MIMICRY_CONFIG.greathammer.effectTime, 1)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.greathammer.effectTime, 1);
        }

        return ticksUsed >= 28;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.greathammer.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.greathammer.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GREATHAMMER));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 9f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
