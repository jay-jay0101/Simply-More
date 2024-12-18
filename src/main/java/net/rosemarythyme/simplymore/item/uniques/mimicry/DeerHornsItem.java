package net.rosemarythyme.simplymore.item.uniques.mimicry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;

import java.util.List;

public class DeerHornsItem extends MimicryItem {
    public DeerHornsItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
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
                player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING);
            }

            List<LivingEntity> enemies = spinAttack(player, 1.2f);
            float damage = mimicryAttributes.getDeerHornsDamage();

            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.timeUntilRegen = 0;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        player.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SPEED,
                                        mimicryAttributes.getDeerHornsEffectTime(),
                                        1
                                )
                        );
                    }
            );
        }

        if(ticksUsed >= 60) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING);
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableDeerHornsVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.deer_horns");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.deer_horns.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.deer_horns.tooltip2").setStyle(textStyle));
    }
}
