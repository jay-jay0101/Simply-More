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
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class TwinbladeItem extends MimicryItem implements TwoHandedWeapon {
    public TwinbladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 8) {
            List<LivingEntity> enemies = spinAttack(player, 4.5f);
            float damage = MIMICRY_CONFIG.twinblade.firstDamage;

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.twinblade.effectTime,
                                        1
                                )
                        );
                    }
            );
        }

        if(ticksUsed == 24) {
            List<LivingEntity> enemies = spinAttack(player, 4.5f);
            float damage = MIMICRY_CONFIG.twinblade.secondDamage;

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        knockback(player, target, MIMICRY_CONFIG.twinblade.knockback);
                    }
            );
        }

        if(ticksUsed >= 30) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.twinblade.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.twinblade.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_TWINBLADE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float firstDamage = 5f;
        @ValidatedFloat.Restrict(min = 0f)
        public float secondDamage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.5f;
    }
}
