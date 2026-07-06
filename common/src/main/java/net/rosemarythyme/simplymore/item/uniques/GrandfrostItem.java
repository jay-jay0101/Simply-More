package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.ItemRegistry;
import net.rosemarythyme.simplymore.registry.StatusEffectRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.TwoHandedWeapon;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;

public class GrandfrostItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon {
    int skillCooldown = UNIQUE_CONFIG.grandfrost.cooldown;

    public GrandfrostItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, SwordType.GRANDSWORD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            if (!attacker.getWorld().isClient()) {
                if (target.isBlocking() || MathUtils.chance(attacker, UNIQUE_CONFIG.grandfrost.chance)) {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), UNIQUE_CONFIG.grandfrost.chillTime, 0), attacker);
                }
            }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getWorld().isClient()) {
            return super.use(world, user, hand);
        }

        Box box = MathUtils.createCubeBox(user.getPos(), UNIQUE_CONFIG.grandfrost.blizzardRange);
        List<LivingEntity> livingEntities = AttackUtils.getTargets(user, box);

        if (livingEntities.size() > 1) {
            boolean isNonTeammateNearby = false;

            for (LivingEntity livingEntity : livingEntities) {

                isNonTeammateNearby = true;

                Vec3d userPosition = user.getPos();
                Vec3d entityPosition = livingEntity.getPos();

                double deltaX = entityPosition.getX() - userPosition.getX();
                double deltaZ = entityPosition.getZ() - userPosition.getZ();
                double distance = Math.hypot(deltaX, deltaZ);

                if (distance == 0) {
                    return super.use(world, user, hand);
                }

                float knockbackStrength = UNIQUE_CONFIG.grandfrost.blizzardStrength;
                double normalizedDeltaX = deltaX / distance;
                double normalizedDeltaZ = deltaZ / distance;

                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffectRegistry.getReference(StatusEffectRegistry.CHILL), UNIQUE_CONFIG.grandfrost.blizzardEffectTime, 0));
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, UNIQUE_CONFIG.grandfrost.blizzardEffectTime, 3));
                livingEntity.setVelocity(normalizedDeltaX * knockbackStrength, 0.4, normalizedDeltaZ * knockbackStrength);
                livingEntity.velocityModified = true;
            }
            if(isNonTeammateNearby) {
                user.getItemCooldownManager().set(this, skillCooldown);
                ((ServerWorld) user.getWorld()).spawnParticles(ParticleTypes.SNOWFLAKE, user.getX(), user.getY() + 3, user.getZ(), 1000, 3, 0, 3, 0.25);
                user.getWorld().playSound(null, user.getBlockPos(), SoundRegistry.ELEMENTAL_SWORD_ICE_ATTACK_03.get(), user.getSoundCategory(), 2F, 0.3F);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ITEM_SNOWBALL, ParticleTypes.ITEM_SNOWBALL, ParticleTypes.SNOWFLAKE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        Style textStyle = Styles.TEXT;
        Style abilityStyle = Styles.ABILITY;
        Style rightClickStyle = Styles.RIGHT_CLICK;

        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip1").setStyle(abilityStyle));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip2").setStyle(textStyle));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(rightClickStyle));
        tooltip.add(Text.translatable("item.simplymore.grandfrost.tooltip4").setStyle(textStyle));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.GRANDFROST));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.25f;
        @ValidatedInt.Restrict(min = 0)
        public int chillTime = 140;
        @ValidatedInt.Restrict(min = 0)
        public int blizzardRange = 5;
        @ValidatedFloat.Restrict(min = 0f)
        public float blizzardStrength = 3.5f;
        @ValidatedInt.Restrict(min = 0)
        public int blizzardEffectTime = 200;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 500;
    }
}
