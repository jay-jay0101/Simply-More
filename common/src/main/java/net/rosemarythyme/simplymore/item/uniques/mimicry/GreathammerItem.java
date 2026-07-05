package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GreathammerItem extends MimicryItem {
    public GreathammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 6 || ticksUsed == 18) {
            List<LivingEntity> enemies = slamAttack(player, 6f);
            float damage = mimicryConfig.greathammer.damage;
            enemies.forEach(
                    target -> {
                        AttackUtils.breakShield(target);
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicryConfig.greathammer.effectTime,
                                        1
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WEAKNESS,
                                        mimicryConfig.greathammer.effectTime,
                                        1
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 28) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryConfig.greathammer.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.greathammer.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GREATHAMMER));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 7.5f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
