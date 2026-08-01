package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class BackhandBladeItem extends MimicryItem {
    public BackhandBladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed > 2 && 9 >= ticksUsed) {
            jump(player,3f,0f);

            List<LivingEntity> enemies = sweepAttack(player, 2f);
            float damage = MIMICRY_CONFIG.backhand_blade.damage;

            enemies.forEach(
                    target -> {
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.BLINDNESS,
                                        MIMICRY_CONFIG.backhand_blade.effectTime,
                                        0
                                )
                        );
                    }
            );
        }
        if(ticksUsed > 10 && 16 >= ticksUsed) {
            jump(player,-3f,0f);
        }

        if(ticksUsed >= 18) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.backhand_blade.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.backhand_blade.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_BACKHAND_BLADE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
