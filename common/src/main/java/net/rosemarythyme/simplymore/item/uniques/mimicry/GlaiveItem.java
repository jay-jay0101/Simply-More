package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GlaiveItem extends MimicryItem implements TwoHandedWeapon {
    public GlaiveItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 2) {
            List<LivingEntity> enemies = sweepAttack(player, 3f);
            float damage = MIMICRY_CONFIG.glaive.damage;
            enemies.forEach(
                    target -> {
                        target.dismountVehicle();
                        AttackUtils.hitWithEnchants(player, target, damage);
                    }
            );
        }

        if(ticksUsed >= 8) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
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
        public float damage = 6f;
    }
}
