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
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class ScytheItem extends MimicryItem implements TwoHandedWeapon {
    public ScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 2 || ticksUsed == 17 || ticksUsed == 32) {
            jump(player, 3f,0.18f);
        }

        if(ticksUsed == 15 || ticksUsed == 30 || ticksUsed == 45) {
            List<LivingEntity> enemies = sweepAttack(player, 2.8f);
            float damage = MIMICRY_CONFIG.scythe.damage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        MIMICRY_CONFIG.scythe.effectTime
                                )
                        );
                    }
            );

            List<LivingEntity> enemies2 = sweepAttack(player, 2.8f,25);
            enemies2.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        MIMICRY_CONFIG.scythe.effectTime
                                )
                        );
                    }
            );

            List<LivingEntity> enemies3 = sweepAttack(player, 2.8f,-25);
            enemies3.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        MIMICRY_CONFIG.scythe.effectTime
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 50) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.scythe.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.scythe.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_SCYTHE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 150;
    }
}
