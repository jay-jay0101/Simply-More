package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class RapierItem extends MimicryItem {
    public RapierItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.rapier.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.rapier.tooltip1").setStyle(Styles.TEXT));
    }

    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        float damage = MIMICRY_CONFIG.rapier.damage;

        if(ticksUsed == 4 || ticksUsed == 16) {
            List<LivingEntity> enemies = stabAttack(player, 3, 0.25f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffectRegistry.getReference(StatusEffectRegistry.WOUNDED),
                                        MIMICRY_CONFIG.rapier.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed == 7 || ticksUsed == 10 || ticksUsed == 13) {
            List<LivingEntity> enemies = stabAttack(player, 3, 0.25f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        AttackUtils.hitWithEnchants(player, target, damage);
                    }
            );
        }

        if(ticksUsed >= 22) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }

    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_RAPIER));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 3f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 60;
    }
}
