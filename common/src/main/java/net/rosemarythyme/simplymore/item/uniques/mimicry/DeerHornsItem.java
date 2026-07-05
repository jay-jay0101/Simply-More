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

public class DeerHornsItem extends MimicryItem {
    public DeerHornsItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }



    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 3) {
            jump(player,-1.2f,1f);
            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.SLOW_FALLING,
                            60,
                            0
                    )
            );
        }


        if(ticksUsed > 6) {

            if(player.isOnGround()) {
                player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
            }

            List<LivingEntity> enemies = spinAttack(player, 1.2f);
            float damage = mimicryConfig.deer_horns.damage;

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        AttackUtils.hitWithEnchants(player, target, damage);
                        player.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SPEED,
                                        mimicryConfig.deer_horns.effectTime,
                                        1
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 60) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryConfig.deer_horns.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.deer_horns.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_DEER_HORNS));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 1.8f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
