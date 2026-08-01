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

public class WarglaiveItem extends MimicryItem {
    public WarglaiveItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 8) {
            List<LivingEntity> enemies = spinAttack(player, 4f);
            float damage = MIMICRY_CONFIG.warglaive.firstDamage;

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.warglaive.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed == 16) {
            jump(player, 3.5f, 0.4f);
        }

        if(ticksUsed == 30) {
            List<LivingEntity> enemies = spinAttack(player, 4f);
            float damage = MIMICRY_CONFIG.warglaive.secondDamage;

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.warglaive.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 36) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.warglaive.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.warglaive.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_WARGLAIVE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float firstDamage = 5f;
        @ValidatedFloat.Restrict(min = 0f)
        public float secondDamage = 6.75f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
