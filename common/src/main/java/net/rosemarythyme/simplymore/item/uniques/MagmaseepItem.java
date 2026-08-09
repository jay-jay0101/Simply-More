package net.rosemarythyme.simplymore.item.uniques;

import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
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
import net.rosemarythyme.simplymore.entity.EruptionEntity;
import net.rosemarythyme.simplymore.entity.VolcanicVentEntity;
import net.rosemarythyme.simplymore.item.SimplyMoreUniqueSwordItem;
import net.rosemarythyme.simplymore.registry.item.ItemRegistry;
import net.rosemarythyme.simplymore.util.AttackUtils;
import net.rosemarythyme.simplymore.util.AudioVisualUtils;
import net.rosemarythyme.simplymore.util.MathUtils;
import net.rosemarythyme.simplymore.util.data.FootfallParticles;
import net.rosemarythyme.simplymore.util.data.Sound;
import net.sweenus.simplyswords.api.WeaponAbilityContext;
import net.sweenus.simplyswords.client.util.TooltipUtils;
import net.sweenus.simplyswords.config.settings.ItemStackTooltipAppender;
import net.sweenus.simplyswords.config.settings.TooltipSettings;
import net.sweenus.simplyswords.item.interfaces.TwoHandedWeapon;
import net.sweenus.simplyswords.item.interfaces.UniqueWeaponActiveAbility;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.Styles;

import java.util.List;


public class MagmaseepItem extends SimplyMoreUniqueSwordItem implements TwoHandedWeapon, UniqueWeaponActiveAbility {
    public static final MagmaseepItem.EffectSettings SETTINGS = UNIQUE_CONFIG.magmaseep;

    public MagmaseepItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed) {
        super(toolMaterial, attackDamage, attackSpeed);
    }

    @Override
    public void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, ServerWorld world, int consecutiveHits, boolean isFirstInTick) {
        if(!isFirstInTick) return;

        if (MathUtils.chance(attacker, SETTINGS.chance)) {
            AudioVisualUtils.particleLine(world, attacker.getPos(), target.getPos(), ParticleTypes.LAVA, 0.4, 3, 0.2f, 0f);
            AudioVisualUtils.particleLine(world, attacker.getPos(), target.getPos(), ParticleTypes.SMOKE, 0.4, 15, 0.2f, 1f);
            AudioVisualUtils.playSound(world, attacker.getPos(), new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_03.get()).setPitch(0.3f));

            AttackUtils.lineAttack(attacker, attacker.getPos(), attacker.getYaw(), attacker.getPitch(), attacker.distanceTo(target),1, AttackUtils.AttackTarget.ENEMIES)
                    .knockback(attacker, SETTINGS.knockback)
                    .forceDamage(AttackUtils.scaleDamage("fire", attacker, stack, 0, 1, SETTINGS.eruptionDamage), attacker.getDamageSources().inFire());

            AttackUtils.spawnAbility(new EruptionEntity(attacker, attacker.getPos()), attacker);
            AttackUtils.spawnAbility(new EruptionEntity(attacker, target.getPos()), attacker);

            AudioVisualUtils.applyScreenshake(world, attacker.getPos(), attacker, 12, 2.5f, 20);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return useFromDefaultInput(world, user, hand);
    }

    @Override
    public boolean canActivate(WeaponAbilityContext context) {
        return context.actor().isAlive();
    }

    @Override
    public boolean activate(WeaponAbilityContext context) {
        if(AttackUtils.spawnAbility(new VolcanicVentEntity(context.actor(), context.origin()), context.actor(), true)) {
            AudioVisualUtils.applyScreenshake(context.world(), context.origin(), context.actor(), 12, 1, 10);
            AudioVisualUtils.playSound(context.world(), context.origin(), new Sound(SoundRegistry.ELEMENTAL_SWORD_EARTH_ATTACK_03.get()).setPitch(0.7f));
            return true;
        }

        return false;
    }

    @Override
    public int getActivationCooldownTicks(ItemStack stack, WeaponAbilityContext context) {
        return SETTINGS.cooldown;
    }

    @Override
    public FootfallParticles getFootfalls() {
        return new FootfallParticles(ParticleTypes.LAVA, ParticleTypes.LAVA, ParticleTypes.SMOKE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.magmaseep.ability").setStyle(Styles.ABILITY));
        tooltip.add(Text.translatable("item.simplymore.magmaseep.tooltip1").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplyswords.onrightclick").setStyle(Styles.RIGHT_CLICK));
        tooltip.add(Text.translatable("item.simplymore.magmaseep.tooltip2").setStyle(Styles.TEXT));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.simplymore.magmaseep.tooltip3").setStyle(Styles.TEXT));
        appendAbilityCooldownTooltip(tooltip, SETTINGS.cooldown);

        super.appendTooltip(itemStack, tooltipContext, tooltip, type);
        TooltipUtils.appendSpellScaleTooltip(tooltip, "fire");
    }

    public static class EffectSettings extends TooltipSettings {
        public EffectSettings() {
            super(new ItemStackTooltipAppender(ItemRegistry.MAGMASEEP));
        }

        @ValidatedFloat.Restrict(min = 0f, max = 1f)
        public float chance = 0.30f;
        @ValidatedInt.Restrict(min = 0)
        public int smokeDuration = 100;
        @ValidatedFloat.Restrict(min = 0)
        public float knockback = 2f;
        @ValidatedFloat.Restrict(min = 0)
        public float eruptionDamage = 8f;
        @ValidatedDouble.Restrict(min=0)
        public double smokeRange = 1.25f;
        @ValidatedInt.Restrict(min = 0)
        public int cooldown = 600;
        @ValidatedFloat.Restrict(min = 0)
        public float lavaDamage = 5f;
        @ValidatedFloat.Restrict(min = 0)
        public float lavaRainDamage = 3f;
        @ValidatedFloat.Restrict(min = 0)
        public float lavaRainRange = 5f;
        @ValidatedInt.Restrict(min = 0)
        public int ventDuration = 300;
        @ValidatedFloat.Restrict(min = 0)
        public float ventExplosionDamage = 14f;
        @ValidatedFloat.Restrict(min = 0)
        public float ventExplosionKnockback = 2f;

   }
}
