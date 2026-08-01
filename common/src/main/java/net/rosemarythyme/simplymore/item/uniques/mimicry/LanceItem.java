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

public class LanceItem extends MimicryItem {
    public LanceItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 2) {
            jump(player, 3.2f,0.4f);
            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffectRegistry.getReference(StatusEffectRegistry.LIGHTWEIGHT),
                            20,
                            0
                    )
            );
        }

        if(ticksUsed==19) {
            List<LivingEntity> enemies = stabAttack(player, 4,0.8f);
            float damage = MIMICRY_CONFIG.lance.firstDamage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.lance.effectTime,
                                        2
                                )
                        );
                    }
            );
        }

        if(ticksUsed==27) {
            List<LivingEntity> enemies = stabAttack(player, 4,0.8f);
            float damage = MIMICRY_CONFIG.lance.secondDamage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        knockback(player, target, MIMICRY_CONFIG.lance.knockback);
                    }
            );
        }

        if(ticksUsed >= 36) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.lance.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.lance.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_LANCE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float firstDamage = 7f;
        @ValidatedFloat.Restrict(min = 0f)
        public float secondDamage = 9f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.5f;
    }
}
