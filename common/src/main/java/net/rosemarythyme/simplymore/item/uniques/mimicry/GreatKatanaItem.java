package net.rosemarythyme.simplymore.item.uniques.mimicry;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.rosemarythyme.simplymore.entity.MimicryVisualEntity;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.rosemarythyme.simplymore.util.data.TargetList;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GreatKatanaItem extends MimicryItem implements TwoHandedWeapon {
    public GreatKatanaItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }



    @Override
    public boolean usageTimeline(LivingEntity entity, int ticksUsed) {
        if(ticksUsed > 3 && 22 >= ticksUsed && ticksUsed % 4 == 0) {
            MimicryVisualEntity.Animation anim = switch ((int) Math.floor(ticksUsed % 9 / 3f)) {
                case 0 -> MimicryVisualEntity.Animation.LONG_SWING;
                case 1 -> MimicryVisualEntity.Animation.REVERSE_LONG_SWING;
                case 2 -> MimicryVisualEntity.Animation.DOWN_SWING;
                default -> MimicryVisualEntity.Animation.NONE;
            };

            MimicryTimelineUtils.startAnimation(entity, 4, anim);

            if (ticksUsed == 20) {
                sweepAttack(entity, 5f)
                        .forceDamageWithEnchants(MIMICRY_CONFIG.great_katana.damage, entity)
                        .knockback(entity, MIMICRY_CONFIG.great_katana.knockback);
            } else {
                sweepAttack(entity, 3f)
                        .filter(PredicateUtils.IS_NOT_BLOCKING)
                        .forceDamageWithEnchants(MIMICRY_CONFIG.great_katana.damage, entity);
            }
        }
        return ticksUsed >= 29;
    }

    public static TargetList sweepAttack(LivingEntity player, float radius) {
        Vec3d position = player.getEyePos().add(MathUtils.getNormalised2dVector(player.getYaw()).multiply(radius));

        AudioVisualUtils.playSound(player.getWorld(), position, new Sound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK).setPitch(0.5f));
        AudioVisualUtils.particleCube((ServerWorld) player.getWorld(), position, ParticleTypes.SWEEP_ATTACK, Math.round(radius * radius * 2 * 2), radius - 0.5f, 0);

        return TargetUtils.cubeAttack(player, position, radius, TargetUtils.TargetType.ENEMIES);
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
        public float damage = 5f;
        @ValidatedFloat.Restrict(min = 0f)
        public float knockback = 1.5f;
    }
}
