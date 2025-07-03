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

public class PernachItem extends MimicryItem {
    public PernachItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        float damage = mimicry.pernach.damage;

        if(ticksUsed == 3 || ticksUsed == 12) {
            List<LivingEntity> enemies = sweepAttack(player, 1.4f);
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) breakShield(target);
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                    }
            );
        }

        if(ticksUsed == 21) {
            List<LivingEntity> enemies = sweepAttack(player, 1.6f);

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        mimicry.pernach.effectTime,
                                        1
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WEAKNESS,
                                        mimicry.pernach.effectTime,
                                        0
                                )
                        );
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        ModEffectsRegistry.getReference(ModEffectsRegistry.BLEED),
                                        mimicry.pernach.effectTime,
                                        0
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 25) {
            player.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicry.pernach.disabled;
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.pernach");
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.pernach.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.pernach.tooltip2").setStyle(textStyle));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MIMICRY_PERNACH));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 5.5f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 100;
    }
}
