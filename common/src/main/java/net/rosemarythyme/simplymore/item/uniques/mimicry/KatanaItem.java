package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.TargetUtils;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class KatanaItem extends MimicryItem implements TwoHandedWeapon {
    public KatanaItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        new TargetList(entity).applyEffect(StatusEffects.SLOWNESS, 10, 4);

        if(ticksUsed == 5) {
            Vec3d originalPos = entity.getPos();
            Vec3d endPos = entity.getEyePos().add(MathUtils.getDirectionalVector(entity.getYaw(), entity.getPitch()).multiply(MIMICRY_CONFIG.katana.maxRange));

            BlockHitResult hit = entity.getWorld().raycast(new RaycastContext(
                    entity.getEyePos(), endPos, RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE, entity
            ));

            entity.requestTeleport(hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ());
            entity.setVelocity(0, 0, 0);
            entity.velocityModified = true;

            katanaAttack(entity, originalPos, 1.3f)
                    .forceDamageWithEnchants(MIMICRY_CONFIG.katana.damage, entity);
        }

        return ticksUsed >= 12;
    }

    public static TargetList katanaAttack(LivingEntity entity, Vec3d originalPos, float width) {
        ServerWorld world = (ServerWorld) entity.getWorld();
        AudioVisualUtils.particleLine(world, originalPos, entity.getEyePos(), ParticleTypes.SWEEP_ATTACK, 0.5f, 5, 0.25f, 0);

        Sound sound = new Sound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP).setPitch(entity.getRandom().nextBetween(9,14) / 10f);
        AudioVisualUtils.playSound(world, originalPos, sound);
        AudioVisualUtils.playSound(world, entity.getPos(), sound);

        return TargetUtils.lineAttack(entity, originalPos, entity.getEyePos(), width, TargetUtils.TargetType.ENEMIES);
    }

    @Override
    public boolean isFormDisabledInConfig() {
        return MIMICRY_CONFIG.katana.disabled;
    }

    @Override
    public void appendSpecificTooltip(List<Text> tooltip) {
        tooltip.add(Text.translatable("item.simplymore.mimicry.katana.tooltip1").setStyle(Styles.TEXT));
    }

    public static class MimicryEffectSettings extends TooltipSettings {
        public MimicryEffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MIMICRY_KATANA));
        }

        public boolean disabled = false;
        @ValidatedFloat.Restrict(min = 0f)
        public float damage = 12f;
        @ValidatedFloat.Restrict(min = 0f)
        public float maxRange = 15f;
    }
}
