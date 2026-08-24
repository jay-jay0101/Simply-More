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

public class KhopeshItem extends MimicryItem {
    public KhopeshItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void usageTimeline(PlayerEntity player, int ticksUsed) {
        if(ticksUsed == 3) {
            jump(player, 2.6f, 0.4f);
        }

        if(ticksUsed == 14) {
            List<LivingEntity> enemies = sweepAttack(player, 1.2f);
            float damage = mimicryAttributes.getKhopeshDamage();
            enemies.forEach(
                    target -> {
                        if(target.isBlocking()) return;
                        target.damage(player.getDamageSources().playerAttack(player), damage);
                        player.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SPEED,
                                        mimicryAttributes.getKhopeshEffectTime(),
                                        2
                                )
                        );
                    }
            );
        }



        if(ticksUsed >= 20) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING.get());
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableKhopeshVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.khopesh");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.khopesh.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.khopesh.tooltip2").setStyle(textStyle));
    }
}
