package net.rosemarythyme.simplymore.item.uniques.mimicry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import org.joml.Vector3d;

import java.util.List;

public class GreatKatanaItem extends MimicryItem {
    public GreatKatanaItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
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
            float damage = mimicryAttributes.getGreatKatanaDamage();

            if(!enemies.isEmpty()) {
                LivingEntity mainTarget = enemies.get(player.getRandom().nextBetween(0, enemies.size() - 1));

                enemies.forEach(
                        target -> {
                            if (target.isBlocking()) return;
                            target.timeUntilRegen = 0;
                            player.teleport(target.getX(), target.getY(), target.getZ());
                            sweepAttack(player, 0.1f);
                            if (target == mainTarget) {
                                target.damage(player.getDamageSources().playerAttack(player), damage + mimicryAttributes.getGreatAdditionalKatanaDamage());
                            } else {
                                target.damage(player.getDamageSources().playerAttack(player), damage);
                            }
                        }
                );

                player.teleport(mainTarget.getX(), mainTarget.getY(), mainTarget.getZ());
            }
        }

        if(ticksUsed >= 40) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING);
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableGreatKatanaVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.great_katana");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_katana.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.great_katana.tooltip2").setStyle(textStyle));
    }
}
