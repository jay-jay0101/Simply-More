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

public class ClaymoreItem extends MimicryItem implements TwoHandedWeapon {
    public ClaymoreItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 2) {
            jump(player, 3.2f,0.7f);
            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffectRegistry.getReference(StatusEffectRegistry.LIGHTWEIGHT),
                            20,
                            0
                    )
            );
        }

        if(ticksUsed==22) {
            List<LivingEntity> enemies = slamAttack(player, 5f);
            float damage = MIMICRY_CONFIG.claymore.damage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        knockback(player, target, MIMICRY_CONFIG.claymore.knockback);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        MIMICRY_CONFIG.claymore.effectTime,
                                        1
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WEAKNESS,
                                        MIMICRY_CONFIG.claymore.effectTime,
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

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.claymore.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.claymore.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_CLAYMORE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.6f;
    }
}
