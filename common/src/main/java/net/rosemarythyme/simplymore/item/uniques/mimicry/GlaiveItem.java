package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
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

public class GlaiveItem extends MimicryItem implements TwoHandedWeapon {
    public GlaiveItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed == 1) {
            MimicryTimelineUtils.startAnimation(entity, 6, MimicryVisualEntity.Animation.DOWN_SWING);
        }

        if(ticksUsed == 2) {
            TargetList targets = MimicryTimelineUtils.sweepAttack(entity, 3f);

            TargetList pets = targets.filter((target) -> target.getVehicle() instanceof LivingEntity)
                    .damageWithEnchants(MIMICRY_CONFIG.glaive.mountedDamage, entity)
                    .applyEffect(StatusEffects.SLOWNESS, MIMICRY_CONFIG.glaive.effectTime, 1)
                    .onEach(Entity::dismountVehicle);

            targets.exclude(pets)
                    .damageWithEnchants(MIMICRY_CONFIG.glaive.damage, entity);
        }

        return ticksUsed >= 8;
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.glaive.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.glaive.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GLAIVE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedFloat.Restrict(min = 0f)
        public float mountedDamage = 12f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 120;
    }
}
