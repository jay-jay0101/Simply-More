package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class PernachItem extends MimicryItem {
    public PernachItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        float damage = MIMICRY_CONFIG.pernach.damage;

        if(ticksUsed == 3 || ticksUsed == 12) {
            List<LivingEntity> enemies = sweepAttack(player, 1.4f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) AttackUtils.breakShield(target);
                        AttackUtils.hitWithEnchants(player, target, damage);
                    }
            );
        }

        if(ticksUsed == 21) {
            List<LivingEntity> enemies = sweepAttack(player, 1.6f);

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.pernach.effectTime,
                                        1
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WEAKNESS,
                                        MIMICRY_CONFIG.pernach.effectTime,
                                        0
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffectRegistry.getReference(StatusEffectRegistry.BLEED),
                                        MIMICRY_CONFIG.pernach.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 25) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.pernach.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.pernach.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_PERNACH));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 5.5f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
