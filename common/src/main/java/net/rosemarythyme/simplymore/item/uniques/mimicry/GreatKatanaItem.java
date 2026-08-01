package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GreatKatanaItem extends MimicryItem implements TwoHandedWeapon {
    public GreatKatanaItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.SWORD, settings);
    }



    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        player.addStatusEffect(
                new StatusEffectInstance(
                        StatusEffects.SLOWNESS,
                        10,
                        4
                )
        );

        if(ticksUsed == 30) {
            List<LivingEntity> enemies = slamAttack(player, 6);
            float damage = MIMICRY_CONFIG.great_katana.damage;

            if(!enemies.isEmpty()) {
                LivingEntity mainTarget = enemies.get(player.getRandom().nextBetween(0, enemies.size() - 1));

                enemies.forEach(
                        target -> {
                            if (target.isBlocking()) return;
                            target.timeUntilRegen = 0;
                            player.teleport(target.getX(), target.getY(), target.getZ(), false);
                            sweepAttack(player, 0.1f);
                            if (target == mainTarget) {
                                AttackUtils.hitWithEnchants(player, target, damage + MIMICRY_CONFIG.great_katana.extraDamage);
                            } else {
                                AttackUtils.hitWithEnchants(player, target, damage);
                            }
                        }
                );

                player.teleport(mainTarget.getX(), mainTarget.getY(), mainTarget.getZ(), false);
            }
        }

        if(ticksUsed >= 40) {
            player.removeStatusEffect(StatusEffectRegistry.getReference(StatusEffectRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.great_katana.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_katana.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_GREAT_KATANA));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 14f;
        @ValidatedFloat.Restrict(min = 0f)
        public float extraDamage = 4f;
    }
}
