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

public class ScytheItem extends MimicryItem {
    public ScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 2 || ticksUsed == 17 || ticksUsed == 32) {
            jump(player, 3f,0.18f);
        }

        if(ticksUsed == 15 || ticksUsed == 30 || ticksUsed == 45) {
            List<LivingEntity> enemies = sweepAttack(player, 2.8f);
            float damage = mimicry.scythe.damage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        mimicry.scythe.effectTime
                                )
                        );
                    }
            );

            List<LivingEntity> enemies2 = sweepAttack(player, 2.8f,25);
            enemies2.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        mimicry.scythe.effectTime
                                )
                        );
                    }
            );

            List<LivingEntity> enemies3 = sweepAttack(player, 2.8f,-25);
            enemies3.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.WITHER,
                                        mimicry.scythe.effectTime
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 50) {
            player.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicry.scythe.disabled;
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.scythe");
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.scythe.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.scythe.tooltip2").setStyle(textStyle));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MIMICRY_SCYTHE));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 10f;
        @ValidatedInt.Restrict(min = 0)
        public int effectTime = 150;
    }
}
