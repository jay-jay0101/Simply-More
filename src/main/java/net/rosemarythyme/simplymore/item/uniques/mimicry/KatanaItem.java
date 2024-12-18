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

public class KatanaItem extends MimicryItem {
    public KatanaItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
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
            Vec3d currentPos = player.getPos();

            float damage = mimicryAttributes.getKatanaDamage();

            Vec3d targetPos = player.raycast(15, 1, false).getPos();

            player.teleport(targetPos.getX(), targetPos.getY(), targetPos.getZ());

            double distance = Vector3d.distance(currentPos.getX(), currentPos.getY(), currentPos.getZ(), targetPos.getX(), targetPos.getY(), targetPos.getZ());

            Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised3dVector(player);
            for(int i = 0; i < 15; i++) {
                double distanceInterval = distance/((double) 15 /(i+1));

                Vec3d slashPos = new Vec3d(
                        currentPos.getX() + (normalisedVector.x() * distanceInterval),
                        currentPos.getY() + (normalisedVector.y() * distanceInterval),
                        currentPos.getZ() + (normalisedVector.z() * distanceInterval)
                );

                List<LivingEntity> livingEntities = katanaAttack(player,slashPos.getX(), slashPos.getY(), slashPos.getZ(), 0.8f);
                livingEntities.forEach(
                        target -> {
                            target.damage(player.getDamageSources().playerAttack(player), damage);
                        }
                );
            }
        }

        if(ticksUsed >= 40) {
            player.removeStatusEffect(ModEffectsRegistry.MIMICRY_HAPPENING);
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicryAttributes.isDisableKatanaVariant();
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.katana");
    }

    @Override
    public void appendSpecificTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.katana.tooltip1").setStyle(textStyle));
        tooltip.add(Text.translatable("item.simplymore.mimicry.katana.tooltip2").setStyle(textStyle));
    }
}
