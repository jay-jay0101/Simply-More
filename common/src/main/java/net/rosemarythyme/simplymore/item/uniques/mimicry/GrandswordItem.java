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
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GrandswordItem extends MimicryItem implements TwoHandedWeapon {
    public GrandswordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.GRANDSWORD, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed > 8 && 50 >= ticksUsed) {
            jump(player,0.65f,0f);
            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.BLINDNESS,
                            40,
                            0
                    )
            );

            if(ticksUsed % 10 != 0) return;

            List<LivingEntity> enemies = spinAttack(player, 4f);
            float damage = mimicryConfig.grandsword.damage;

            enemies.forEach(
                    target -> {
                        AttackUtils.breakShield(target);
                        AttackUtils.hitWithEnchants(player, target, damage);
                        player.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.STRENGTH,
                                        mimicryConfig.grandsword.effectTime,
                                        0
                                )
                        );
                        knockback(player, target, mimicryConfig.grandsword.knockback);
                    }
            );
        }

        if(ticksUsed >= 60) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryConfig.grandsword.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.grandsword.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GRANDSWORD));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 15f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 120;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 2.5f;
    }
}
