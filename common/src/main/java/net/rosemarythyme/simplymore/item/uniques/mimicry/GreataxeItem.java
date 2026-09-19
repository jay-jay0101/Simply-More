package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
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

public class GreataxeItem extends MimicryItem implements TwoHandedWeapon {
    public GreataxeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 4) {
            MimicryTimelineUtils.startAnimation(entity, 10, MimicryVisualEntity.Animation.SPIN);
        }

        if(ticksUsed == 8) {
            TargetList targets = MimicryTimelineUtils.spinAttack(entity, 5f)
                    .breakShield();

            float damage = MIMICRY_CONFIG.greataxe.damage + (targets.size() * MIMICRY_CONFIG.greataxe.extraDamage);
            targets.damageWithEnchants(damage, entity)
                    .knockback(entity, MIMICRY_CONFIG.greataxe.knockback);
        }

        return ticksUsed >= 15;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.greataxe.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.greataxe.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GREATAXE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedFloat.Restrict(min = 0f)
        public float extraDamage = 1f;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 2f;
    }
}
