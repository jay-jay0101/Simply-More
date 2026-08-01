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
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GreatSpearItem extends MimicryItem implements TwoHandedWeapon {
    public GreatSpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 6) {
            List<LivingEntity> enemies = slamAttack(player, 4f);
            float damage = MIMICRY_CONFIG.great_spear.slamDamage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.MINING_FATIGUE,
                                        MIMICRY_CONFIG.great_spear.effectTime,
                                        25
                                )
                        );
                        jump(target, 0, 0.525f);
                    }
            );
        }

        if(ticksUsed == 18) {
            List<LivingEntity> enemies = stabAttack(player, 6,0.8f);
            float damage = MIMICRY_CONFIG.great_spear.stabDamage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        knockback(player,target, MIMICRY_CONFIG.great_spear.knockback);
                    }
            );
        }

        if(ticksUsed >= 28) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.great_spear.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_spear.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GREAT_SPEAR));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float slamDamage = 6f;
        @ValidatedFloat.Restrict(min = 0f)
        public float stabDamage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 20;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.3f;
    }
}
