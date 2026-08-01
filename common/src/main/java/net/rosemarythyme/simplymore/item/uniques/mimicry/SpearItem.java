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

public class SpearItem extends MimicryItem {
    public SpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.spear.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.spear.tooltip1").setStyle(Styles.TEXT));
    }

    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 6 || ticksUsed == 10) {
            List<LivingEntity> enemies = stabAttack(player, 5, 0.4f);

            float damage = MIMICRY_CONFIG.spear.damage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.spear.effectTime,
                                        0
                                )
                        );
                    }
            );
        }
        if(ticksUsed == 20) {
            List<LivingEntity> enemies = stabAttack(player, 5, 0.4f);

            float damage = MIMICRY_CONFIG.spear.finalDamage;
            enemies.forEach(
                    target -> {
                        AttackUtils.breakShield(target);
                        target.timeUntilRegen = 0;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.spear.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 28) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }

    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_SPEAR));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedFloat.Restrict(min = 0f)
        public float finalDamage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
