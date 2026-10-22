package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.rosemarythyme.simplymore.entity.projectiles.AntimatterParticleEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.*;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.sweenus.simplyswords.api.WeaponAbilityActivationSource;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;
import java.util.Optional;

public class MatterbaneItem extends SimplyMoreUniqueSwordItem implements UniqueWeaponActiveAbility {
    public static final MatterbaneItem.EffectSettings SETTINGS = UNIQUE_CONFIG.matterbane;

    public MatterbaneItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public boolean onSwing(ItemStack stack, ServerWorld world, LivingEntity user, long timeSinceLastActivation) {
        if (timeSinceLastActivation < SETTINGS.particleCooldown) return false;

        SummonUtils.spawnProjectile(new AntimatterParticleEntity(user, user.getEyePos(), MathUtils.getDirectionalVector(user.getYaw(), user.getPitch()), ItemStackUtils.getColorDec(stack, 0xFF0000)), user);
        return true;
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) {
            return TargetUtils.findTarget(context.actor(), MatterbaneItem.SETTINGS.particleEmpowermentRange, entity -> entity instanceof AntimatterParticleEntity && ((AntimatterParticleEntity) entity).canEmpower(context.actor())).isPresent();
        } else {
            return !SummonUtils.getOwnedProjectiles(context.actor(), AntimatterParticleEntity.class).isEmpty();
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        Optional<Entity> particle;
        if(context.activationSource() == WeaponAbilityActivationSource.PLAYER) {
            particle = TargetUtils.findTarget(context.actor(), MatterbaneItem.SETTINGS.particleEmpowermentRange, entity -> entity instanceof AntimatterParticleEntity p && p.canEmpower(context.actor()));
        } else {
            List<AntimatterParticleEntity> particles = SummonUtils.getOwnedProjectiles(context.actor(), AntimatterParticleEntity.class);
            particle = particles.isEmpty() ? Optional.empty() : Optional.of(particles.getFirst());
        }

        if(particle.isPresent() && particle.get() instanceof AntimatterParticleEntity target) {
            target.empower();
        }

        return true;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.ASH);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip1").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip3").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip4").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, itemStack, SETTINGS.cooldown);
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.matterbane.tooltip5").setStyle(Styles.ABILITY));

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MATTERBANE));
        }

        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 600;
        @ValidatedInt.Restrict(min = 0)
        public int particleCooldown = 30;
        @ValidatedInt.Restrict(min = 0)
        public int particleLifespan = 240;
        @ValidatedInt.Restrict(min = 0)
        public int particleStableTime = 40;
        @ValidatedInt.Restrict(min = 0)
        public int particleEmpowermentDuration = 200;
        @ValidatedFloat.Restrict(min = 0f)
        public float particleEmpowermentRadius = 6f;
        @ValidatedFloat.Restrict(min = 0f)
        public float particleEmpowermentRange = 15f;
        @ValidatedFloat.Restrict(min = 0f)
        public float explosionDamage = 12f;
        @ValidatedFloat.Restrict(min = 0f)
        public float empoweredExplosionDamage = 24f;
        @ValidatedDouble.Restrict(min = 0)
        public double explosionRange = 3f;
        @ValidatedDouble.Restrict(min = 0)
        public double explosionKnockback = 1.7f;
    }
}
