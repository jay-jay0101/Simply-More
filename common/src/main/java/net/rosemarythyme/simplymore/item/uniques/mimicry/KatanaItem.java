package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.rosemarythyme.simplymore.item.uniques.MimicryItem;
import net.rosemarythyme.simplymore.registry.ModEffectsRegistry;
import net.rosemarythyme.simplymore.registry.ModItemsRegistry;
import net.rosemarythyme.simplymore.util.SimplyMoreHelperMethods;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import org.joml.Vector3d;

import java.util.List;

public class KatanaItem extends MimicryItem {
    public KatanaItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordTypes.SWORD, settings);
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
            float damage = mimicry.katana.damage;

            Vec3d eyePos = player.getEyePos();
            Vec3d playerRotation = player.getRotationVec(1);
            Vec3d maxDistance = eyePos.add(playerRotation.x * 15, playerRotation.y * 15, playerRotation.z * 15);

            BlockHitResult hit = player.getWorld().raycast(new RaycastContext(
                    currentPos, maxDistance, RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE, player
            ));

            player.requestTeleport(hit.getPos().getX(), hit.getPos().getY() + 0.2, hit.getPos().getZ());
            player.setVelocity(0, 0, 0);
            player.velocityModified = true;

            double distance = Vector3d.distance(currentPos.getX(), currentPos.getY(), currentPos.getZ(), maxDistance.getX(), maxDistance.getY(), maxDistance.getZ());

            Vector3d normalisedVector = SimplyMoreHelperMethods.getNormalised3dVector(player);
            for(int i = 0; i < 15; i++) {
                double distanceInterval = distance/((double) 15 /(i+1));

                Vec3d slashPos = new Vec3d(
                        currentPos.getX() + (normalisedVector.x() * distanceInterval),
                        currentPos.getY() + (normalisedVector.y() * distanceInterval),
                        currentPos.getZ() + (normalisedVector.z() * distanceInterval)
                );

                List<LivingEntity> livingEntities = katanaAttack(player,slashPos.getX(), slashPos.getY(), slashPos.getZ(), 1.3f);
                livingEntities.forEach(
                        (target) -> SimplyMoreHelperMethods.hitWithEnchants(player, target, damage)
                );
            }
        }

        if(ticksUsed >= 40) {
            player.removeStatusEffect(ModEffectsRegistry.getReference(ModEffectsRegistry.MIMICRY_HAPPENING));
        }
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return mimicry.katana.disabled;
    }

    @Override
    public Text getMimicryFormName() {
        return Text.translatable("item.simplymore.mimicry.katana");
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.katana.tooltip1").setStyle(textStyle));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ModItemsRegistry.MIMICRY_KATANA));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 12f;
    }
}
