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
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;

import java.util.List;

public class SpearItem extends MimicryItem {
    public SpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    @Override
    public boolean isFormDisabledInConfig() {
        return mimicry.spear.disabled;
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.spear");
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.spear.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.spear.tooltip2").setStyle(textStyle));
    }

    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 6 || ticksUsed == 10) {
            List<LivingEntity> enemies = stabAttack(player, 5, 0.4f);

            float damage = mimicry.spear.damage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicry.spear.effectTime,
                                        0
                                )
                        );
                    }
            );
        }
        if(ticksUsed == 20) {
            List<LivingEntity> enemies = stabAttack(player, 5, 0.4f);

            float damage = mimicry.spear.finalDamage;
            enemies.forEach(
                    target -> {
                        breakShield(target);
                        target.timeUntilRegen = 0;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicry.spear.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 28) {
            player.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING));
        }

    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MIMICRY_SPEAR));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 8f;
        @ValidatedFloat.Restrict(min = 0f)
        public float finalDamage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 80;
    }
}
