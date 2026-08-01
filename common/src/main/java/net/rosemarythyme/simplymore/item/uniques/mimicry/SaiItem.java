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

public class SaiItem extends MimicryItem {
    public SaiItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.sai.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.sai.tooltip1").setStyle(Styles.TEXT));
    }

    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        float damage = MIMICRY_CONFIG.sai.damage;

        if(ticksUsed == 4 || ticksUsed == 7 || ticksUsed == 10) {
            List<LivingEntity> enemies = stabAttack(player, 2, 0.25f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffectRegistry.getReference(StatusEffectRegistry.BLEED),
                                        MIMICRY_CONFIG.sai.effectTime,
                                        0
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.BLINDNESS,
                                        MIMICRY_CONFIG.sai.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 15) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }

    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_SAI));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 5.5f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
