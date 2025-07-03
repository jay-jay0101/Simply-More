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

public class GreatSpearItem extends MimicryItem {
    public GreatSpearItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
    }


    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 6) {
            List<LivingEntity> enemies = slamAttack(player, 4f);
            float damage = mimicry.great_spear.slamDamage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        target.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.MINING_FATIGUE,
                                        mimicry.great_spear.effectTime,
                                        25
                                )
                        );
                        jump(target, 0, 0.525f);
                    }
            );
        }

        if(ticksUsed == 18) {
            List<LivingEntity> enemies = stabAttack(player, 6,0.8f);
            float damage = mimicry.great_spear.stabDamage;
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        knockback(player,target, mimicry.great_spear.knockback);
                    }
            );
        }

        if(ticksUsed >= 28) {
            player.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicry.great_spear.disabled;
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.great_spear");
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_spear.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_spear.tooltip2").setStyle(textStyle));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MIMICRY_GREAT_SPEAR));
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
