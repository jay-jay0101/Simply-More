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
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GrandswordItem extends MimicryItem implements TwoHandedWeapon {
    public GrandswordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 5) {
            MimicryTimelineUtils.startAnimation(entity, 10, MimicryVisualEntity.Animation.SPIN);
        }

        if(ticksUsed > 8 && 50 >= ticksUsed) {
            MimicryTimelineUtils.move(entity,0.65f,0f);
            TargetList user = new TargetList(entity).applyEffect(StatusEffects.BLINDNESS, 40, 0);

            int cycle = ticksUsed % 10;
            if(cycle == 0) {
                TargetList targets = MimicryTimelineUtils.spinAttack(entity, 4f)
                        .breakShield()
                        .damageWithEnchants(MIMICRY_CONFIG.grandsword.damage, entity)
                        .knockback(entity, MIMICRY_CONFIG.grandsword.knockback);

                if (targets.isPopulated()) {
                    user.applyEffect(StatusEffects.STRENGTH, MIMICRY_CONFIG.grandsword.effectTime, 0);
                }
            } else if (cycle == 5) {
                MimicryTimelineUtils.startAnimation(entity, 10, MimicryVisualEntity.Animation.SPIN);
            }
        }

        return ticksUsed >= 60;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.grandsword.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.grandsword.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GRANDSWORD));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 120;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 2.5f;
    }
}
